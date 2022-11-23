package main;

//import java.awt.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class UtilityTool {
    //THIS CLASS IS USED TO HANDLE SCALING THE BUFFERED IMAGES
    public BufferedImage scaleImage(BufferedImage original,int width,int height){
        //scale buffered images when importing, no need to scaled images when calling draw function in TileManager class when drawing images
        BufferedImage scaledImage = new BufferedImage(width,height,original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original,0,0,width,height,null);
        g2.dispose();
        return scaledImage;
    }
}
