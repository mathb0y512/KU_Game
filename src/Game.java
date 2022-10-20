
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.awt.image.DataBufferByte;
import javax.imageio.ImageIO;
import java.io.File;

public class Game extends Canvas implements Runnable{

    private static final long serialVersionUID = 1550691097823471818L;
    //Window size
    public static final int WIDTH = 33 * 16, HEIGHT = 33 * 9;
    private Thread thread;
    private boolean running = false;

    public SpriteSheet ss = new SpriteSheet();
    public Object Player;
    public Object[][] background;
    public ArrayList<Object> foreground;

    BufferedImage Level;

    public String levelName;
    public String[] levelList;

    int levelCursor;

    public SpriteSheet GetSpriteSheet() {
        return ss;
    }

    public Object[][] GetBackground() {
        return background;
    }

    public ArrayList<Object> GetForeground() {
        return foreground;
    }
    // returns the object's position on the grid
    public Object GetForegroundPosition(int[] position) {
        for(Object o : foreground) {
            if(Arrays.equals(o.getPosition(0), position)) {
                return o;
            }
        }
        return null;
    }
    //Runs the game
    public static void main(String args[]){
        new Game();
    }

    public Game() {
        // adds keyListener for checking user keyboard input
        this.addKeyListener(new KeyInput(this));
        // opens the start menu for level select
        startMenu();
        // Window title
        new Window(WIDTH, HEIGHT, "Blocker", this);
    }

    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
        this.setFocusable(true);
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
            this.requestFocus();

            render();
    
            updateTime = System.nanoTime() - now;
            wait = (OPTIMAL_TIME - updateTime) / 1000000;

            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
    }

    public void move(int dir) {
        //System.out.println(dir);
        Player.move(dir);
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

        if(levelName == "menu") {
            g.setColor(Color.white);
            g.setFont(g.getFont().deriveFont(20f));
            g.drawString("Blocker!", 5, 20);
            g.setFont(g.getFont().deriveFont(15f));
            for(int i = 0; i < levelList.length; i++) {
                String beginning = (levelCursor == i) ? "> " : "";
                g.drawString(beginning + levelList[i].replace(".png", ""), 5, 40 + 15 * i);
            }
        } else {
            for(Object[] ooo : background){
                for (Object o : ooo) {
                    g.drawImage(o.GetImage(), o.GetX()*32, o.GetY()*32, this);
                }
            }
            for (Object o : foreground){
                g.drawImage(o.GetImage(), o.GetX()*32, o.GetY()*32, this);
            }
        }

        g.dispose();
        bs.show();
    }

    public void startMenu() {
        levelCursor = 0;
        levelName = "menu";
        File directoryPath = new File("src/res/Level_Pack/");
        //List of all files and directories
        levelList = directoryPath.list();
        String[] sortList = new String[levelList.length];
        for(int i = 0; i < levelList.length; i++)
        {
            int least = i;
            for(int j = i + 1; j < levelList.length; j++)
            {
                if(levelList[j].compareTo(levelList[i]) < 0)
                    least = j;
            }
            //put the new minimum in the i-th position.
            String aux = levelList[i];
            levelList[i] = levelList[least];
            levelList[least] = aux;
        }

    }

    public void startLevel() {
        //load level
        try {
            Level = ImageIO.read(new FileInputStream("src/res/Level_Pack/" + levelList[levelCursor]));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //convert image to pixels
        final byte[] pixels = ((DataBufferByte) Level.getRaster().getDataBuffer()).getData();
        final int width = Level.getWidth();
        final int height = Level.getHeight();
        int[][][] result = new int[height][width][3];
        final boolean hasAlphaChannel = Level.getAlphaRaster() != null;
        int start = (hasAlphaChannel) ? 1 : 0;
        int pixelLength = (hasAlphaChannel) ? 4 : 3;
            
        for (int pixel = start, row = 0, col = 0; pixel + 2 < pixels.length; pixel += pixelLength) {
            result[row][col][2] += ((int) pixels[pixel] & 0xff); // blue
            result[row][col][1] += ((int) pixels[pixel + 1] & 0xff); // green
            result[row][col][0] += ((int) pixels[pixel + 2] & 0xff); // red
            col++;
            if (col == width) {
               col = 0;
               row++;
            }
        }

        //System.out.println(result[3][5][0] + "," + result[3][5][1] + "," + result[3][5][2]);

        //initializing level layout
        this.foreground = new ArrayList<Object>();
        this.background = new Object[16][9];

        //convert pixels to objects
        for(int i = 0; i < 16; i++) {
            for(int j = 0; j < 9; j++){
                if(Arrays.equals(result[j][i], new int[] {34, 177, 76})) { //colors -> objects
                    background[i][j] = new Grass(i, j, this);
                } else if(Arrays.equals(result[j][i], new int[] {0, 162, 232})) {
                    background[i][j] = new Puddle(i, j, this);
                } else if(Arrays.equals(result[j][i], new int[] {63, 72, 204})){
                    background[i][j] = new Portal(i, j, this);
                } else {
                    background[i][j] = new RockPuddle(i, j, this);
                }
            }
        }
        for(int i = 0; i < 16; i++) {
            for(int j = 0; j < 9; j++){
                //System.out.println(result[j+9][i][0] + ", " + result[j+9][i][1] + ", " + result[j+9][i][2]);
                Object object = null;
                if(Arrays.equals(result[j+9][i], new int[] {255, 255, 255})) { //colors -> objects
                    continue;
                }
                if(Arrays.equals(result[j+9][i], new int[] {185, 122, 87})) {
                    object = new Crate(i, j, this);
                } else if(Arrays.equals(result[j+9][i], new int[] {127, 127, 127})){
                    Player = new Player(i, j, this);
                    foreground.add(Player);
                } else if(Arrays.equals(result[j+9][i], new int[] {181, 230, 29})){
                    object = new Bush(i, j, this);
                } else if(Arrays.equals(result[j+9][i], new int[] {255, 0, 0})){
                    object = new Flower(i, j, this);
                } else if(Arrays.equals(result[j+9][i], new int[] {0, 0, 0})){
                    object = new Rock(i, j, this);
                }
                if(object != null) {
                    foreground.add(object);
                }
            }
        }
        levelName = levelList[levelCursor];
    }
}
