How to compile:
basta ig copy paste la ine: "javac -d out (Get-ChildItem src -Recurse -Filter *.java | ForEach-Object { $_.FullName })"

How to run:
copy paste la gihap: "java -cp out app.Main"