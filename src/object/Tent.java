package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Tent extends Entity {

    public Tent(GamePanel gp){
        super(gp);
        name = "Tent";
        down1 = setup("/objects/tent",gp.tileSize,gp.tileSize);
    }
}

