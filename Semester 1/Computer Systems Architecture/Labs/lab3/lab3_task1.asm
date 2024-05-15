bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db 5
    b dw 10
    c dd 10
    d dq 15
; our code starts here
segment code use32 class=code
    start: ; (a+c)-b+a + (d-c)
        mov AL, [a]
        mov AH, 0 ; AX = a
        add AX, [b] ; AX = a + b
        sub AX, [b] ; AX = (a + b) - b
        mov BL, [a]
        mov BH, 0 ; BX = a
        add AX, BX ; AX = AX + b => AX = (a + b) - b + a
        mov EBX, [d] ; EBX = d
        mov ECX, [c] ; ECX = c
        sub EBX, ECX; EBX = EBX - ECX (d = d - c)
        mov DX, 0 ; DX:AX = AX
        push DX ; pushing DX and AX so we can pop them into EAX so that the subtraction compiles between EAX EBX
        push AX
        pop EAX ; putting DX:AX into EAX
        sub EAX, EBX ; final result
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
