package breakable;

import main.GamePanel;

public class breakableWall extends breakable{
    GamePanel gp;
    public breakableWall(GamePanel gp) {
        super(gp);
        this.gp = gp;
        down1 = setup("/tiles/breakablewall",gp.tileSize,gp.tileSize);
        destructible = true;
    }
}
