import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomSearch extends Algorithm {
    private Random random;
    private List<Block> opened;

    RandomSearch(Maze maze) {
        super(maze);
        algorithmType = AlgorithmType.Random_Search;
        random = new Random();
        opened = new ArrayList<>();
    }

    @Override
    public void solve() {
        Block randomBlock;
        int randomIndex;
        opened.add(start);
        while (!opened.isEmpty()) {
            randomIndex = random.nextInt(opened.size());
            randomBlock = opened.get(randomIndex);
            randomBlock.setBlockState(BlockState.CLOSED);
            opened.remove(randomIndex);
            animate();

            addNeighbour(map.get(randomBlock.getY() + 1).get(randomBlock.getX()), randomBlock);
            addNeighbour(map.get(randomBlock.getY() - 1).get(randomBlock.getX()), randomBlock);
            addNeighbour(map.get(randomBlock.getY()).get(randomBlock.getX() + 1), randomBlock);
            addNeighbour(map.get(randomBlock.getY()).get(randomBlock.getX() - 1), randomBlock);
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
            opened.add(block);
            nodesOpened++;
            animate();
        }
    }
}
