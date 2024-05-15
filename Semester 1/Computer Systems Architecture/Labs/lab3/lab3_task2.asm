bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db 50
    b dw 10
    c dd 55
    d dq 5
; our code starts here
segment code use32 class=code
    start:
        ;a - byte, b - word, c - double word, d - qword - Signed representation
        ; a-b-(c-d)+d
        mov AL, [a];
        cbw;
        sbb AX, [b]; AX = a - b
        mov BX, AX; BX = a - b
        mov AX, [c];
        mov DX, 0; DX:AX = c
        push DX
        push AX ; so we can put DX:AX into EAX
        pop EAX; c is now in EAX so that we can subtract d (quadword) from it
        sbb EAX, [d]
        push word 0
        push BX
        pop EBX ; EBX = BX = a - b
        sbb EBX, EAX
        adc EBX, [d] ; result is in EBX
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
