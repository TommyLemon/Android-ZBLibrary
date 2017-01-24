[General]
SyntaxVersion=2
BeginHotkey=121
BeginHotkeyMod=0
PauseHotkey=0
PauseHotkeyMod=0
StopHotkey=123
StopHotkeyMod=0
RunOnce=1
EnableWindow=
MacroID=bd5ab056-963f-4abf-915d-17f6e9e3f777
Description=adtImport
Enable=1
AutoRun=0
[Repeat]
Type=1
Number=1
[SetupUI]
Type=2
QUI=
[Relative]
SetupOCXFile=
[Comment]

[Script]
int N = 0


'==========以下是按键精灵录制的内容==========
MoveTo 158, 136
Delay 10
LeftClick 1

For i=1 To N
    //这里的脚本可以循环N次
Delay 20
KeyDown "Down", 1
Delay 10
KeyUp "Down", 1

Next


KeyDown "Ctrl", 1
KeyDown "Shift", 1
Delay 296
KeyDown "O", 1
Delay 110
KeyUp "O", 1
Delay 218
KeyUp "Shift", 1
Delay 31
KeyUp "Ctrl", 1
'==========以上是按键精灵录制的内容==========



N=N+1
Delay 1000
