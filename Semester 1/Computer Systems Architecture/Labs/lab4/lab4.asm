bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a dw 0000000000000000b
    b dw 0000000000001100b
    c dd 0
;   Given the words A and B, compute the doubleword C as follows:
    ;   the bits 0-5 of C are the same as the bits 3-8 of A
    ;   the bits 6-8 of C are the same as the bits 2-4 of B
    ;   the bits 9-15 of C are the same as the bits 6-12 of A
    ;   the bits 16-31 of C have the value 0
; our code starts here
segment code use32 class=code
    start:
        mov EAX, 0
        mov AX, [a]
        and EAX, 0000000111111000b
        mov CL, 3
        ror EAX, CL
        or [c], EAX
        mov EAX, 0
        mov AX, [b]
        and EAX, 0000000000011100b
        mov CL, 4
        rol EAX, CL
        or [c], EAX
        mov EAX, 0
        mov AX, [a]
        and EAX, 0001111110000000b
        mov CL, 3
        rol EAX, CL
        or [c], EAX
        and dword [c], 000000000000000000001111111111111111b
        mov EAX, [c]
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
