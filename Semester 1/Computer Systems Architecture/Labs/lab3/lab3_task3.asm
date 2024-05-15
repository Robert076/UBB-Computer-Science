bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a dw 23
    b db 10
    e dd 15
    x dq 20
    TEMP dd 0
    
; our code starts here
segment code use32 class=code
    start:
        ;unsigned
        ; x-b+8+(2*a-b)/(b*b)+e; a-word; b-byte; e-doubleword; x-qword
        mov BL, [b]
        mov BH, 0; BX = b
        push 0
        push BX
        pop EBX; EBX = b
        mov EAX, [x]
        sub EAX, EBX ; x - b
        add EAX, 8
        mov [TEMP], EAX ; TEMP = x - b + 8
        mov AL, 2
        mov AH, 0; AX = 2
        mov CX, [a]
        mul CX ; DX:AX = AX * CX
        push DX
        push AX
        pop EAX; EAX = 2 * a
        mov EDX, EAX ; EDX = 2 * a
        mov AH, 0
        mov AL, [b]
        push 0
        push AX
        pop EAX ; EAX = b
        mov ECX, EAX
        sub EDX, EAX ; 2 * a - b = EDX
        mov EBX, EDX
        mov AH, [b]
        mul AH
        mov BX, AX
        mov EAX, EDX
        push EAX
        pop AX
        pop DX
        div BX
        mov AH, 0; AL = cat, AH = rest de dinainte da il facem 0 ca sa avem ax = al adica ax = impartirea aia
        ; acuma adunam ce o ramas de la inceput
        add EAX, [TEMP]
        add EAX, [e]
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
