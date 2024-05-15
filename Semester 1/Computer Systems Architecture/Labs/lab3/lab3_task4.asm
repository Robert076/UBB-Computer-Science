bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a dw 10
    b db 5
    e dd 15
    x dq 20
    temp dq 0

; our code starts here
segment code use32 class=code
    start:
        ;x-b+8+(2*a-b)/(b*b)+e; SIGNED
        mov EAX, 0
        mov AL, [b]
        cbw ; EAX = b = 5
        mov EBX, EAX
        mov EAX, 0
        mov EAX, [x]; EAX = x = 20
        sbb EAX, EBX ; EAX = x - b = 20 - 5 = 15
        adc EAX, 8 ; EAX = x - b + 8 = 15 + 8 = 23
        ; EBX, ECX, EDX clear
        mov EBX, EAX ; EBX = x - b + 8
        ; EAX, ECX, EDX clear
        mov EAX, 0
        mov AX, [a]; AX = a
        mov DX, 2
        imul DX; DX:AX = AX * DX = 2 * a = 20
        push DX
        push AX
        pop EAX ; EAX = DX:AX => EAX = 2 * a = 20
        ; ECX, EDX clear
        mov ECX, EAX ; ECX = EAX
        ; EAX, EDX clear
        ; ECX = 2 * a,  EBX = x - b + 8
        mov EAX, 0
        mov AL, [b]
        cbw
        cwd
        cdq
        sbb ECX, EAX ; ECX = 2 * a - b
        ; EAX, EDX clear
        push ECX
        pop AX
        pop DX
        ; DX : AX = ECX
        ; ECX clear
        mov [temp], EBX
        ; ECX, EBX clear
        mov ECX, EAX
        mov AL, [b]
        imul AL ; AX = b * b
        mov BX, AX
        mov EAX, ECX
        idiv BX ; DX:AX / BX = AX quotient, DX remainder
        mov EBX, [temp]
        cwd ; EAX = AX
        sbb EBX, EAX;
        adc EBX, [e]
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
