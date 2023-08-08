import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Algorithm {
    Maze maze;
    protected boolean found;
    protected Block start;
    protected Block end;
    protected Map<Block, Block> pred;
    protected List<List<Block>> map;
    protected AlgorithmType algorithmType;
    protected int pathLength;
    protected int nodesOpened;

    Algorithm(Maze maze) {
        this.maze = maze;
        this.start = maze.getStart();
        this.end = maze.getEnd();
        pred = new HashMap<>();
        map = maze.getMap();
        pathLength = 0;
        nodesOpened = 0;
        found = false;
    }

    abstract public void solve();

    protected void getPath() {
        Block current = end;

        while (current != start) {
            pathLength++;
            current.setBlockState(BlockState.PATH);
            animate();
            current = pred.get(current);
        }
    }

    protected void animate() {
        try {
            Thread.sleep(0, 1);
        } catch (InterruptedException ignored) {
        }
        maze.repaint();
    }

    protected void printInfo() {
        System.out.println(algorithmType);
        System.out.println("Nodes expanded: " + nodesOpened);
        System.out.println("Path length: " + pathLength);
        System.out.println("_________________________");
    }
}
