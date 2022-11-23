package entity;

import main.GamePanel;

import java.util.Random;

public class NPC_Object extends Entity{
    public NPC_Object(GamePanel gp){
        super(gp);

        direction = "Down";
        speed = 1;
        getImage();
    }
    public void getImage(){

        up1 = setup("/npc/bat_up_1",gp.tileSize,gp.tileSize);
        down1 =setup("/npc/bat_down_1",gp.tileSize,gp.tileSize);
        left1 = setup("/npc/bat_left_1",gp.tileSize,gp.tileSize);
        right1 = setup("/npc/bat_right_1",gp.tileSize,gp.tileSize);
    }
    public void setAction() {
        //SET CHARACTERS BEHAVIOR, SORT OF AI
        //in subclass to override it
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
    }
}
