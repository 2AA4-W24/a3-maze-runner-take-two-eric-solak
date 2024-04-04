# Assignment A3 - Maze Runner Take Two

- Authors:
    - [Alexandre Lachance](lachaa2@mcmaster.ca)
    - [Sébastien Mosser](mossers@mcmaster.ca)
    - [Eric Solak](solake@mcmaster.ca)

* **Program**: B. Eng. In Software Engineering
* **Course code**: SFWRENG 2AA4
* **Course Title**: Software Design I - Introduction to Software Development
* Term: *Level II - Winter 2024*

## Specification

This program explores a maze, finding a path from an entry point to an exit one.

- The maze is stored in a text file, with `#` representing walls and `␣` (_empty space_) representing passages.
- You’ll find examples of such mazes in the [`examples`](./examples) directory.
    - You can also use the [Maze Generator](https://github.com/ace-lectures/maze-gen) to generate others.
- The Maze is surrounded by walls on its four borders, except for its entry/exit points.
    - Entry and exit points are always located on the East and West border.
    - The maze is not directed. As such, exit and entry can be interchanged.
- At the beginning of the exploration, we're located on the entry tile, facing the opposite side (e.g., if entering by
  the eastern entry, you're facing West).
- The program generates a sequence of instructions to reach the opposite exit (i.e., a "path"):
    - `F` means 'move forward' according to your current direction
    - `R` means 'turn right' (does not move, just change direction), and `L` means ‘turn left’.
- A canonical path contains only `F`, `R` and `L` symbols
- A factorized path squashes together similar instructions (i.e., `FFF` = `3F`, `LL` = `2L`).
- Spaces are ignored in the instruction sequence (only for readability: `FFLFF` = `FF L FF`)
- The program takes as input a maze and print the path on the standard output.
- The program can compare path computation methods (i.e. time, ratio of path lengths)

## How to run this software?

To build the program, simply package it with Maven:

```
mosser@azrael a3-maze-runner-take-two-eric-solak % mvn -q clean package 
```
#### Command line arguments

The program uses the following flags:
- `-i MAZE_FILE`: specifies the filename to be used;
- `-p PATH_SEQUENCE`: activates the path verification mode to validate that PATH_SEQUENCE is correct for the maze
- `-method {tremaux, righthand, bfs}`: specifies which path computation method to use. (default is right hand)
- `-baseline {tremaux, righthand, bfs}`: activates the baseline mode to compare two methods of maze traversal

When called on a non-existing file. it prints an error message

```
mosser@azrael a3-maze-runner-take-two-eric-solak % java -jar target/mazerunner.jar -i ./examples/small.maz.txtd
MazeSolver failed.  Reason: ./examples/huge.maz.txtd (No such file or directory)
```

#### Examples

When no logs are activated, the programs only print the computed path on the standard output.

```
mosser@azrael a3-maze-runner-take-two-eric-solak % java -jar target/mazerunner.jar -i ./examples/straight.maz.txt
4F
mosser@azrael a3-maze-runner-take-two-eric-solak %
```

If a given path is correct, the program prints the message `correct path` on the standard output.

```
mosser@azrael a3-maze-runner-take-two-eric-solak % java -jar target/mazerunner.jar -i ./examples/straight.maz.txt -p 4F
correct path
mosser@azrael a3-maze-runner-take-two-eric-solak %
```

If a given path is incorrect, the program prints the message `incorrect path` on the standard output.

```
mosser@azrael a3-maze-runner-take-two-eric-solak % java -jar target/mazerunner.jar -i ./examples/straight.maz.txt -p 3F
incorrect path
mosser@azrael a3-maze-runner-take-two-eric-solak %
```

When run in baseline mode, the program prints on the standard output the time to load the maze, the time to solve for both algorithms, and the path speedup.

```
mosser@azrael a3-maze-runner-take-two-eric-solak % java -jar target/mazerunner.jar -i ./examples/small.maz.txt -method bfs -baseline righthand
Time to load maze: 4.19 milliseconds
Time to solve using bfs: 7.34 milliseconds
Time to solve using righthand: 1.05 milliseconds
Speedup of path: 3.28
mosser@azrael a3-maze-runner-take-two-eric-solak %
```

