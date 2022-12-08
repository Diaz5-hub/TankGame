package main;

import entity.NPC_Object;
import object.Boots;
import object.Tent;
import opponentmain.OPP_Tank;

public class AssetSetter {
    //THIS CLASS IS USED TO SET OBJECTS AND PLACE THEM ON THE MAP
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }
    public void setObject(){
        //BOOTS USED AS POWER UP SPEED
        gp.obj[0] = new Boots(gp);
        gp.obj[0].worldX = gp.tileSize*23;
        gp.obj[0].worldY = gp.tileSize*25;

        gp.obj[1] = new Boots(gp);
        gp.obj[1].worldX = gp.tileSize*25;
        gp.obj[1].worldY = gp.tileSize*28;

        gp.obj[2] = new Tent(gp);
        gp.obj[2].worldX = gp.tileSize*29;
        gp.obj[2].worldY = gp.tileSize*34;

        gp.obj[3] = new Tent(gp);
        gp.obj[3].worldX = gp.tileSize*18;
        gp.obj[3].worldY = gp.tileSize*48;

        gp.obj[4] = new Boots(gp);
        gp.obj[4].worldX = gp.tileSize*35;
        gp.obj[4].worldY = gp.tileSize*28;

        gp.obj[5] = new Boots(gp);
        gp.obj[5].worldX = gp.tileSize*35;
        gp.obj[5].worldY = gp.tileSize*20;

    }
    public void setNPC(){
        gp.npc[0] = new NPC_Object(gp);
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY = gp.tileSize*21;
    }
    public void setOpponent(){
        gp.opponent[0] = new OPP_Tank(gp);
        gp.opponent[0].worldX = gp.tileSize*23;
        gp.opponent[0].worldY = gp.tileSize*36;
    }
}
