

public class Bush extends Object {

    public Bush(int X, int Y, Game Game) {
        super(X, Y, "Bush", Game.GetSpriteSheet().grabImage(0, 3), Game);
    }

    public boolean move(int dir) {
    return false;
    }
}