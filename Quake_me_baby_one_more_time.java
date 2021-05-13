//Tyler Dolph, Jaden Torres 2021
//  https://youtu.be/NATtwV2SDOM

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.KeyAdapter;              
import java.awt.event.KeyEvent;


public class Quake_me_baby_one_more_time{
//=============================================
    static int[][] grid = new int[25][25]; //grid that makes up the gameplay area
    static int[] windowSize = {600,600};
    static Color[] colors = new Color[]{Color.black, Color.orange, Color.blue};
    public static Player player = new Player(windowSize[0]/2f, windowSize[1]/2f,0f);
//=============================================
    public static void main(String[] args){
        grid[3][15]=1;
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
    public static void draw(Graphics graphics){
        Graphics2D g = (Graphics2D) graphics;
        int gridSize = 20;//windowSize[0]/grid.length;
        //draw the map ================================
        for(int y = 0; y < grid.length; y++){
            for(int x = 0; x < grid[y].length; x++){
                int col = grid[y][x];
                if(col>=1){ //zero is no block, so draw one if it's one or greater
                    g.setColor(colors[col]); //set the block color to it's value in colors
                    g.setStroke(new BasicStroke(gridSize)); //set line thiccness to gridsize
                    g.drawRect((x*gridSize)+(gridSize/2), (y*gridSize)+(gridSize/2), 0,0);
                    //the block size is zero, so it draws a filled square of the stroke size
                }
                //drawing the gridline
                g.setColor(colors[0]); //set color to black.
                g.setStroke(new BasicStroke(1f)); //set line thiccness
                g.drawRect(x*gridSize, y*gridSize, gridSize, gridSize);//draw gridline
            }
        }
        g.setColor(colors[2]);
        g.setStroke(new BasicStroke(3));
        g.drawRect((int)player.x(), (int)player.y(), 5, 5);
        //g.drawRoundRect((int)player.x(), (int)player.y(), 20, 20, 20, 20);

    }
//=============================================
    public static class Player{
        public float x; // x coordinate
        public float y; //y coordinate
        public float rot; //rotation
        Player(float x,float y,float rot){
            this.x=x;
            this.y=y;
            this.rot=rot;
        }
        public float x(){return x;}
        public float y(){return y;}
        public float rot(){return rot;}
    }
//=============================================
    public class KeyInput extends KeyAdapter {
        public void keyPressed(KeyEvent d){
            //do stuff if the d key is pressed
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