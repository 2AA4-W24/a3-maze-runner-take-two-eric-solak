package ca.mcmaster.se2aa4.mazerunner;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");
        CommandLineParser parser = new DefaultParser();

        CommandLine cmd = null;
        try {
            cmd = parser.parse(getParserOptions(), args);
            String filePath = cmd.getOptionValue('i');


            if (cmd.getOptionValue("p") != null) {
                Maze maze = new Maze(filePath);
                logger.info("Validating path");
                Path path = new Path(cmd.getOptionValue("p"));
                if (maze.validatePath(path)) {
                    System.out.println("correct path");
                } else {
                    System.out.println("incorrect path");
                }
            } else {
                String method = cmd.getOptionValue("method", "righthand");
                if (cmd.getOptionValue("baseline") == null) {
                    Maze maze = new Maze(filePath);
                    Path path = solveMaze(method, maze);
                    System.out.println(path.getFactorizedForm());
                } else {
                    String baseline = cmd.getOptionValue("baseline");

                    float mazeStart = System.nanoTime();
                    Maze maze = new Maze(filePath);
                    float mazeEnd = System.nanoTime();
                    System.out.printf("Time to load maze: %.2f milliseconds\n", (mazeEnd - mazeStart) / 1e6);

                    float methodStart = System.nanoTime();
                    Path methodPath = solveMaze(method, maze);
                    float methodEnd = System.nanoTime();
                    System.out.printf("Time to solve using %s: %.2f milliseconds\n", method, (methodEnd - methodStart) / 1e6);

                    float baselineStart = System.nanoTime();
                    Path baselinePath = solveMaze(baseline, maze);
                    float baseLineEnd = System.nanoTime();
                    System.out.printf("Time to solve using %s: %.2f milliseconds\n", baseline, (baseLineEnd - baselineStart) / 1e6);

                    float pathSpeedup = comparePathLengths(baselinePath, methodPath);
                    System.out.printf("Speedup of path: %.2f\n", pathSpeedup);
                }
            }
        } catch (Exception e) {
            System.err.println("MazeSolver failed.  Reason: " + e.getMessage());
            logger.error("MazeSolver failed.  Reason: " + e.getMessage());
            logger.error("PATH NOT COMPUTED");
        }

        logger.info("End of MazeRunner");
    }

    /**
     * Solve provided maze with specified method.
     *
     * @param method Method to solve maze with
     * @param maze Maze to solve
     * @return Maze solution path
     * @throws Exception If provided method does not exist
     */
    private static Path solveMaze(String method, Maze maze) throws Exception {
        MazeSolver solver;
        switch (method) {
            case "righthand" -> {
                logger.debug("RightHand algorithm chosen.");
                solver = new RightHandSolver();
            }
            case "tremaux" -> {
                logger.debug("Tremaux algorithm chosen.");
                solver = new TremauxSolver();
            }
            case "bfs" -> {
                logger.debug("Breadth First Search algorithm chosen.");
                solver = new BFSSolver();
            }
            default -> {
                throw new Exception("Maze solving method '" + method + "' not supported.");
            }
        }

        logger.info("Computing path");
        return solver.solve(maze);
    }

    private static float comparePathLengths(Path baseline, Path method) {
        AlgorithmComparer comparer = new AlgorithmComparer();

        return comparer.compareLength(baseline, method);
    }


    /**
     * Get options for CLI parser.
     *
     * @return CLI parser options
     */
    private static Options getParserOptions() {
        Options options = new Options();

        Option fileOption = new Option("i", true, "File that contains maze");
        fileOption.setRequired(true);
        options.addOption(fileOption);

        options.addOption(new Option("p", true, "Path to be verified in maze"));
        options.addOption(new Option("method", true, "Specify which path computation algorithm will be used"));
        options.addOption(new Option("baseline", true, "Compare the speedup of algorithms to solve the maze"));

        return options;
    }
}
