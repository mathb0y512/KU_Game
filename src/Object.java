import java.awt.image.BufferedImage;

public class Object {
    int x;
    int y;
    int dir;
    int sprite;
    BufferedImage img;

    public Object(int X, int Y, int Sprite, BufferedImage Img) {
        x = X;
        y = Y;
        sprite = Sprite;
        img = Img;
    }

    public int GetX() {
        return x;
    }
    public int GetY() {
        return y;
    }
    public int GetSprite() {
        return sprite;
    }

    public BufferedImage GetImage() {
        return img;
    }
}