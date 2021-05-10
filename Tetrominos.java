//Tyler Dolph, Jaden Torres 2021
//TODO Tyler: Tetromino spawning/active block flag, sidebar
//TODO Jaden: hard/soft drops, row completion
//TODO collab: rotation
//DONE: game window, gameplay grid, blocks/block colors

import javax.swing.*;
import java.awt.*;
public class Engine2D extends JFrame { 
    //configurable variables
    //probably dont touch windowSize or root ever. i certainly wont.
    static String windowName = "Tetrominos"; //copyright friendly and technically correct https://en.wikipedia.org/wiki/Tetromino
    Color backgroundColor = Color.WHITE;
    private Color[] colors = new Color[]{Color.black, Color.orange, Color.blue}; //colors (0 is black (the grid), 1 is orange, 2 is blue, etc)
    int[] windowSize = {391,391}; 
    int[] root = {14,37}; //top left corner of the gameplay grid (9, 32 is no gap. it's quirky like that)

    //systems variables
    int[][] grid = new int[10][20]; //grid that makes up the gameplay area

    public Tetrominos(){
        //make the window and set the size and background color (note: the background will probably need to be updated in the main loop with every frame)
        super(windowName);
        getContentPane().setBackground(backgroundColor);
        setSize(windowSize[0],windowSize[1]);
        //magic
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);





        //main loop goes here??????
    }
    //draw the board and blocks
    void draw(Graphics graphics){
        //=========================================================
        //test blocks
        grid[0]=new int[]{0,0,0,0,0,0,0,0,2,0}; //the blue one lol
        grid[1]=new int[]{0,0,0,0,1,0,0,0,2,0}; //im 12
        grid[2]=new int[]{0,0,0,1,1,1,0,2,0,2};
        //=========================================================
        Graphics2D g2d = (Graphics2D) graphics;
        int x = root[0]; int y = root[1]; 
        for(int[] row : grid){
            for(int col : row){ 
                if(x>=root[0]+200){x=root[0]; y+=20;} //go to the next row
                //drawing the block if there is one
                if(col>=1){ //zero is no block, so draw one if it's one or greater
                    g2d.setColor(colors[col]); //set the block color to it's value in colors
                    g2d.setStroke(new BasicStroke(11f)); //set line thiccness to 1
                    g2d.drawRect(x+7, y+7, 5, 5);//draw the block
                }
                //drawing the gridline
                g2d.setColor(colors[0]); //set color to black.
                g2d.setStroke(new BasicStroke(2f)); //set line thiccness to 2
                g2d.drawRect(x, y, 20, 20);//draw gridline
                x+=20; //ecks dot equals twenty
            }
        }
 
    }
    //=========================================================
    //me when
    public void paint(Graphics g){ //me when the
        super.paint(g);
        draw(g);
    }
    //=========================================================
    //spooky.
    public static void main(String[] args) throws Exception { //magic
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){new Tetrominos().setVisible(true);}
        });
    }
}

//░░░░░░░░░░░▄▀▄▀▀▀▀▄▀▄░░░░░░░░░░░░░░░░░░ 
//░░░░░░░░░░░█░░░░░░░░▀▄░░░░░░▄░░░░░░░░░░ 
//░░░░░░░░░░█░░▀░░▀░░░░░▀▄▄░░█░█░░░░░░░░░ 
//░░░░░░░░░░█░▄░█▀░▄░░░░░░░▀▀░░█░░░░░░░░░ 
//░░░░░░░░░░█░░▀▀▀▀░░░░░░░░░░░░█░░░░░░░░░ 
//░░░░░░░░░░█░░░░░░░░░░░░░░░░░░█░░░░░░░░░ 
//░░░░░░░░░░█░░░░░░░░░░░░░░░░░░█░░░░░░░░░ 
//░░░░░░░░░░░█░░▄▄░░▄▄▄▄░░▄▄░░█░░░░░░░░░░ 
//░░░░░░░░░░░█░▄▀█░▄▀░░█░▄▀█░▄▀░░░░░░░░░░ 
//░░░░░░░░░░░░▀░░░▀░░░░░▀░░░▀░░░░░░░░░░░░ 
//╔═════════════════════════════════════╗
//║ * You feel like you're going to     ║
//║ have a ruff time.                   ║
//║                                     ║
//╚═════════════════════════════════════╝
//┌───────┐ ┌───────┐ ┌───────┐ ┌───────┐
//│/ FIGHT| │ ) PET | |6 ITEM | |X MERCY| 
//└───────┘ └───────┘ └───────┘ └───────┘
