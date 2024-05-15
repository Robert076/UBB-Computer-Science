bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov BL, 15 ; variabila b
        mov BH, 10 ; variabila c
        add BL, BH ; b = b + c
        mov BH, 50 ; c = 50
        sub BH, BL ; 50 - b - c
        mov AL, 2 ; AL = 2
        mul BH ; AL * BH -> AX
        mov BX, AX ; moving BX into AX to be able to multiply a*a into AX
        mov AL, 10 ; variable a is stored into AL (8bit)
        mul AL ; AL * AL -> AX now a 16 bit result ( this is a * a )
        add BX, AX ; adding a * a to the first part of the expression
        mov CX, 10 ; CX is variable d ( of type word )
        add BX, CX ; done (50 - b - c ) * 2 + a * a + d
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
