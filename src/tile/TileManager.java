package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;


public class TileManager {
        // THIS CLASS IS USED TO MANAGE TILES
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gp){
        this.gp = gp;

        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/maps/world01.txt");   //pass map file here
    }
    public void getTileImage(){
        //calling function to get tile images
            setup(0,"tree",true);
            setup(1,"floor01",false);
            setup(2,"road00",false);
            setup(3,"wall",true);
            setup(4,"earth",false);
            setup(5,"water00",true);
    }
    public void setup(int index,String imageName,boolean collision){
        //FUNCTION USED TO GET TILE IMAGES
        UtilityTool uTool = new UtilityTool();
        try {
            //USED TO HANDLE ALL THE HALF DUPLICATED LINES HERE(INSTANTIATION,IMPORT IMAGE,SCALE AND SET COLLISION)
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image,gp.tileSize,gp.tileSize);
            tile[index].collision = collision;
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void loadMap(String filePath){
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;
            while(col < gp.maxWorldCol && row < gp.maxWorldRow){
                String line = br.readLine();    //read line of text from the map txt file

                while(col < gp.maxWorldCol){
                    String numbers[] = line.split(" "); //split to get numbers 1 by 1
                    int num = Integer.parseInt(numbers[col]);   //getting each number in the map after splitting
                    mapTileNum[col][row] = num;
                    col++;  //continue until every numbers[] is stored in mapTileNum[]
                }
                if(col == gp.maxWorldCol){
                    //if hit 16(the end)
                    col = 0;
                    row++;  //go to the next row to read into mapTileNum[]
                }
            }
            br.close();
        }catch (Exception e){

        }
    }
    public void draw(Graphics2D g2){
        int worldCol = 0;
        int worldRow = 0;


        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){
            int tileNum = mapTileNum[worldCol][worldRow]; //extract a tile number from mapTileNum
            //if get 0 from mapTileNum then draw that image
            //data stored inside of mapTileNum[]
            int worldX = worldCol * gp.tileSize;    //worldX and Y is the position on the map
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;    //where on the screen we draw it
            int screenY = worldY - gp.player.worldY + gp.player.screenY;
            //if worldX:500 worldY:500 then player is 500 pixels away from 0:0 tiles
            //screenX 0 and screenY is top left corner
            //add player.screenX and y to offset since the player is drawn at the center
            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
                g2.drawImage(tile[tileNum].image,screenX,screenY,null);
            }
            worldCol++;
            if(worldCol == gp.maxWorldCol){
                //if at the maxScreenCol reset the col and x
                worldCol = 0;
                worldRow++;
            }
        }


    }
}
