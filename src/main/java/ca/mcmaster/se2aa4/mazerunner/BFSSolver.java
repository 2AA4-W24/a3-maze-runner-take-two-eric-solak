package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class BFSSolver implements MazeSolver{
    private static final Logger logger = LogManager.getLogger();
    private boolean[][] marked;
    private Queue<Position> queue;
    private Maze maze;

    @Override
    public Path solve(Maze maze) {
        this.maze = maze;
        this.queue = new LinkedList<>();
        marked = new boolean[maze.getSizeY()][maze.getSizeX()];
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
        path.addStep('F');

        Direction dir = Direction.RIGHT;
        Position pos = maze.getStart();
        queue.add(pos);

        while (!queue.isEmpty()) {
            pos = queue.remove();
            for (int i = 0; i < 3; i++) {
                Position nextPos = pos;
                Direction nextDir = dir;
                switch(i) {
                    case 0:
                        nextDir = dir.turnRight();
                        nextPos = pos.move(dir.turnRight());
                        break;
                    case 1:
                        nextDir = dir.turnLeft();
                        nextPos = pos.move(dir.turnLeft());
                        break;
                    case 2:
                        if (isInBounds(pos.move(dir), maze.getSizeX(), maze.getSizeY())) {
                        nextPos = pos.move(dir);
                        }
                        break;
                    }

                if (!marked[nextPos.x()][nextPos.y()] && isInBounds(nextPos, maze.getSizeX(), maze.getSizeY()) && !maze.isWall(nextPos)) {
                    marked[nextPos.x()][nextPos.y()] = true;
                    logger.info(nextPos);
                    queue.add(nextPos);
                }
            }
        }
        return path;
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
}
