

public class RockPuddle extends Object {

    public RockPuddle(int X, int Y, Game Game) {
        super(X, Y, "RockPuddle", Game.GetSpriteSheet().grabImage(1, 2), Game);
    }

    public boolean move(int dir) {
    return false;
    }
}