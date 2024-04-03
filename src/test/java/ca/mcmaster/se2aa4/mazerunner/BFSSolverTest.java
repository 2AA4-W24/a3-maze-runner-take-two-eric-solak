package ca.mcmaster.se2aa4.mazerunner;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class BFSSolverTest {

    private final MazeSolver solver = new BFSSolver();
    private Maze maze;

    /**
     * Tests to see if implementation can solve a maze with no dead ends
     *
     * @throws Exception Invalid maze
     */
    @Test
    void solveDirectMaze() throws Exception {
        this.maze = new Maze("examples/direct.maz.txt");
        Path path = solver.solve(this.maze);

        assertNotNull(path);
    }

    /**
     * Tests to see if implementation can handle scaling up to a
     * large maze
     *
     * @throws Exception Invalid maze
     */
    @Test
    void solveLargeMaze() throws Exception {
        this.maze = new Maze("examples/large.maz.txt");
        Path path = solver.solve(this.maze);

        assertNotNull(path);
    }

    /**
     * Tests to see if implementation can solve a maze with uneven side lengths
     *
     * @throws Exception Invalid maze
     */
    @Test
    void solveRectangleMaze() throws Exception {
        this.maze = new Maze("examples/rectangle.maz.txt");
        Path path = solver.solve(this.maze);

        assertNotNull(path);
    }

    /**
     * Tests to see if implementation can solve a small maze with
     * dead ends
     *
     * @throws Exception Invalid maze
     */
    @Test
    void solveSmallMaze() throws Exception {
        this.maze = new Maze("examples/small.maz.txt");
        Path path = solver.solve(this.maze);

        assertNotNull(path);
    }

    /**
     * Tests to see if implementation can solve a direct maze (only move is forward)
     *
     * @throws Exception Invalid maze
     */
    @Test
    void solveStraightMaze() throws Exception {
        this.maze = new Maze("examples/straight.maz.txt");
        Path path = solver.solve(this.maze);

        assertNotNull(path);
    }

    /**
     * Tests to see if implementation will detect an invalid Maze input (null)
     */
    @Test
    void exceptNullMaze() {
        boolean exceptionThrown = false;

        try {
            solver.solve(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }

        assertTrue(exceptionThrown);
    }

    /**
     * Tests to see if implementation will detect an invalid Maze input (no exit)
     */
    @Test
    void exceptNoExitMaze() {
        boolean exceptionThrown = false;

        try {
            this.maze = new Maze("examples/noExit.maz.txt");
            solver.solve(this.maze);
        } catch (Exception e) {
            exceptionThrown = true;
        }

        assertTrue(exceptionThrown);
    }

}
