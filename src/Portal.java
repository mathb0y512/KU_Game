public class Portal extends Object {
    public Portal(int X, int Y, Game Game) {
        super(X, Y, "Portal", Game.GetSpriteSheet().grabImage(1, 3), Game);
    }

    public boolean move(int dir) {
    return false;
    }
}