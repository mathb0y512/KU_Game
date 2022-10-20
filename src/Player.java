
public class Player extends Object {

    public Player(int X, int Y, Game Game) {
        super(X, Y, "Player", Game.GetSpriteSheet().grabImage(1, 0), Game);
    }

    public boolean move(int dir) {
        int[] Towards = getPosition(dir);
        if(game.GetBackground()[Towards[0]][Towards[1]].sprite == "Portal") {
            game.startMenu();
            return true;
        }
        if(game.GetBackground()[Towards[0]][Towards[1]].sprite == "Puddle") {
            return false;
        }
        Object object = game.GetForegroundPosition(Towards);
        if(object == null) {
            shift(dir);
            return true;
        }
        if(object.move(dir)) {
            shift(dir);
            return true;
        }
        return false;
    }
}
