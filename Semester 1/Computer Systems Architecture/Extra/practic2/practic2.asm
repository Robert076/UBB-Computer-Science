; move elements from a file to another, each char + 1
bits 32
global start

extern fopen, fclose, fread, fwrite, perror, exit
import fopen msvcrt.dll
import fclose msvcrt.dll
import fread msvcrt.dll
import fwrite msvcrt.dll
import perror msvcrt.dll
import exit msvcrt.dll

segment data use32 class=data
    inputfile db "file.txt", 0 ; input file
    inputmode db "r", 0        ; input mode
    outputfile db "out.txt", 0  ; output file
    outputmode db "w", 0        ; output mode
    filedescriptorinput dd -1 ; file descriptor input
    filedescriptoroutput dd -1 ; file descriptor output
    e db "error", 0
    c db 0

segment code use32 class=code
    start:
        push inputmode
        push inputfile
        call [fopen]
        add esp, 8
        
        cmp eax, 0
        je end1
        mov [filedescriptorinput], eax

        push outputmode
        push outputfile
        call [fopen]
        add esp, 8
        
        cmp eax, 0
        je end2
        mov [filedescriptoroutput], eax

        loop1:
            push dword [filedescriptorinput]
            push dword 1
            push dword 1
            push c
            call [fread]
            add esp, 16
            
            cmp eax, 0
            je end_loop1

            mov al, [c]
            cmp al, 20
            je nuincrement
            inc byte [c]
            nuincrement:
            push dword [filedescriptoroutput]
            push dword 1
            push dword 1
            push c
            call [fwrite]
            add esp, 16
            
        jmp loop1
        end_loop1:
        
        push dword[filedescriptoroutput]
        call [fclose]
        add esp, 4
        
        jmp ok
        end2:
            push e
            call [perror]
            add esp, 4
        ok:
            push dword[filedescriptorinput]
            call [fclose]
            add esp, 4
            jmp ok2
        end1:
            push e
            call [perror]
            add esp, 4
        ok2:
        
        push dword 0
        call [exit]