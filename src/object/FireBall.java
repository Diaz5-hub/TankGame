package object;

import entity.Projectile;
import main.GamePanel;

public class FireBall extends Projectile {
    GamePanel gp;
    public FireBall(GamePanel gp) {
        //structure entity->projectile->fireball. to be able to get from projectile and entity
        super(gp);
        this.gp = gp;
        name = "Fireball";
        speed = 10;      //how fast the fireball flies
        maxLife = 80;
        life = maxLife;
        //attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }
    public void getImage(){
        up1 = setup("/projectile/fireball_up_1",gp.tileSize, gp.tileSize);
        down1 = setup("/projectile/fireball_down_1",gp.tileSize, gp.tileSize);
        left1 = setup("/projectile/fireball_left_1",gp.tileSize, gp.tileSize);
        right1 = setup("/projectile/fireball_right_1",gp.tileSize, gp.tileSize);


    }
}
