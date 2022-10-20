import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class KeyInput implements KeyListener{
    
    private Game game;

    public KeyInput(Game game){
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(game.levelname == "menu") {
            switch(e.getKeyChar()) {
                case 'w':
                    game.levelcursor -= 1;
                    return;
                case 's':
                    game.levelcursor += 1;
                    return;
                case ' ':
                    game.startlevel();
                    return;
            }
            game.levelcursor = Math.max(0, Math.min(game.levellist.length-1, game.levelcursor));
        } else {
            switch(e.getKeyChar()) {
                case 'w':
                    game.move(1);
                    break;
                case 'd':
                    game.move(2);
                    break;
                case 's':
                    game.move(3);
                    break;
                case 'a':
                    game.move(4);
                    break;
                case 'm':
                    game.startmenu();
                    break;
                case 'r':
                    game.startlevel();
                    break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        
    }
}