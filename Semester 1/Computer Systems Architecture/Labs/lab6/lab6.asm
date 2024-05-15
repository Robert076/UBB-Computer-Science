bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                        ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s1 db 7, 2, 4, 1, 7, 9, 33, 20, 9, 76, 34, 12
    lens1 equ $-s1
    s2 db 33, 20, 9
    lens2 equ $-s2
    rez times lens1 db 1
    l equ lens1-lens2+1
    ; ...

; our code starts here
segment code use32 class=code
    start:
        mov ecx, l
        mov esi, s1
        mov ebx, 0
        mov edx, 0
        jecxz final
        loop1:
            push esi            ; la a[i]
            push ecx            ;indicele i
            mov ecx, lens2
            mov edi, s2
            repe cmpsb          ; do while esi=edi
            pop ecx             ; luam inapoi i
            jne notfound
                mov edx, l  
                sub edx, ecx    
                mov [rez+ebx], edx    ;r[i] = index la care am gasit aparitia lui b in a
                inc ebx
            notfound:
            pop esi
            inc esi             ; i++
        loop loop1
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program