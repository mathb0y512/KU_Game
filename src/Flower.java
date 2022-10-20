import java.util.ArrayList;

public class Flower extends Object {

    public Flower(int X, int Y, Game Game) {
        super(X, Y, "Flower", Game.GetSpriteSheet().grabImage(2, 2), Game);
    }
    
    public boolean move(int dir) {
        ArrayList<Object> foreground = game.GetForeground();
        foreground.remove(game.person);
        game.person = new FirePlayer(GetX(), GetY(), game);
        foreground.add(game.person);
        game.GetForeground().remove(this);
        return true;
    }
}