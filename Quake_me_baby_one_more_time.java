import javax.swing.*;
import java.awt.*;

public class Quake_me_baby_one_more_time{
    int[][] grid = new int[10][20]; //grid that makes up the gameplay area
    public static void main(String[] args){
        JFrame frame = new JFrame();
        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());

        JPanel renderPanel = new JPanel() {
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);
            }
        };
    }
}