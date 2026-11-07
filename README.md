# DeBUGGED README File

## Details
A Java-based interactive quiz game inspired by "Are You Smarter Than a 5th-Grader?" that tests your knowledge of Programming Paradigms and programming language concepts.

## Narrative
Due to a broken laptop and an urgent CMSC 13 submission due the next morning, a Computer Science student tests an old, archived computer in the university lab. The student stumbles upon an unfinished legacy executable named DoNotRun.exe and decides to execute it, causing an unforeseen series of mishaps.

## Compile and run via cmd
1. Go to project folder "PS C:\Users\ ...\CMSC-13MP>"
2. Type in the follwing to comipile: 
javac -Xlint:unchecked -d out (Get-ChildItem src -Recurse -Filter *.java | ForEach-Object { $_.FullName })
3. Type in the following to run the code:
java -cp "out;resources" app.Main

## How to Install The App
1. Go to CMSC-13MP\InnoPackage\Output and run Debugged_Setup
2. After set-up, you should see the debugged app icon
3. Double click to run

## Features
### Dual Gameplay Phases:

Phase 1 (Theoretical Mastery): 7 Acts (70 questions total) featuring 7 distinct cartoon opponents across 5 core programming paradigms (Procedural, Object-Oriented, Functional, Declarative, Imperative).

Phase 2 (Practical Application): 3 Stages (75 questions total) covering code prediction, syntax debugging, and paradigm mapping across C, Java, Python, and JavaScript.

### Character Roster:
Double D: Expert in Procedural Programming

Jimmy Neutron: Expert in Object-Oriented Programming (OOP)

BMO: Expert in Functional Programming

Dexter: Expert in Declarative Programming

Dr. Doofenshmirtz: Expert in Imperative Programming

Nigel Numbuh 1: Strategic advisor in Declarative & Imperative Paradigms

Morty Smith: Flexible buddy across OOP & Functional Paradigms

### Audio & Dynamic Sound Effects
Check Settings for volume adjustment!

### System Health & Scoring
Track progress via cumulative debug points across 5 system stability hearts.

