bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, scanf, printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import scanf msvcrt.dll
import printf msvcrt.dll                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    

    
; Problem statement
;
; se citesc n numere de la tastatura, sa se afiseze perechile de numere prime intre ele din sir
;


    n dd 0
    x times 100 dd 0
    rf db "%d", 0
    pf db "%d, %d", 0

; our code starts here
segment code use32 class=code
    start:
        push n
        push rf
        call [scanf]
        add esp, 4 * 2
        
        mov esi, x
        mov ecx, [n]
        jecxz theend
        
        repeat1:
            push esi
            push rf
            call [scanf]
            add esp, 4 * 2
            add esi, 4 ; go to the next dword space
            loop repeat1
        
        mov ecx, [n]
        mov esi, 0
        
        repeat2:
            mov eax, [x + esi]
            push ecx
            mov edi, esi
            pop ecx
            add edi, 4 ; the next dword
            dec ecx
            jecxz theend
            
            repeat3:
                mov ebx, [x + edi]
                push eax
                gcd:
                    mov edx, 0
                    div ebx
                    mov eax, ebx
                    mov ebx, edx
                    cmp ebx, 0
                    jne gcd ; calculating the greatest common divisor
                cmp eax, 1
                jne no 
                push dword [x + edi]
                push dword [x + esi]
                push pf
                call [printf]
                add esp, 4 * 3
                no:
                pop eax
                add edi, 4 ; next doubleword
                loop repeat3
            pop ecx
            add esi, 4
            loop repeat2
        theend:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
