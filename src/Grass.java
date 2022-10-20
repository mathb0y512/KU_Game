
public class Grass extends Object {

    public Grass(int X, int Y, Game Game) {
        super(X, Y, "Grass", Game.GetSpriteSheet().grabImage(0, 0), Game);
    }

    public boolean move(int dir) {
    return false;
    }
}