import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class SpriteSheet {
    private BufferedImage sprite;

    public SpriteSheet() {
        try {
            this.sprite = ImageIO.read(new FileInputStream("src/res/sprite_sheet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage grabImage(int col, int row) {
        BufferedImage img = sprite.getSubimage(col * 8, row * 8, 8, 8);
        BufferedImage resizedImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        float xScale = (float) 32 / img.getWidth();
        float yScale = (float) 32 / img.getHeight();
        AffineTransform at = AffineTransform.getScaleInstance(xScale, yScale);
        g.drawRenderedImage(img, at);
        g.dispose();
        return resizedImage;
    }
}