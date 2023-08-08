import javax.swing.*;
import java.awt.event.*;

public class SolvingScreen extends JDialog {
    private Maze maze;
    private boolean started = false;
    private Algorithm algorithm;

    SolvingScreen(Algorithm algorithm, Maze maze) {
        this.algorithm = algorithm;
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.maze = maze;
        add(maze);

        setTitle(algorithm.algorithmType.toString());
        setFocusable(true);
        addEvents();
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    private void addEvents() {
        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
                if (mouseWheelEvent.isControlDown()) {
                    if (mouseWheelEvent.getWheelRotation() < 0) {
                        maze.setBlockSize(maze.getBlockSize() + 1);
                    } else {
                        maze.setBlockSize(maze.getBlockSize() - 1);
                    }
                    repaint();
                }
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int speed = 2 * maze.getBlockSize();
                super.keyPressed(e);
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        maze.offX -= speed;
                        break;
                    case KeyEvent.VK_RIGHT:
                        maze.offX += speed;
                        break;
                    case KeyEvent.VK_DOWN:
                        maze.offY += speed;
                        break;
                    case KeyEvent.VK_UP:
                        maze.offY -= speed;
                        break;
                    case KeyEvent.VK_ESCAPE:
                        maze.offX = maze.offY = 0;
                        maze.setBlockSize(10);
                        break;
                    case KeyEvent.VK_SPACE:
                        if (!started) {
                            Thread run = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    algorithm.solve();
                                }
                            });
                            run.start();
                            started = true;
                        }
                        break;
                }

                repaint();
            }
        });
    }
}
