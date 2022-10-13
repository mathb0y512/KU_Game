
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable{

    private static final long serialVersionUID = 1550691097823471818L;

    public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;
    private Thread thread;
    private boolean running = false;

    public SpriteSheet ss = new SpriteSheet();
    public Object person = new Object(3, 3, 1, ss.grabImage(0, 0));

    public static void main(String args[]){
        new Game();
    }

    public Game(){
        new Window(WIDTH, HEIGHT, "Yay!", this);
    }

    public synchronized void start(){


        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void run(){
        long now;
        long updateTime;
        long wait;
    
        final int TARGET_FPS = 30;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
    
        while (running) {
            now = System.nanoTime();
    
            tick();
            render();
    
            updateTime = System.nanoTime() - now;
            wait = (OPTIMAL_TIME - updateTime) / 1000000;

            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0,0,WIDTH,HEIGHT);

        g.drawImage(person.GetImage(), 0, 0, this);

        g.dispose();
        bs.show();
    }

    private void tick() {

    }
}
