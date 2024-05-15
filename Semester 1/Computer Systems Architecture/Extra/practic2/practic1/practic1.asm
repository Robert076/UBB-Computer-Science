bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, scanf, printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import scanf msvcrt.dll                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import printf msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    n dd 0
    rf dd "%d"
    pf dd "%d and %d are prime between them",  0
    x times 100 dd 0CCh
; numerele prime intre ele dintr-un sir
segment code use32 class=code
    start:
        push dword n
        push dword rf
        call [scanf]
        add esp, 4 * 2
        
        mov ecx, [n]
        jecxz theend
        
        mov esi, x
        
        citireNumere:
            push ecx
            push dword esi
            push dword rf
            call [scanf]
            add esp, 4 * 2
            pop ecx
            add esi, 4
            loop citireNumere ; dec ecx; cmp ecx, 0; jne citireNumere
        
        mov esi, 0
        mov ecx, [n]
        
        repeta1:
        mov eax, [x + esi]
        add esi, 4
        mov edi, esi
        sub esi, 4
        push ecx
        dec ecx
        jecxz theend
        repeta2:
            mov ebx, [x + edi]
            push eax
            push ecx
            gcd:
                mov edx, 0
                div ebx ;edx rest eax cat
                mov eax, ebx
                mov ebx, edx
                cmp ebx, 0
                jne gcd
            cmp eax, 1
            jne nuSuntPrimeIntreEle
            push dword [x + esi]
            push dword [x + edi]
            push pf
            call [printf]
            add esp, 4 * 3
            nuSuntPrimeIntreEle:
            add edi, 4
            pop ecx
            pop eax
            loop repeta2
        pop ecx
        add esi, 4
        loop repeta1
        theend:
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
