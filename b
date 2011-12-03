#!/bin/sh
cd c
rm *.class
cd ..
javac *.java
echo "All Builds complete!"
mv *.class c
cd c
echo "All classes:"
ls

