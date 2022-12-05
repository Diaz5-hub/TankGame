package main;

import breakable.breakable;
import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable{
    //works as kind of a game screen
    //SCREEN SETTINGS
    final int originalTileSize = 16;    //16 x 16 tile
    final int scale = 3;    //used to scale resolution

    public final int tileSize = originalTileSize * scale;  //48x48 tile, actual size to be displayed
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;    //ratio 4x3 because 16x12
    public final int screenWidth = tileSize * maxScreenCol;    //48x16 = 768 pixels
    public final int screenHeight = tileSize * maxScreenRow;   //48x12 = 576 pixels

    //WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;


    //FPS
    int FPS = 60;

    TileManager tileM = new TileManager(this);

    public KeyHandler keyH = new KeyHandler(this);

    //SOUND
    Sound music = new Sound();
    Sound se = new Sound();

    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread;

    //ENTITY AND OBJECT
    public Player player = new Player(this,keyH);
    public Entity obj[] = new Entity[10];
    public Entity npc[] = new Entity[10];
    public Entity opponent[] = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();   //place all npc and objects into this list, one big entity list
    public ArrayList<Entity> projectileList = new ArrayList<>();
    public breakable breakablee[] = new breakable[20];

    //GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int gameOverState = 3;
    public final int mapState = 4;
    public final int splitScreen = 5;



    public GamePanel(){

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));    //set the size of this class(JPanel)
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);   //all drawing will be done in an offscreen painting buffer
        this.addKeyListener(keyH);  //adding key input
        this.setFocusable(true);    //can be focused to receive key input
    }

    public void setUpGame(){    //created this method so we can add other setup stuff in the future. handle all the objects
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setOpponent();
        //playMusic(0);
        gameState = titleState;
    }
    public void startGameThread(){
        gameThread = new Thread(this);  //passing gp class to thread constructor(this how instantiate thread)
        gameThread.start();
    }

    public void run(){
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime  = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;


        while(gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime)/drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >=1){
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if(timer >= 1000000000){
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer=0;
            }
        }
    }
    public void update(){
        if(gameState == playState){
            player.update();

            //NPC
            for (int i =0;i<npc.length;i++){
                if(npc[i] != null){
                    npc[i].update();
                }
            }
        }
        if(gameState == pauseState){
            //add stuff later
        }
        for (int i =0;i<opponent.length;i++){
            if(opponent[i] != null){
                if(opponent[i].alive == true && opponent[i].dying == false){
                    opponent[i].update();
                }
                if(opponent[i].alive == false){
                    opponent[i] = null;
                }
            }
        }

        for (int i =0;i<projectileList.size();i++){
            if(projectileList.get(i) != null){
                if(projectileList.get(i).alive == true ){
                    projectileList.get(i).update();
                }
                if(projectileList.get(i).alive == false){
                    projectileList.remove(i);
                }
            }
        }
        for(int i =0;i<breakablee.length;i++){
            if(breakablee[i] != null){
                breakablee[i].update();
            }
        }
    }
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; //2D to make control more sophisticated

        //DEBUG
        long drawStart = 0;
        if(keyH.checkDrawTime == true){
            drawStart = System.nanoTime();
        }

        //TITLE SCREEN
        if(gameState == titleState){
            ui.draw(g2);
        }
        else{
            //TILE
            tileM.draw(g2); //draw tiles then player so player is drawn on top. if done opposite, tiles will overpower player

            for(int i =0;i<breakablee.length;i++){
                if(breakablee[i] != null){
                    breakablee[i].draw(g2);
                }
            }

            //ADD ENTITIES TO THE LIST(OBJECTS AND NPC)
            entityList.add(player);
            for(int i =0;i < npc.length;i++){
                if(npc[i] != null){
                    entityList.add(npc[i]);
                }
            }
            for(int i =0;i< obj.length;i++){
                if(obj[i] != null){
                    entityList.add(obj[i]);
                }
            }
            for(int i =0;i< opponent.length;i++){
                if(opponent[i] != null){
                    entityList.add(opponent[i]);
                }
            }
            for(int i =0;i< projectileList.size();i++){
                if(projectileList.get(i) != null){
                    entityList.add(projectileList.get(i));
                }
            }

            //SORT
            Collections.sort(entityList, new Comparator<Entity>() { //can compare to entity list worldY to draw
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY,e2.worldY);
                    return result;
                }
            });

            //DRAW ENTITIES in order for pickup object to work. in order for it to objects to disappear
            for (int i =0;i<entityList.size();i++){
                entityList.get(i).draw(g2);
            }
            //EMPTY ENTITY LIST UNLESS IT WILL CONTINOUSLY LOOP
            entityList.clear();

            ui.draw(g2);
        }
        //DEBUG
        if(keyH.checkDrawTime == true) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw time: " + passed, 10, 400);
            System.out.println("Draw Time: " + passed);
        }

        g2.dispose();

    }
    public void playMusic(int i){
        music.setFile(i);
        music.play();
        //music.loop();
    }
    public void stopMusic(){
        music.stop();
    }
    public void playSE(int i){
        se.setFile(i);
        se.play();   //sound effect does not need loop as it should play once for a set amount of time
    }
}
