public class Block {
    private BlockState blockState;
    private BlockRole blockRole;
    private int x;
    private int y;

    Block(int x, int y, BlockRole role) {
        this.x = x;
        this.y = y;
        blockState = BlockState.FRESH;
        blockRole = role;
    }

    public BlockState getBlockState() {
        return blockState;
    }

    public void setBlockState(BlockState blockState) {
        this.blockState = blockState;
    }

    public BlockRole getBlockRole() {
        return blockRole;
    }

    public void setBlockRole(BlockRole blockRole) {
        this.blockRole = blockRole;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
