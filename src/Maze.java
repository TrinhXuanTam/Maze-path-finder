import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Maze extends JPanel {
    private List<List<Block>> map;
    private int blockSize = 10;
    private int height;
    private int width;
    private Block start;
    private Block end;
    public int offX = 0;
    public int offY = 0;

    public Maze(String path) {
        getInput(path);
        setPreferredSize(new Dimension(width * blockSize, height * blockSize));
    }

    private void getInput(String path) {
        // Get number of lines
        try (LineNumberReader reader = new LineNumberReader(new FileReader(path));
             BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            reader.skip(Integer.MAX_VALUE);

            map = new ArrayList<List<Block>>();
            height = reader.getLineNumber() - 2;
            int cnt = 0;
            String line;

            // Initialize blocks from text input
            while (height != cnt) {
                map.add(new ArrayList<Block>());
                line = bufferedReader.readLine();

                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == 'X') {
                        map.get(cnt).add(new Block(i, cnt, BlockRole.WALL));
                    } else {
                        map.get(cnt).add(new Block(i, cnt, BlockRole.GROUND));
                    }
                }
                cnt++;
            }

            width = map.get(0).size();

            // Get start and end
            Scanner parseInt;
            line = bufferedReader.readLine();
            parseInt = new Scanner(line).useDelimiter("\\D+");
            int x = parseInt.nextInt();
            int y = parseInt.nextInt();
            start = map.get(y).get(x);
            start.setBlockRole(BlockRole.START);
            line = bufferedReader.readLine();
            parseInt = new Scanner(line).useDelimiter("\\D+");
            x = parseInt.nextInt();
            y = parseInt.nextInt();
            end = map.get(y).get(x);
            end.setBlockRole(BlockRole.END);
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    private void drawBlock(Block block, Graphics g) {
        switch (block.getBlockRole()) {
            case WALL:
                g.setColor(Color.darkGray);
                break;
            case GROUND:
                g.setColor(Color.WHITE);
                break;
            case END:
                g.setColor(Color.RED);
                break;
            case START:
                g.setColor(Color.GREEN);
                break;
        }

        if (block.getBlockRole() != BlockRole.START && block.getBlockRole() != BlockRole.END) {
            switch (block.getBlockState()) {
                case CLOSED:
                    g.setColor(Color.orange);
                    break;
                case OPEN:
                    g.setColor(Color.yellow);
                    break;
                case PATH:
                    g.setColor(Color.BLUE);
                    break;
            }
        }

        g.fillRect((block.getX() + offX) * blockSize, (block.getY() + offY) * blockSize, blockSize, blockSize);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (var row : map) {
            for (var block : row) {
                drawBlock(block, g);
            }
        }
    }

    public int getOffX() {
        return offX;
    }

    public void setOffX(int offX) {
        this.offX = offX;
    }

    public int getOffY() {
        return offY;
    }

    public void setOffY(int offY) {
        this.offY = offY;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public Block getStart() {
        return start;
    }

    public Block getEnd() {
        return end;
    }

    public List<List<Block>> getMap() {
        return map;
    }

    public Maze getMaze() {
        return this;
    }
}
