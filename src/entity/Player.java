package entity;

import main.GamePanel;
import main.KeyHandler;
import java.awt.*;
import java.awt.image.BufferedImage;



public class Player extends Entity{
    //THIS CLASS IS USED FOR PLAYER DIRECTIONS AND SHOWING PLAYER ON THE SCREEN

    //GamePanel gp;
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);    //if want to use NPC
        //this.gp = gp;   //delete this line if want NPC and keep one above
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);           //subtract half tile to be displayed at half screen
        screenY = gp.screenHeight/2 - (gp.tileSize/2);        //position to use for players starting position

        solidArea = new Rectangle();//used to set the players solid area to move through tiny spaces in between blocks
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;  //cut down so there is space to hit the block. Easier for player to move in between blocks. allows for more space to move

        attackArea.width = 36;
        attackArea.height = 36;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }
    public void setDefaultValues(){
        worldX = gp.tileSize*23;   //not screen position, this is players position on the world map. starting point
        worldY = gp.tileSize*21;    //21 rows and 22 columns for starting position
        speed = 4;
        direction = "down";

        //PLAYER STATUS
        maxLife = 6;        //1 life means half heart so 6 lives is 3 hearts
        life = maxLife;
    }
    public void getPlayerImage(){

        up1 = setup("/player/Hull_08Up",gp.tileSize,gp.tileSize);
        down1 =setup("/player/Hull_08Down",gp.tileSize,gp.tileSize);
        left1 = setup("/player/Hull_08Left",gp.tileSize,gp.tileSize);
        right1 = setup("/player/Hull_08Right",gp.tileSize,gp.tileSize);
    }
    public void getPlayerAttackImage(){
        attackup = setup("/player/Hull_08Up",gp.tileSize,gp.tileSize*2);
        attackdown =setup("/player/Hull_08Down",gp.tileSize,gp.tileSize*2);
        attackleft = setup("/player/Hull_08Left",gp.tileSize*2,gp.tileSize);
        attackright = setup("/player/Hull_08Right",gp.tileSize*2,gp.tileSize);
    }
    public void update(){
        if(attacking == true){
            attacking();
        }//MIGHT NEED TO DELTE IF STATEMENT, CHECK LATER
        else if(keyH.upPressed == true || keyH.downPressed == true ||
                keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true){
            if(keyH.upPressed == true){
                direction = "up";
            }
            else if(keyH.downPressed == true){
                direction = "down";
            }
            else if(keyH.leftPressed == true){
                direction = "left";
            }
            else if(keyH.rightPressed == true){
                direction = "right";
            }
            else if(keyH.enterPressed == true){

            }

            //CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);    //receive player class as entity

            //CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this,true);
            pickUpObject(objIndex);

            //CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this,gp.npc);
            interactNPC(npcIndex);

            //CHECK OPPONENT COLLISION
            int opponentIndex = gp.cChecker.checkEntity(this,gp.opponent); //send opponent array to detect opponent collision
            contactWithOpponent(opponentIndex);


            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if(collisionOn == false){
                switch (direction){
                    case "up": worldY -= speed; break;
                    case "down":worldY += speed;break;
                    case "left": worldX -= speed;break;
                    case "right":worldX += speed;break;
                }
            }
        }
    }
    public void attacking(){
        //SAVE THE CURRENT worldX, worldY and solid area
        int currentWorldX = worldX;
        int currentWorldY = worldY;
        int solidAreaWidth = solidArea.width;
        int solidAreaHeight = solidArea.height;
        switch (direction){
            case"up": worldY -= attackArea.height;break;
            case "down" : worldY += attackArea.height;break;
            case "left" : worldX -= attackArea.width; break;
            case "right" : worldX += attackArea.width; break;
        }
        //ATTACK AREA BECOMES SOLID AREA
        solidArea.width = attackArea.width;
        solidArea.height = attackArea.height;
        //CHECK OPPONENT COLLISION WITH THE UPDATE WORLX WORLDY AND SOLID AREA
        int opponentIndex = gp.cChecker.checkEntity(this,gp.opponent);
        damageOpponent(opponentIndex);
        worldX = currentWorldX;
        worldY = currentWorldY;
        solidArea.width = solidAreaWidth;
        solidArea.height = solidAreaHeight;



    }

    public void interactNPC(int i){
        //if(gp.keyH.enterPressed == true){ } put body in this if statement after looking at dialogue video to check bug
        if( i != 999){
            System.out.println("you are hitting an npc!");
        }
//        else{
//            attacking = true;
//        }
        //need to check again
    }
    public void contactWithOpponent(int i ){
        if(i != 999){
            //means player touched a monster
            //CAN TAKE OUT IF STATEMENT IF NOT WANT INVINCIBLE ATTRIBUTE BUT KEEP LIFE DAMAGE
            gp.playSE(5);
            life-=1;
        }
    }
    public void damageOpponent(int i ){
        if( i!= 999) {
            gp.opponent[i].life -=1;
            gp.opponent[i].damageReaction();
            if(gp.opponent[i].life <= 0){
                gp.opponent[i].dying = true;
            }
        }
    }
    public void pickUpObject(int i) {
        if (i != 999) {
            //use this for powerups and sound effects when picking them up. look at screenshot in phone
        }
    }
    public void draw(Graphics2D g2){
        ///used to draw images in each direction
//        g2.setColor(Color.white);//set to use for drawing objects
//        g2.fillRect(x,y,gp.tileSize,gp.tileSize);  //draw rectangle on the screen
        BufferedImage image = null;

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
        g2.drawImage(image,screenX,screenY,null);   // x and y do not change throughout the game.

    }
}