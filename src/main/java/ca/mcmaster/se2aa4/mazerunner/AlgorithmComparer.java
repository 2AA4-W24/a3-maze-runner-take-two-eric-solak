package ca.mcmaster.se2aa4.mazerunner;

public class AlgorithmComparer {

    public float compareLength(Path baseline, Path method) {
        int baselineLength = 0;
        int methodLength = 0;

        // Get length of the path "baseline"
        for (char ignored : baseline.getPathSteps()) {
            baselineLength++;
        }

        // Get length of the path "method"
        for (char ignored : method.getPathSteps()) {
            methodLength++;
        }

        return (float) (baselineLength / methodLength);
    }

}
