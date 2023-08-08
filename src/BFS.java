import java.util.*;

public class BFS extends Algorithm {
    private List<List<Block>> map;
    Queue<Block> queue;

    BFS(Maze maze) {
        super(maze);
        algorithmType = AlgorithmType.BFS;
        this.map = maze.getMap();
        queue = new LinkedList<>();
    }

    public void solve() {
        Block front;
        queue.offer(start);
        while (!queue.isEmpty()) {
            front = queue.peek();
            front.setBlockState(BlockState.CLOSED);
            animate();
            queue.poll();

            addNeighbour(map.get(front.getY() + 1).get(front.getX()), front);
            addNeighbour(map.get(front.getY() - 1).get(front.getX()), front);
            addNeighbour(map.get(front.getY()).get(front.getX() + 1), front);
            addNeighbour(map.get(front.getY()).get(front.getX() - 1), front);

        }

        getPath();
        printInfo();
    }

    private void addNeighbour(Block block, Block prev) {
        if ((block.getBlockRole() == BlockRole.GROUND || block.getBlockRole() == BlockRole.END) && block.getBlockState() == BlockState.FRESH && !found) {
            if (block == end) {
                found = true;
            }
            pred.put(block, prev);
            block.setBlockState(BlockState.OPEN);
            queue.offer(block);
            nodesOpened++;
            animate();
        }
    }
}
