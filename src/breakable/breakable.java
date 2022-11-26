package breakable;

import entity.Entity;
import main.GamePanel;

public class breakable extends Entity {
    GamePanel gp;
    public boolean destructible = false;
    public breakable(GamePanel gp) {
        super(gp);
        this.gp = gp;
    }
    public void update(){

    }
}
