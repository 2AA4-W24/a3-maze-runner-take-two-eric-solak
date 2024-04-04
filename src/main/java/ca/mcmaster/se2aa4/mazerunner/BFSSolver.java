package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class BFSSolver implements MazeSolver{
    private static final Logger logger = LogManager.getLogger();
    private boolean[][] marked;
    private Queue<Position> queue;
    private final Map<Position, PathInfo> hashMap = new HashMap<>();
    private Maze maze;
    private enum Compass {LEFT, RIGHT, FORWARD}

    @Override
    public Path solve(Maze maze) {
        this.maze = maze;
        this.queue = new LinkedList<>();
        marked = new boolean[maze.getSizeX()][maze.getSizeY()];
        logger.debug("Tracing path...");
        return tracePath();
    }

    /**
     * Create path from start to end using Breadth First Search
     *
     * @return Path from start to end
     */
    private Path tracePath() {
        Path path = new Path();
        PathInfo pathInfo = new PathInfo(path, Direction.RIGHT);
        Position pos = maze.getStart();
        marked[pos.x()][pos.y()] = true; // Mark the starting position as visited
        hashMap.put(pos, pathInfo); // Add the starting position to the HashMap
        queue.add(pos);

        while (!queue.isEmpty()) {
            pos = queue.remove();

            // If the end of the maze has been reached, return the path taken there
            // The path returned will always be the shortest, due to the logistics of Breadth First Search
            if (pos.x() == maze.getSizeX()) {
                PathInfo end = hashMap.get(maze.getEnd());
                return end.path();
            }

            PathInfo currentPathInfo = hashMap.get(pos);
            Direction currentDir = currentPathInfo.direction();
            Path currentPath = currentPathInfo.path();

            for (Compass compass : Compass.values()) {
                Path newPath = copyPath(currentPath);
                Position nextPos = pos;
                Direction nextDir = currentDir;

                switch(compass) {
                    case LEFT:
                        nextDir = currentDir.turnLeft();
                        nextPos = pos.move(nextDir);
                        newPath.addStep('L');
                        newPath.addStep('F');
                        break;
                    case RIGHT:
                        nextDir = currentDir.turnRight();
                        nextPos = pos.move(nextDir);
                        newPath.addStep('R');
                        newPath.addStep('F');
                        break;
                    case FORWARD:
                        if (isInBounds(pos.move(currentDir), maze.getSizeX(), maze.getSizeY())) {
                            newPath.addStep('F');
                            nextPos = pos.move(currentDir);
                        }
                        break;
                    }

                if (!marked[nextPos.x()][nextPos.y()] && isInBounds(nextPos, maze.getSizeX(), maze.getSizeY()) && !maze.isWall(nextPos)) {
                    marked[nextPos.x()][nextPos.y()] = true;

                    pathInfo = new PathInfo(newPath, nextDir);
                    hashMap.put(nextPos, pathInfo);
                    queue.add(nextPos);
                }
            }
        }


        PathInfo end = hashMap.get(maze.getEnd());
        if (end != null) {
            return end.path();
        } else {
            throw new RuntimeException("Invalid maze.");
        }
    }

    /**
     * Check if position is in the maze bounds.
     *
     * @param position Position to validate
     * @param sizeX    Maze horizontal (X) size
     * @param sizeY    Maze vertical (Y) size
     * @return If position is in bounds
     */
    private boolean isInBounds(Position position, int sizeX, int sizeY) {
        return position.x() >= 0 && position.x() < sizeX && position.y() >= 0 && position.y() < sizeY;
    }


    /**
     * Copy contents of a Path object to another Path object
     * @param oldPath Older path
     * @return Path object that is a replica of oldPath
     */
    private Path copyPath(Path oldPath) {
        Path newPath = new Path();
        for (char chars : oldPath.getPathSteps()) {
            newPath.addStep(chars);
        }
        return newPath;
    }

}
