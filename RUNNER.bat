@ECHO OFF
SET location1=F:\ExpensasPovarchicAbril\ExpensaV2.2\dist\informesTemplate
SET location3=F:\ExpensasPovarchicAbril\ExpensaV2.2\dist
DEL "%location1%\*.JAVA"
DEL "%location1%\*.BAK"
cd %location3%
start ExpensaV2.2.jar