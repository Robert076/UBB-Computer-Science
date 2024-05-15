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
    a db 100
    b dw 10
    c dd 110
    d dq 500
    
    ; (a+c)-b+a + (d-c)
; our code starts here
segment code use32 class=code
    start:
        mov EAX, 0
    
        mov AL, [a]
        mov AH, 0 ; AX = a
        
        add EAX, [c] ; AX = a + c
        
        mov EBX, 0
        mov BX, [b] ; EBX = b
        
        sub EAX, EBX ; (a + c) - b = EAX

        mov EBX, 0
        mov BL, [a]
        
        add EAX, EBX ; (a + c) - b + a = EAX
        
        mov EBX, dword [d]
        mov EDX, dword [d + 4]
        
        sub EBX, dword [c] ; EDX:EBX = d - c
        
        add EBX, EAX
        adc EDX, dword 0
        
        mov ECX, 0
        
        ; ECX:EAX = (a + c) - b + ad
        
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
