public class PathElement {
    public int x;
    public int y;

    public PathElement predecessor;

    public PathElement(int x, int y, PathElement predecessor) {
        this.x = x;
        this.y = y;
        this.predecessor = predecessor;
    }

    public boolean isVertical() {
        return x == predecessor.x && y == predecessor.y + 1;
    }

    public boolean isHorizontal() {
        return x == predecessor.x + 1 && y == predecessor.y;
    }

    public boolean isDiagonal() {
        return x == predecessor.x + 1 && y == predecessor.y + 1;
    }


}