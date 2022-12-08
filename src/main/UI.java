package main;

import entity.Entity;
import object.Heart;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    //THIS CLASS HANDLES ALL THE HOME SCREEN UI TO DISLAY TEXT MESSAGE AND ITEM ICONS
    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80BOLD;
    BufferedImage heart_full, heart_half, heart_blank;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public int commandNum = 0;


    public UI(GamePanel gp){
        this.gp = gp;
        arial_40 = new Font("Arial",Font.PLAIN,40); //setting font before game start so it does not create a new instance FPS per second
        arial_80BOLD = new Font("Arial",Font.BOLD,80);
        //CREATE HUD OBJECT
        Entity heart = new Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }
    public void showMessage(String text){   //receives text and store into message string
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2){    //DO NOT INSTANTIATE IN THE LOOP, WILL CONTINOUSLY REFRESH. DO IT BEFORE
        this.g2 = g2;   //do this so we can use g2 in other methods in this class
        g2.setFont(arial_40);
        g2.setColor(Color.white);
        //TITLE STATE
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }
        if(gp.gameState == gp.playState){
            //DO PLAYSTATE STUFF
            drawPlayerLife();
        }
        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
            drawPlayerLife();
        }
        if(gp.gameState == gp.gameOverState){
            drawGameOverScreen();
        }

    }
    public void drawMiniMap(){

    }
    public void drawPlayerLife(){
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;  //half tile, starting point
        int i = 0;
        //DRAW MAXLIFE
        while(i < gp.player.maxLife/2){ //2 life mean 1 heart, 6 life is 3 heart
            g2.drawImage(heart_blank,x,y,null);
            i++;
            x += gp.tileSize;
        }
        //RESET VALUES
        x = gp.tileSize/2;
        y = gp.tileSize/2;  //half tile, starting point
        i = 0;

        //DRAW CURRENT LIFE
        while(i < gp.player.life){
            g2.drawImage(heart_half,x,y,null);  //DRAW HALF HEART FIRST
            i++;
            if(i < gp.player.life){
                g2.drawImage(heart_full,x,y,null);
            }
            i++;
            x += gp.tileSize;
        }

    }
    public void drawTitleScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
        String text = "Tank Game";
        int x = getXforCenteredText(text);
        int y = gp.tileSize*3;
        //SHADOW COLOR
        g2.setColor(Color.gray);
        g2.drawString(text,x+5,y+5);

        //MAIN COLOR
        g2.setColor(Color.white);
        g2.drawString(text,x,y);

        //DISPLAY TANK IMAGE
        x = gp.screenWidth/2 - (gp.tileSize*2)/2;//PLACE AT THE CENTER
        y += gp.tileSize*2;
        g2.drawImage(gp.player.down1,x,y,gp.tileSize*2,gp.tileSize*2,null);

        //DISPLAY MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F)); //START GAME
        text = "Start Game";
        x = getXforCenteredText(text);
        y += gp.tileSize*3.5;
        g2.drawString(text,x,y);
        g2.setColor(Color.gray);
        g2.drawString(text,x+1,y+2);//fade
        if(commandNum == 0){
            g2.drawString(">",x-gp.tileSize,y);
        }

        //QUIT GAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
        text = "QUIT";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text,x,y);
        g2.setColor(Color.gray);    //fade
        g2.drawString(text,x+1,y+2);
        if(commandNum == 1){
            g2.drawString(">",x-gp.tileSize,y);
        }
    }
    public void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text,x,y);
    }
    public int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x = gp.screenWidth/2 - length/2;    //so we can display at center of the screen
        return x;
    }
    public void drawGameOverScreen(){
        //REFERENCING RYISNOW VIDEO 37 ON YOUTUBE FOR DRAWING GAME OVER SCREEN
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,100F));

        text = "GAME OVER";
        //SHADOW
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.tileSize*4;
        g2.drawString(text,x,y);
        //MAIN
        g2.setColor(Color.white);
        g2.drawString(text,x-5,y-4);

        //BACK TO TITLE SCREEN
        text = "Quit";
        x = getXforCenteredText(text);
        y += 155;        //go down 55 tiles
        g2.drawString(text,x,y);
        if(commandNum == 1){
            g2.drawString(">",x-70,y);
        }
    }
}
