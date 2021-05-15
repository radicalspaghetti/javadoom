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
    static int[][] grid = new int[][]{
{2,2,2,2,2,2,2,2,2,2}, //grid that makes up the gameplay area
{2,0,0,0,0,0,0,0,0,2},
{2,0,2,0,2,2,2,2,0,2},
{2,0,2,0,0,0,0,2,0,2},
{2,0,2,0,0,0,0,2,0,2},
{2,0,2,0,0,0,0,2,0,2},
{2,0,2,0,0,0,0,2,0,2},
{2,0,2,2,2,2,2,2,0,2},
{2,0,0,0,0,0,0,0,0,2},
{2,2,2,2,2,2,2,2,2,2},
};
    static int[] windowSize = {973,680};
    static Color[] colors = new Color[]{Color.black, Color.orange, Color.PINK, Color.blue, Color.cyan};
    public static Player player = new Player(317, 317,0f);
    public static ArrayList<Integer> keys = new ArrayList<Integer>();
    public static int gridSize = 64;
//=============================================
    public static void main(String[] args){
        JFrame frame = new JFrame();
        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());
        frame.setTitle("Quake Me Baby One More Time");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.darkGray);
        frame.setSize(windowSize[0],windowSize[1]);
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

        //main loop TODO make loop speed not dependant on system performance
        float rotationSpeed = .00000055f;
        while(true){
            if(keys.contains(37)||keys.contains(65)){ //right rotation
                player.ang-=rotationSpeed;
                if(player.ang<.0001){player.ang+=Math.PI*2;}
                player.deltaX=(float)(Math.cos(player.ang)*5); 
                player.deltaY=(float)(Math.sin(player.ang)*5); 
            }
            if(keys.contains(39)||keys.contains(68)){ //left rotation
                player.ang+=rotationSpeed;
                if(player.ang>Math.PI*2){player.ang-=Math.PI*2;}
                player.deltaX=(float)(Math.cos(player.ang)*5); 
                player.deltaY=(float)(Math.sin(player.ang)*5); 
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
    public static void draw(Graphics graphics){
        Graphics2D g = (Graphics2D) graphics;
        //draw stuff here
        drawBackground(g); 
        drawMain(g); //3 D
        //drawMap(g); //used for debugging, will be turned into minimap later
    }
    //=============================================
    public static void drawBackground(Graphics2D g){
        g.setColor(Color.darkGray); //ground
        g.fillRect(0,windowSize[1]/2,windowSize[0],windowSize[1]); 
        g.setColor(new Color(75,0,130)); //sky
        g.fillRect(0,0,windowSize[0],windowSize[1]/2); 
    }
    //TODO make ray number more easily changable
    //TODO clean drawMain
    public static void drawMain(Graphics2D g){ //FIXME: at a certain player rotation, some of the raycasts break. why??? has I ever??????
        double radian = 0.0174533; //one radian in degrees, used for degree-to-radian conversion
        int lineY = 0; //I forgot what this does but if I remove it everything breaks
        double rayAng=player.ang-radian*30; //the angle of the first ray
        if(rayAng<0){rayAng+=2*Math.PI;} //TODO move this stuff to a func
        if(rayAng>2*Math.PI){rayAng-=2*Math.PI;}
        int dof, mx, my; //integers babeyyyyyyyyyyyyyyyyy
        int dofMax = 10; //maximum number of checks before moving on, stops infinite loops
        double dist = 0; //ray distance. should probably move this down a bit.
        double rayX = player.x, rayY=player.y, xOffset = 0f, yOffset = 0f; //doubles babeyyyyyyyyyyyyyyyyyyyyyyyyy
        for(int r=0; r<240; r++){ //raycasting time (it is spaghetti)
            //horizontal gridlines ================================
            dof=0;
            double aTan = -1/Math.tan(rayAng);
            double distH=Integer.MAX_VALUE; int hx=(int)player.x; int hy=(int)player.y;
            if(rayAng>Math.PI){ //looking up
                rayY=((((int)player.y>>6)<<6)-0.0001); //evil bitshifting. how vile.
                rayX=(player.y-rayY)*aTan+player.x;
                yOffset=-gridSize; xOffset=-yOffset*aTan;
            }
            else if (rayAng<Math.PI){ //looking down
                rayY=((((int)player.y>>6)<<6)+gridSize);
                rayX=(player.y-rayY)*aTan+player.x;
                yOffset=gridSize; xOffset=-yOffset*aTan;
            }
            if(rayAng==0||rayAng==Math.PI){rayX=player.x; rayY=player.y; dof=dofMax;}//looking directly right or left
            while(dof<dofMax){
                mx=(int)(rayX)>>6; my=(int)(rayY)>>6; //set x and y of ray's hit location
                if(mx>=0&&my>=0&&my<grid.length&&mx<grid[my].length&&grid[my][mx]>0){ //hit a wall :D
                    hx=(int)rayX; hy=(int)rayY; distH=dist(player.x,player.y,hx,hy,rayAng); dof=dofMax;
                } 
                else{rayX+=xOffset; rayY+=yOffset; dof+=1;} //didn't hit a wall, add offsets and inumerate dof
            }

            double PI2=Math.PI/2; double PI3=3*PI2;
            //vertical gridlines ================================
            dof=0;
            double nTan = Math.tan(rayAng)*-1;
            double distV=Integer.MAX_VALUE; int vx=(int)player.x; int vy=(int)player.y;
            if(rayAng>PI2&&rayAng<PI3){ //looking left
                rayX=(((int)player.x>>6)<<6)-0.0001; //evil bitshifting. how vile.
                rayY=(player.x-rayX)*nTan+player.y;
                xOffset=-gridSize; yOffset=-xOffset*nTan;
            }
            else if (rayAng<PI2||rayAng>PI3){ //looking right
                rayX=((((int)player.x>>6)<<6)+gridSize);
                rayY=(player.x-rayX)*nTan+player.y;
                xOffset=gridSize; yOffset=-xOffset*nTan;
            }
           // if(rayAng==0||rayAng==Math.PI){rayX=player.x; rayY=player.y; dof=dofMax;}//looking directly up or down
            while(dof<dofMax){
                mx=(int)(rayX)>>6; my=(int)(rayY)>>6; //set x and y of ray's hit location
                if(mx>=0&&my>=0&&my<grid.length&&mx<grid[my].length&&grid[my][mx]>0){
                    vx=(int)rayX; vy=(int)rayY; distV=dist(player.x,player.y,vx,vy,rayAng); dof=dofMax;
                } //hit a wall :D
                else{rayX+=xOffset; rayY+=yOffset; dof+=1;} //didn't hit a wall, add offsets and inumerate dof
            }
            //drawing rays ================================

            //getting the shortest distance and setting variables accordingly
                    //if(distH<distV&&(hx!=player.x&&hy!=player.y)){rayX=hx; rayY=hy;}
            if(distV<distH){rayX=vx; rayY=vy; dist = distV;}
            if(distV>distH){rayX=hx; rayY=hy; dist = distH;}
                    //else{rayX=player.x; rayY=player.y;}

            //shading OwO 
            //TODO only works with gray bc im lazy ill fix it later
            double vdo = .1; //view distance multiplier, higher is darker, 0 is no shading 
            int falloff = 15; //adds to the shaded color's brightness. might be useful if we use a color other than g r a y, or use a texture
            int original = 110; //original color of wall
            int shade = original;
            double shadeAmt = dist*vdo-falloff;
            if(shade-shadeAmt>0){shade-=shadeAmt;}
            else{shade=0;}
            //System.out.println(shade);
            Color shadedColor = new Color(shade,shade,shade);

            //correct fisheye effect
            double corrAng=player.ang-rayAng; //corrected angle
            if(corrAng<0){corrAng+=2*Math.PI;} //TODO move the radian correcting code to a function, the same thing is called a few times
            if(corrAng>2*Math.PI){corrAng-=2*Math.PI;}
            dist=dist*Math.cos(corrAng);

            //actually drawing them on the screen
            //FIXME this is actual barf that wrote at midnight I need to fix this laterrrrrrrrrrrrrrrr,...
            int xMult = 4; int yOffset2 = 400 ;
            g.setColor(shadedColor);
            g.setStroke(new BasicStroke(4));
            double lineHeight = gridSize*windowSize[1]/dist; //if(lineHeight > windowSize[0]){lineHeight=windowSize[0];}
            double lineOffset = windowSize[1]-lineHeight/2;
            g.drawLine(r*xMult, (int)lineOffset-yOffset2, r*xMult, (int)(lineHeight+lineOffset)-yOffset2);
            //g.drawLine((int)player.x,(int)player.y,(int)rayX,(int)rayY);

            //inumerating variables
            rayAng+=radian/4;
            lineY+=(windowSize[1]/240);
        }
    }
    //=============================================
    public static void drawMap(Graphics2D g){ //TODO make this into a minimap
        int scale = 1; //scale of the map
        int size = gridSize/scale;
        for(int y = 0; y < grid.length; y++){
            for(int x = 0; x < grid[y].length; x++){
                int col = grid[y][x];
                if(col>=1){ //zero is no block, so draw one if it's one or greater
                    g.setColor(colors[col]); //set the block color to it's value in colors
                    g.setStroke(new BasicStroke(size)); //set line thiccness to gridsize
                    g.drawRect((x*size)+(size/2), (y*size)+(size/2), 0,0);
                    //the block size is zero, so it draws a filled square of the stroke size
                }
                //drawing the gridlines
                g.setColor(colors[0]); //set color to black.
                g.setStroke(new BasicStroke(1f)); //set line thiccness
                g.drawRect(x*size, y*size, size, size);//draw gridline
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
    public static double dist(double ax, double ay, double bx, double by, double ang){return(Math.sqrt((bx-ax)*(bx-ax)+(by-ay)*(by-ay)));} //pythagorean theorem
//=============================================
    public static class Player{ //TODO: move player-related code to the player class to avoid spaghetti
        public float x; // x coordinate
        public float y; //y coordinate
        public float ang; //rotation
        public float deltaX = 1;
        public float deltaY = 1;
        Player(float x,float y,float rot){
            this.x=x;
            this.y=y;
            this.ang=rot;
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

//        /\_____/\
//       /  o   o  \
//      ( ==  ^  == )  < boo >
//       )         (
//      (           )
//     ( (  )   (  ) )
//    (__(__)___(__)__)

// fluffles (he is a good boy)