import java.awt.image.BufferedImage;

public class Object {
    protected int x;
    protected int y;
    protected int dir;
    protected String sprite;
    protected BufferedImage img;
    protected Game game;

    public Object(int X, int Y, String Sprite, BufferedImage Img, Game Game) {
        x = X;
        y = Y;
        sprite = Sprite;
        img = Img;
        game = Game;
    }

    public Object(int X, int Y, String Sprite, int Col, int Row, Game Game) {
        x = X;
        y = Y;
        sprite = Sprite;
        img = Game.GetSpriteSheet().grabImage(Col, Row);
        game = Game;
    }

    public boolean move(int dir) {
        return false;
    }

    public int[] getPosition(int dir) {
        int[] off = getOffset(dir);
        return new int[] {x+off[0], y+off[1]};
    }

    public int[] getOffset(int dir) {
        switch(dir) {
            case 1:
                return new int[] {0, -1};
            case 2:
                return new int[] {1, 0};
            case 3:
                return new int[] {0, 1};
            case 4:
                return new int[] {-1, 0};
        }
        return new int[] {0, 0};
    }

    public void shift(int dir) {
        int[] off = getOffset(dir);
        shift(off[0], off[1]);
    }

    public void shift(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public int GetX() {
        return x;
    }
    public int GetY() {
        return y;
    }
    public void SetX(int x) {
        this.x = x;
    }
    public void SetY(int y) {
        this.y = y;
    }
    public String GetSprite() {
        return sprite;
    }

    public BufferedImage GetImage() {
        return img;
    }
}