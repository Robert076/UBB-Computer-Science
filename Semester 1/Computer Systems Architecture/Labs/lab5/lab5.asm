bits 32
global start        
extern exit               
import exit msvcrt.dll    

segment data use32 class=data
    A db 2, -4, -3, 3, 4, 2, 6
    l1 equ $-A ; compute the length of A into l1
    B db 4, 5, -5, 7, -6, -2, 1
    l2 equ $-B ; compute the length of B into l2
    C times l1 + l2 db 0
    l3 db 0
    d db 255

segment code use32 class=code
    start:
        mov ECX, l1
        mov ESI, 0
        mov EDI, 0
        jecxz Sfarsit
            loop1:
                mov AL, [A + ESI]
                cbw ; AX = AL
                mov BL, 2
                idiv BL ; AX / 2
                cmp AH, 0
                jne end_1
                mov AL, [A + ESI]
                cmp byte AL, 0
                jge end_1
                mov AL, [A + ESI]
                mov [C + EDI], AL
                inc EDI
                end_1:
                inc ESI
            
            loop loop1
            mov ECX, l2
            mov ESI, 0
            loop2:
                mov AL, [B + ESI]
                cbw ; AX = AL
                mov BL, 2
                idiv BL ; AX / 2
                cmp AH, 0
                jne end_2
                mov AL, [B + ESI]
                cmp byte AL, 0
                jge end_2
                mov AL, [B + ESI]
                mov [C + EDI], AL
                inc EDI
                end_2:
                inc ESI
            
            loop loop2
        
        Sfarsit:
        
        push    dword 0      
        call    [exit]       
