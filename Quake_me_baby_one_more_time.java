//Tyler Dolph, Jaden Torres 2021
//  https://youtu.be/NATtwV2SDOM

import javax.swing.*;
import java.awt.*;

public class Quake_me_baby_one_more_time{
//=============================================
    static int[][] grid = new int[25][25]; //grid that makes up the gameplay area
    static int[] windowSize = {600,600};
    static Color[] colors = new Color[]{Color.black, Color.orange, Color.blue};
//=============================================
    public static void main(String[] args){
        grid[5][7]=1;
        JFrame frame = new JFrame();
        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());
        frame.setSize(windowSize[0],windowSize[1]);
        frame.setTitle("Quake Me Baby One More Time");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //=============================================
        JPanel renderPanel = new JPanel() {
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                draw(g2);
            }
        };
        pane.add(renderPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
//=============================================
static void draw(Graphics graphics){
    Graphics2D g2d = (Graphics2D) graphics;
    int gridSize = 20;//windowSize[0]/grid.length;
    //=============================================
    for(int y = 0; y < grid.length; y++){
        for(int x = 0; x < grid[y].length; x++){
            int col = grid[y][x];
            if(col>=1){ //zero is no block, so draw one if it's one or greater
                g2d.setColor(colors[col]); //set the block color to it's value in colors
                g2d.setStroke(new BasicStroke(gridSize)); //set line thiccness to gridsize
                g2d.drawRect((x*gridSize)+(gridSize/2), (y*gridSize)+(gridSize/2), 0,0);
                //the block size is zero, so it draws a filled square of the stroke size
            }
            //drawing the gridline
            g2d.setColor(colors[0]); //set color to black.
            g2d.setStroke(new BasicStroke(1f)); //set line thiccness
            g2d.drawRect(x*gridSize, y*gridSize, gridSize, gridSize);//draw gridline
            }
        }
    }
//=============================================
    class Player{
        double x;
        double y;
        double rot;
        Player(double x,double y,double rot){
            this.x=x;
            this.y=y;
            this.rot=rot;
        }
    }
//=============================================
}
//
//░▄▀▄▀▀▀▀▄▀▄░░░░░░░░░
//░█░░░░░░░░▀▄░░░░░░▄░
//█░░▀░░▀░░░░░▀▄▄░░█░█
//█░▄░█▀░▄░░░░░░░▀▀░░█
//█░░▀▀▀▀░░░░░░░░░░░░█
//█░░░░░░░░░░░░░░░░░░█
//█░░░░░░░░░░░░░░░░░░█
//░█░░▄▄░░▄▄▄▄░░▄▄░░█░
//░█░▄▀█░▄▀░░█░▄▀█░▄▀░
//░░▀░░░▀░░░░░▀░░░▀░░░