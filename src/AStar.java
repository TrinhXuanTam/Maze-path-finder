import java.util.HashMap;
import java.util.Map;

public class AStar extends Algorithm {
    private Map<Block, Integer> cost;
    private Map<Block, Integer> heuristic;

    AStar(Maze maze) {
        super(maze);
        algorithmType = AlgorithmType.A_Star;
        cost = new HashMap<>();
        heuristic = new HashMap<>();
    }

    private int calculate(Block block) {
        return Math.abs(block.getX() - end.getX()) + Math.abs(block.getY() - end.getY());
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

            heuristic.put(block, calculate(block));
            pred.put(block, prev);
            block.setBlockState(BlockState.OPEN);
            nodesOpened++;
            animate();
        }
    }

    private Block findBest() {
        Block best = cost.entrySet().iterator().next().getKey();
        int bestVal = Integer.MAX_VALUE;

        for (var pair : cost.entrySet()) {
            if (bestVal > cost.get(pair.getKey()) + heuristic.get(pair.getKey())) {
                bestVal = cost.get(pair.getKey()) + heuristic.get(pair.getKey());
                best = pair.getKey();
            }
        }

        return best;
    }

    @Override
    public void solve() {
        cost.put(start, 0);
        heuristic.put(start, calculate(start));
        Block best;

        while (!cost.isEmpty() && !heuristic.isEmpty()) {
            best = findBest();
            best.setBlockState(BlockState.CLOSED);
            animate();

            addNeighbour(map.get(best.getY() + 1).get(best.getX()), best);
            addNeighbour(map.get(best.getY() - 1).get(best.getX()), best);
            addNeighbour(map.get(best.getY()).get(best.getX() + 1), best);
            addNeighbour(map.get(best.getY()).get(best.getX() - 1), best);

            cost.remove(best);
            heuristic.remove(best);
        }

        getPath();
        printInfo();
    }
}
