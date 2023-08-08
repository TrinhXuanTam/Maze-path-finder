public class DFS extends Algorithm {
    DFS(Maze maze) {
        super(maze);
        algorithmType = AlgorithmType.DFS;
    }

    @Override
    public void solve() {
        dfs(start);
        getPath();
        printInfo();
    }

    private void dfs(Block current) {
        if (current == end || found) {
            current.setBlockState(BlockState.CLOSED);
            found = true;
            return;
        }

        current.setBlockState(BlockState.CLOSED);
        animate();

        dfsUtil(current, map.get(current.getY() + 1).get(current.getX()));
        dfsUtil(current, map.get(current.getY() - 1).get(current.getX()));
        dfsUtil(current, map.get(current.getY()).get(current.getX() + 1));
        dfsUtil(current, map.get(current.getY()).get(current.getX() - 1));
    }

    private void dfsUtil(Block prev, Block opened) {
        if ((opened.getBlockRole() == BlockRole.END || opened.getBlockRole() == BlockRole.GROUND) && opened.getBlockState() == BlockState.FRESH) {
            opened.setBlockState(BlockState.OPEN);
            pred.put(opened, prev);
            nodesOpened++;
            dfs(opened);
        }
    }
}
