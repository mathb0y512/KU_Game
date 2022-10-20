

public class Crate extends Object {

    public Crate(int X, int Y, Game Game) {
        super(X, Y, "Crate", Game.GetSpriteSheet().grabImage(0, 1), Game);
    }
    
    public boolean move(int dir) {
        int[] Towards = getPosition(dir);
        Object BackO = game.GetBackground()[Towards[0]][Towards[1]];
        if(BackO.sprite == "Puddle") {
            game.GetBackground()[Towards[0]][Towards[1]] = new CratePuddle(BackO.GetX(), BackO.GetY(), game);
            game.GetForeground().remove(this);
            return true;
        }
        Object FrontO = game.GetForegroundPosition(Towards);
        if(FrontO == null) {
            shift(dir);
            return true;
        }
        return false;
    }
}