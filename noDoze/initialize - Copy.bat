@echo Off
title Server

"C:\Program Files (X86)\java\jdk1.6.0\bin\java.exe" -Xmx512m -XX:+DisableExplicitGC -noverify -cp bin;libs/xstream.jar;libs/xpp.jar;libs/mysql-connector.jar com.rs2.GameEngine
pause