@echo off
@title [%date% %time%] RuneSource Compiler

:compile
mkdir bin
echo [%date% %time%]: Compiling source code...
"C:\Program Files (X86)\java\jdk1.6.0\bin\java.exe" -jar libs/javac++.jar src libs bin
echo [%date% %time%]: Done!
pause