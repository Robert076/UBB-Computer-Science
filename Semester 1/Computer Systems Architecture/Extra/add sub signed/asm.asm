bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
; our code starts here
    a db -5
    b dw 10
    c dd 15
    d dq 30
    aux1 dd 0
    aux2 dd 0

; (c+b+a)-(d+d)
; a - byte, b - word, c - double word, d - qword - Signed representation


segment code use32 class=code
    start:
        mov AL, [a]
        cbw
        cwde
        mov EBX, EAX
        mov AX, [b]
        cwde
        
        mov ECX, EAX
        mov EAX, [c]
        
        add EBX, EAX
        add EBX, ECX
        
        mov EAX, dword[d]
        mov EDX, dword[d + 4]
        
        mov ECX, 0
        
        mov [aux1], EBX
        mov [aux2], ECX
        
        mov EBX, [d]
        mov ECX, [d + 4]
        
        add EAX, EBX
        adc EDX, ECX
        
        mov EBX, [aux1]
        mov ECX, [aux2]
        
        sub EBX, EAX
        sbb ECX, EDX ; result in ECX:EBX
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
