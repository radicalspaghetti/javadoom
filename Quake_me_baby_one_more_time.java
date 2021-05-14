//Tyler Dolph, Jaden Torres 2021
//  https://youtu.be/NATtwV2SDOM

import javax.swing.*;

//import jdk.internal.org.jline.utils.Colors;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.KeyAdapter;              
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Quake_me_baby_one_more_time{
//=============================================
    static int[][] grid = new int[25][25]; //grid that makes up the gameplay area
    static int[] windowSize = {517,540};
    static Color[] colors = new Color[]{Color.black, Color.orange, Color.pink, Color.yellow, Color.cyan};
    public static Player player = new Player(windowSize[0]/2f, windowSize[1]/2f,0f);
    public static ArrayList<Integer> keys = new ArrayList<Integer>();
    public static int gridSize = 20;
//=============================================
    public static void main(String[] args){
        grid[3][15]=2;
        JFrame frame = new JFrame();
        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());
        frame.setSize(windowSize[0],windowSize[1]);
        frame.setTitle("Quake Me Baby One More Time");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.darkGray);
        //keyboard stuff
        KeyListener input = new Input();
		frame.addKeyListener(input);
		frame.setFocusable(true);
        //=============================================
        JPanel renderPanel = new JPanel() {
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                draw(g2);
            }
        };
        pane.add(renderPanel, BorderLayout.CENTER);
        frame.setVisible(true);

        //main loop
        float rotationSpeed = .00000085f;
        while(true){
            if(keys.contains(37)||keys.contains(65)){ //right rotation
                player.rot-=rotationSpeed;
                if(player.rot<.0001){player.rot+=Math.PI*2;}
                player.deltaX=(float)(Math.cos(player.rot)*5); 
                player.deltaY=(float)(Math.sin(player.rot)*5); 
            }
            if(keys.contains(39)||keys.contains(68)){ //left rotation
                player.rot+=rotationSpeed;
                if(player.rot>Math.PI*2){player.rot-=Math.PI*2;}
                player.deltaX=(float)(Math.cos(player.rot)*5); 
                player.deltaY=(float)(Math.sin(player.rot)*5); 
            }
            //TODO movement
           // System.out.println(keys.toString() +"  "+ player.rot);
           //System.out.println(frame.getHeight()+" "+frame.getWidth());
            renderPanel.removeAll();
            renderPanel.revalidate();
            renderPanel.repaint();
        }
    }
//=============================================
//note: current rendering code should be recycled for minimap
    public static void draw(Graphics graphics){
        //draw the background
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(Color.darkGray);
        g.setStroke(new BasicStroke(520));
        g.drawRect(0,0,windowSize[0],windowSize[1]);
        //draw the main window here ================================
        double rayRot = player.rot;
        int dof, mx, my;
        int dofMax = 8;
        double rayX, rayY, xOffset, yOffset;
        for(int r=0; r<1; r++){ //raycasting time babeyyyyyy
            //horizontal gridlines ================================
            dof=0;
            double aTan = -1/Math.tan(rayRot);
            if(rayRot==0||rayRot==Math.PI){rayX=player.x; rayY=player.y; dof=dofMax;}//looking directly right or left
            if(rayRot>Math.PI){ //looking up
                rayY=((((int)player.y>>6)<<6)-0.0001); //evil bitshifting. how vile. also it breaks everything. //TODO replace bitshifting
                rayX=(player.y-rayY)*aTan+player.x;
                yOffset=-gridSize; xOffset=-yOffset*aTan;
            }
            else{ //looking down
                rayY=((((int)player.y>>6)<<6)+gridSize); //here too.
                rayX=(player.y-rayY)*aTan+player.x;
                yOffset=gridSize; xOffset=-yOffset*aTan;
            }
            while(dof<dofMax){
                mx=(int)(rayX)>>6; my=(int)(rayY)>>6;
                if(my<grid.length&&mx<grid[my].length&&grid[my][mx]>0){dof=dofMax;} //hit a wall :D
                else{rayX+=xOffset; rayY+=yOffset; dof+=1;} //didn't hit a wall, add offsets and inumerate dof
            }
            g.setColor(Color.orange);
            g.setStroke(new BasicStroke(1));
            g.drawLine((int)player.x,(int)player.y,(int)rayX,(int)rayY);
        }
        //================================
        drawMap(g);
    }
    public static void drawMap(Graphics2D g){
        for(int y = 0; y < grid.length; y++){
            for(int x = 0; x < grid[y].length; x++){
                int col = grid[y][x];
                if(col>=1){ //zero is no block, so draw one if it's one or greater
                    g.setColor(colors[col]); //set the block color to it's value in colors
                    g.setStroke(new BasicStroke(gridSize)); //set line thiccness to gridsize
                    g.drawRect((x*gridSize)+(gridSize/2), (y*gridSize)+(gridSize/2), 0,0);
                    //the block size is zero, so it draws a filled square of the stroke size
                }
                //drawing the gridlines
                g.setColor(colors[0]); //set color to black.
                g.setStroke(new BasicStroke(1f)); //set line thiccness
                g.drawRect(x*gridSize, y*gridSize, gridSize, gridSize);//draw gridline
            }
        }
        //draw the player's direction line =========================
        int line = 10;
        g.setColor(colors[4]);
        g.setStroke(new BasicStroke(2));
        g.drawLine((int)player.x,(int)player.y,(int)(player.x+player.deltaX*line),(int)(player.y+player.deltaY*line));
        //draw the player on the map================================
        g.setColor(colors[3]);
        g.setStroke(new BasicStroke(10));
        g.drawRect((int)player.x, (int)player.y, 0, 0); //draw the player
    }
//=============================================
    public static class Player{ //TODO: move player-related code to the player class to avoid spaghetti
        public float x; // x coordinate
        public float y; //y coordinate
        public float rot; //rotation
        public float deltaX = 1;
        public float deltaY = 1;
        Player(float x,float y,float rot){
            this.x=x;
            this.y=y;
            this.rot=rot;
        }
    }
//=============================================
    public static class Input implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {}//balls uwu (I don't even know what this does. it is mysterious.)
        @Override
        public void keyPressed(KeyEvent e) {
            //System.out.println("key pressed: "+e.getKeyCode());
            if(!keys.contains(e.getKeyCode())){
                keys.add(e.getKeyCode());
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {
            //System.out.println("key released: "+e.getKeyCode());
            keys.remove(keys.indexOf(e.getKeyCode()));
        }
    }
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