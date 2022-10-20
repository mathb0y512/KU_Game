

public class Rock extends Object {

    public Rock(int X, int Y, Game Game) {
        super(X, Y, "Rock", Game.GetSpriteSheet().grabImage(1, 1), Game);
    }
    
    public boolean move(int dir) {
        int[] Towards = getPosition(dir);
        Object BackO = game.GetBackground()[Towards[0]][Towards[1]];
        if(BackO.sprite == "Puddle") {
            game.GetBackground()[Towards[0]][Towards[1]] = new RockPuddle(BackO.GetX(), BackO.GetY(), game);
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