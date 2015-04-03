# Introduction #

This page will have useful tips about tomcat

# Details #

how to attach a debugger in tomcat

in startup.bat

call "%EXECUTABLE%" jpda start %CMD\_LINE\_ARGS%

in catalina.bat

if not "%JPDA\_TRANSPORT%" == "" goto gotJpdaTransport
set JPDA\_TRANSPORT=_dt\_socket_
:gotJpdaTransport
if not "%JPDA\_ADDRESS%" == "" goto gotJpdaAddress
set JPDA\_ADDRESS=_9009_