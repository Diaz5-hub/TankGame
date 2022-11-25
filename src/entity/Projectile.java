package entity;

import main.GamePanel;

public class Projectile extends Entity{
    Entity user;
    public Projectile(GamePanel gp) {
        super(gp);
    }
    public void set(int worldX,int worldY,String direction,boolean alive, Entity user){
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive =alive;
        this.user = user;
        this.life = maxLife;
    }
    public void update(){
            //checking to see if player shot the fireball
        if(user == gp.player){
            int opponentIndex = gp.cChecker.checkEntity(this,gp.opponent);
            if(opponentIndex != 999){
                gp.player.damageOpponent(opponentIndex,22);
                alive = false;  //if projectile hits the opponent, it dies(disappears)
            }
        }
        if(user != gp.player){
            //if the user is the opponent
            boolean contactPlayer = gp.cChecker.checkPlayer(this);
            if(gp.player.invincible == false && contactPlayer == true){
                damagePlayer(16);   //get 16 damage to player
                alive = false;
            }
        }
        switch (direction){
            case "up": worldY -= speed;break;
            case "down": worldY += speed;break;
            case "left": worldX -= speed;break;
            case "right": worldX += speed;break;
        }
        life--; //decrement life of fireball so it does not shoot forever
        if(life < 0){
            alive = false;
        }
    }
}
