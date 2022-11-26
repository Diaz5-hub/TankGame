package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {
    //this class stores variables that will be used in player and other player or NPC classes

    GamePanel gp;   //if want to have NPC

    public int speed;
    public BufferedImage up1,down1,left1,right1;   //describes an image with an accessible buffer of image data
    public BufferedImage attackup, attackdown, attackleft,attackright;
    public Rectangle solidArea = new Rectangle(0,0,48,48);  //so all objects can share
    public int solidAreaDefaultX, solidAreaDefaultY;
    public int actionLockCounter = 0;
    public boolean invincible = false;
    public int invincibleCounter = 0;
    public BufferedImage image,image2,image3;
    public boolean collision = false;
    public Rectangle attackArea = new Rectangle(0,0,0,0);

    //STATE
    public int worldX,worldY;   //used for world map, different from screenX and Y
    public String direction = "down";
    public boolean collisionOn = false;
    public boolean attacking = false;   //PRESS ENTER TO ATTACK
    public boolean alive = true;
    public boolean dying = false;
    public boolean hpBarOn = true;


    //CHARACTER ATTRIBUTES
    public int maxLife;
    public int life;
    public int type;    //2 is opponent
    public String name;
    int dyingCounter =0;
    int hpBarCounter = 0;
    public Projectile projectile;
    public int useCost;
    public int shotAvailableCounter = 0;



    public Entity(GamePanel gp){
        this.gp = gp;
    }
    public void setAction(){

    }
    public void damageReaction(){

    }
    public void update(){
        setAction();
        collisionOn = false;
        gp.cChecker.checkTile(this);    //passing npc as entity
        gp.cChecker.checkObject(this,false);
        gp.cChecker.checkEntity(this,gp.npc);
        gp.cChecker.checkEntity(this,gp.opponent);  //CHECK COLLISION BETWEEN ENTITIES(OPPONENT AND BAT)
        //int checkBreakableWallHit = gp.cChecker.checkEntity(projectile,gp.breakablee);    might be able to use this to check against breakable wall
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if(this.type == 2 && contactPlayer == true){    //if contacted player from opponent, damage is resulted
            damagePlayer(4);    //get 4 damage
        }
//        if(checkBreakableWallHit == 0){
//            System.out.println("hit breakable wall");
//        }
        if(collisionOn == false){
            switch (direction){
                case "up": worldY -= speed; break;
                case "down":worldY += speed;break;
                case "left": worldX -= speed;break;
                case "right":worldX += speed;break;
            }
        }
        if(invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 40){
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(shotAvailableCounter < 30){
            shotAvailableCounter++;
        }
    }
    public void damagePlayer(int attack){
        if(gp.player.invincible == false) {
            gp.playSE(5);
            gp.player.life -= 1;
            gp.player.invincible = true;
        }
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;    //where on the screen we draw it
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        //if worldX:500 worldY:500 then player is 500 pixels away from 0:0 tiles
        //screenX 0 and screenY is top left corner
        //add player.screenX and y to offset since the player is drawn at the center
        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
            switch (direction){
                case "up":
                    image = up1;
                    break;
                case "down":
                    image = down1;
                    break;
                case "left":
                    image = left1;
                    break;
                case "right":
                    image = right1;
                    break;
            }

            //Opponent health bar
            if(type == 2 && hpBarOn == true) {
                double oneScale = (double)gp.tileSize/maxLife;  //divide by maxlife to know the length of 1 hp
                double hpBarValue = oneScale*life;

                g2.setColor(new Color(35,25,35));
                g2.fillRect(screenX-1,screenY-16,gp.tileSize+2,12); //setting outline for health bar

                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10); //-15 to display right above opponent

                hpBarCounter++;

                if(hpBarCounter > 600){
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }
            //add when adding invincible, need to change in order for hpbar to show at all times for opponent, right now only showing when hit
            if(invincible == true){
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2,0.4f);
            }


            if(dying == true){
                //dying animation
                dyingAnimation(g2);
            }
            g2.drawImage(image,screenX,screenY,gp.tileSize,gp.tileSize,null);
            changeAlpha(g2,1F);
        }
    }
    public void dyingAnimation(Graphics2D g2){
        //add blinking effect when dying
        dyingCounter++;

        int i = 5;

        if(dyingCounter <= i){
            changeAlpha(g2,0f);
        }
        if(dyingCounter > i && dyingCounter <= i*2){
            changeAlpha(g2,1f);
        }
        if(dyingCounter > i*2 && dyingCounter <= i*3){
            changeAlpha(g2,0f);
        }
        if(dyingCounter > i*3 && dyingCounter <= i*4){
            changeAlpha(g2,1f);
        }
        if(dyingCounter > i*4 && dyingCounter <= i*5){
            changeAlpha(g2,0f);
        }
        if(dyingCounter > i*5 && dyingCounter <= i*6){
            changeAlpha(g2,1f);
        }
        if(dyingCounter > i*6 && dyingCounter <= i*7){
            changeAlpha(g2,0f);
        }
        if(dyingCounter > i*7 && dyingCounter <= i*8){
            changeAlpha(g2,1f);
        }
        if(dyingCounter > i*8){
            alive = false;
        }
    }
    public void changeAlpha(Graphics2D g2,float alphaValue){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alphaValue));

    }

    public BufferedImage setup(String imagePath,int width,int height){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image,width,height);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }
}
