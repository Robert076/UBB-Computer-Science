bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, scanf, printf              ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import scanf msvcrt.dll                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import printf msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    n dd 0h
    m dd 0h
    rf db "%d", 0
    pf db "%d", 0
    pfs db "%s", 0
    space db " ", 0
; our code starts here
segment code use32 class=code
    start:
        push dword n
        push dword rf
        call [scanf]
        add ESP, 4 * 2 ; reading n from the console
        
        push dword m
        push dword rf
        call [scanf]
        add ESP, 4 * 2 ; reading m from the console
     
        push dword [n]
        pop AX
        pop BX ; putting n in BX:AX
        
        push dword [m]
        pop CX
        pop DX ; putting m in DX:CX
        
        add AX, CX
        sub BX, DX ; computing the requested sum and difference
        
        push word 0
        push AX
        push dword pf
        call [printf]
        add ESP, 4 * 2 ; printing the sum
        
        push dword space
        push dword pfs
        call [printf]
        add ESP, 4 * 2
        
        push word 0
        push BX
        push dword pf
        call [printf]
        add ESP, 4 * 2 ; printing the difference
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
