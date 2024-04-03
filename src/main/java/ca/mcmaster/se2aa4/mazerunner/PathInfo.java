package ca.mcmaster.se2aa4.mazerunner;

public class PathInfo {
    private Path path;
    private Direction direction;

    public PathInfo(Path path, Direction direction) {
        this.path = path;
        this.direction = direction;
    }

    public Path getPath() {
        return this.path;
    }

    public Direction getDirection() {
        return this.direction;
    }
}
