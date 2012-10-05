@echo Off
title Project No-Doze Registration server.
"C:\Program Files (X86)\java\jdk1.6.0\bin\java.exe" -Xmx32m -XX:+DisableExplicitGC -noverify -cp bin;libs/xstream.jar;libs/xpp.jar;libs/mysql-connector.jar com.rs2.net.RegisterListener
pause