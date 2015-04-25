@ECHO OFF
SET location1=F:\ExpensasPovarchicAbril\ExpensaV2.2\dist\informesTemplate
SET location2=F:\ExpensasPovarchicAbril\ExpensaV2.2\dist
DEL "%location1%\*.JAVA"
DEL "%location1%\*.BAK"
cd %location2%
start ExpensaV2.2.jar