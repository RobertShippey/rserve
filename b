#!/bin/sh
echo "Starting..."
cd c
rm *.class
cd ..
javac *.java >z
mv *.class c
cd c
ls
echo "Done!\a"
