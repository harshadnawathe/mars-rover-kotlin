# Mars Rover

This is a Kotlin implementation of the Mars Rover problem used in ThoughtWorks coding round.

Project contains two modules
- lib : Implements domain.
- app : A command line application to read input text from std in and print result on std out. 


## How to build?
```
gradle build
```

## How to run?
```
cat input.txt > java -jar app/build/libs/mars-rover-app-1.0-SNAPSHOT.jar

```

## Sample input
```
5 5

1 2 N

LMLMLMLMM

3 3 E

MMRMMRMRRM
```

## Sample output
```
1 3 N
5 1 E
```