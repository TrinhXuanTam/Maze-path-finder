import java.util.*;

public class Dijkstra extends Algorithm {
    private Map<Block, Integer> cost;

    Dijkstra(Maze maze) {
        super(maze);
        algorithmType = AlgorithmType.Dijkstra;
        cost = new HashMap<>();
    }

    private void addNeighbour(Block block, Block prev) {
        if ((block.getBlockRole() == BlockRole.GROUND || block.getBlockRole() == BlockRole.END) && !found && block.getBlockState() != BlockState.CLOSED) {
            if (!cost.containsKey(block) || cost.get(block) > cost.get(prev) + 1) {
                cost.put(block, cost.get(prev) + 1);
            } else {
                return;
            }

            if (block == end) {
                found = true;
            }
            pred.put(block, prev);
            block.setBlockState(BlockState.OPEN);
            nodesOpened++;
            animate();
        }
    }

    @Override
    public void solve() {
        cost.put(start, 0);
        Block lowest;

        while (!cost.isEmpty()) {
            lowest = Collections.min(cost.entrySet(), Map.Entry.comparingByValue()).getKey();
            lowest.setBlockState(BlockState.CLOSED);
            animate();

            addNeighbour(map.get(lowest.getY() + 1).get(lowest.getX()), lowest);
            addNeighbour(map.get(lowest.getY() - 1).get(lowest.getX()), lowest);
            addNeighbour(map.get(lowest.getY()).get(lowest.getX() + 1), lowest);
            addNeighbour(map.get(lowest.getY()).get(lowest.getX() - 1), lowest);
            cost.remove(lowest);
        }

        getPath();
        printInfo();
    }
}
