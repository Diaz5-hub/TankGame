package main;

import entity.NPC_Object;
import object.OBJ_Boots;
import object.OBJ_Door;
import object.OBJ_Key;
import object.OBJ_Tent;
import opponentmain.OPP_Tank;

public class AssetSetter {
    //THIS CLASS IS USED TO SET OBJECTS AND PLACE THEM ON THE MAP
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }
    public void setObject(){
        //BOOTS USED AS POWER UP SPEED
//        gp.obj[0] = new OBJ_Boots(gp);
//        gp.obj[0].worldX = gp.tileSize*21;
//        gp.obj[0].worldY = gp.tileSize*22;
//
//        gp.obj[1] = new OBJ_Boots(gp);
//        gp.obj[1].worldX = gp.tileSize*21;
//        gp.obj[1].worldY = gp.tileSize*22;

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
