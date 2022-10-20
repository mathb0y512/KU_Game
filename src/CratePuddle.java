

public class CratePuddle extends Object {

    public CratePuddle(int X, int Y, Game Game) {
        super(X, Y, "CratePuddle", Game.GetSpriteSheet().grabImage(0, 2), Game);
    }

    public boolean move(int dir) {
    return false;
    }
}