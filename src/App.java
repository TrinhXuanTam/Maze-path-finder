import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class App extends JFrame {

    App() {
        setTitle("Maze Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuScreen();
    }

    private void menuScreen() {
        setSize(300, 150);
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        JLabel caption = new JLabel("Maze Solver");
        caption.setAlignmentX(Component.CENTER_ALIGNMENT);
        caption.setFont(new Font("Arial", Font.BOLD, 30));
        add(caption);

        String[] algOptions = {"BFS", "DFS", "Random Search", "Dijkstra", "Greedy Search", "A*"};
        JComboBox<String> algorithm = new JComboBox<>(algOptions);
        add(algorithm);

        String[] mazeOptions = {"0.txt", "4.txt", "6.txt", "26.txt", "36.txt", "42.txt", "72.txt", "84.txt", "114.txt", "220.txt", "332.txt"};
        JComboBox<String> path = new JComboBox<>(mazeOptions);
        add(path);

        JButton startButton = new JButton("start");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                solve((String) algorithm.getSelectedItem(), (String) path.getSelectedItem());
            }
        });

        add(startButton);
        setResizable(false);
        setVisible(true);
    }

    private void solve(String selectedAlgorithm, String path) {
        Algorithm algorithm;
        Maze maze = new Maze("./dataset/" + path);
        switch (selectedAlgorithm) {
            case "BFS":
                algorithm = new BFS(maze);
                break;
            case "DFS":
                algorithm = new DFS(maze);
                break;
            case "Random Search":
                algorithm = new RandomSearch(maze);
                break;
            case "Greedy Search":
                algorithm = new GreedySearch(maze);
                break;
            case "Dijkstra":
                algorithm = new Dijkstra(maze);
                break;
            default:
                algorithm = new AStar(maze);
                break;
        }

        SolvingScreen solvingScreen = new SolvingScreen(algorithm, maze);
    }


}
