package opponentmain;

import entity.Entity;
import main.GamePanel;
import object.FireBall;

import java.util.Random;

public class OPP_Tank extends Entity {
    GamePanel gp;
    public OPP_Tank(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = 2;
        name = "Opp Tank";
        speed = 2;
        maxLife = 20;
        life = maxLife;
        //SAME SOLID AREA AS MAIN TANK
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;  //cut down so there is space to hit the block. Easier for player to move in between blocks. allows for more space to move

        getImage();
        projectile = new FireBall(gp);  //setting the opponent with a fireball to shoot

    }
    public void getImage(){
        up1 = setup("/opponent/Hull_03up",gp.tileSize,gp.tileSize);
        down1 = setup("/opponent/Hull_03down",gp.tileSize,gp.tileSize);
        left1 = setup("/opponent/Hull_03left",gp.tileSize,gp.tileSize);
        right1 = setup("/opponent/Hull_03right",gp.tileSize,gp.tileSize);
    }
    public void setAction(){
        //SETTING TANKS BEHAVIOR
        //USED SAME AI AS BAT
        actionLockCounter++;

        if (actionLockCounter == 120) {
            Random random = new Random();   //get random number to get the direction of the npc object
            int i = random.nextInt(100) + 1;
            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
        //AI FOR OPPONENT TO SHOT
        int i = new Random().nextInt(100)+1;
        if(i > 99 && projectile.alive == false && shotAvailableCounter == 30){
            projectile.set(worldX,worldY,direction,true,this);
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;
        }
    }
    public void damageReaction(){
        actionLockCounter = 0;  //when recieve damage, face the players direction, if player facing right, opponent faces right
        direction = gp.player.direction;
    }
}
