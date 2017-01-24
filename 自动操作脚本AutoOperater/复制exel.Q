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
MacroID=5e634ba8-e053-4d9a-9e78-915606f62511
Description=复制exel
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
'==========以下是按键精灵录制的内容==========


INT N = 1


MoveTo 328, 128
Delay 100
LeftDown 1
MoveTo 329, 127
Delay 140
LeftUp 1
LeftUp 1
MoveTo 284, 219
Delay 100
LeftClick 1

For i=0 To N
    //这里的脚本可以循环
Delay 50
KeyDown "Down", 1
Delay 10
KeyUp "Down", 1

Next
Delay 100

KeyDown "Ctrl", 1
Delay 100
KeyDown "C", 1
Delay 78
KeyUp "C", 1
Delay 94
KeyUp "Ctrl", 1
MoveTo 594, 127
Delay 100
LeftClick 1
Delay 100
KeyDown "Ctrl", 1
Delay 100
KeyDown "F", 1
MoveTo 1114, 604
Delay 105
KeyUp "F", 1
Delay 140
KeyDown "V", 1
Delay 140
KeyUp "V", 1
Delay 78
KeyUp "Ctrl", 1
Delay 100
KeyDown "Enter", 1
Delay 110
KeyUp "Enter", 1
Delay 500
KeyDown "Esc", 1
Delay 110
KeyUp "Esc", 1
Delay 100
KeyDown "Right", 1
Delay 10
KeyUp "Right", 1
Delay 50
KeyDown "Right", 1
Delay 10
KeyUp "Right", 1
Delay 50
KeyDown "Right", 1
Delay 10
KeyUp "Right", 1
Delay 50
KeyDown "Right", 1
Delay 10
KeyUp "Right", 1
Delay 50



MoveTo 328, 128
Delay 100
LeftDown 1
MoveTo 329, 127
Delay 140
LeftUp 1
LeftUp 1
MoveTo 284, 219
Delay 100
LeftClick 1

For i=0 To N
    //这里的脚本可以循环
Delay 50
KeyDown "Down", 1
Delay 10
KeyUp "Down", 1

Next
Delay 100




KeyDown "Right", 1
Delay 10
KeyUp "Right", 1
Delay 50
KeyDown "Right", 1
Delay 10
KeyUp "Right", 1
Delay 50
KeyDown "Ctrl", 1
Delay 100
KeyDown "C", 1
Delay 109
KeyUp "C", 1
Delay 47
KeyUp "Ctrl", 1
MoveTo 654, 131
Delay 100
LeftClick 1
LeftUp 1
MoveTo 653, 134
Delay 100
KeyDown "Ctrl", 1
Delay 100
KeyDown "V", 1
Delay 114
KeyUp "V", 1
Delay 62
KeyUp "Ctrl", 1
'==========以上是按键精灵录制的内容==========

Delay 600

N=N+1