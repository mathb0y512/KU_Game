public class Puddle extends Object {
    public Puddle(int X, int Y, Game Game) {
        super(X, Y, "Puddle", Game.GetSpriteSheet().grabImage(2, 1), Game);
    }

    public boolean move(int dir) {
    return false;
    }
}