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
        mov AH, 10 ; a = 10
        mov AL, 5 ; b = 5
        mov BH, 20 ; c = 20
        add AL, AH ; b = b + a
        add AL, BH ; b = b + c
        mov AH, 2 ; to be able to do (a + b + c) * 2 because AH * AL -> AX
        mul AH
        mov AH, 3 ; to do the next multiplication
        mul AH
        mov BX, 5 ; divisor ( g )  
        mov DX, AX
        div BX ; DX:AX / BX -> AX
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
