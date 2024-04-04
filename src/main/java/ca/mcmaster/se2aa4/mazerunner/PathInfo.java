package ca.mcmaster.se2aa4.mazerunner;

/**
 * Holds information about the current Path:
 * - Path taken
 * - Current direction of the last position
 */
public record PathInfo(Path path, Direction direction) {
}
