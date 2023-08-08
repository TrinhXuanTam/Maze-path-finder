import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GreedySearch extends Algorithm {
    private Map<Block, Integer> heuristic;

    GreedySearch(Maze maze) {
        super(maze);
        algorithmType = AlgorithmType.Greedy_Search;
        heuristic = new HashMap<>();
    }

    private int calculate(Block block) {
        return Math.abs(block.getX() - end.getX()) + Math.abs(block.getY() - end.getY());
    }

    private void addNeighbour(Block block, Block prev) {
        if ((block.getBlockRole() == BlockRole.GROUND || block.getBlockRole() == BlockRole.END) && !found && block.getBlockState() != BlockState.CLOSED) {
            if (block == end) {
                found = true;
            }

            heuristic.put(block, calculate(block));
            pred.put(block, prev);
            block.setBlockState(BlockState.OPEN);
            nodesOpened++;
            animate();
        }
    }

    @Override
    public void solve() {
        heuristic.put(start, calculate(start));
        Block best;

        while (!heuristic.isEmpty()) {
            best = Collections.min(heuristic.entrySet(), Map.Entry.comparingByValue()).getKey();
            best.setBlockState(BlockState.CLOSED);
            animate();

            addNeighbour(map.get(best.getY() + 1).get(best.getX()), best);
            addNeighbour(map.get(best.getY() - 1).get(best.getX()), best);
            addNeighbour(map.get(best.getY()).get(best.getX() + 1), best);
            addNeighbour(map.get(best.getY()).get(best.getX() - 1), best);
            heuristic.remove(best);
        }

        getPath();
        printInfo();
    }
}
