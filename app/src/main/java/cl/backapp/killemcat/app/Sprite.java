package cl.backapp.killemcat.app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaPlayer;

import java.util.List;
import java.util.Random;

/**
 * Created by xfce on 12-07-14.
 */
public class Sprite {
    // direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right
    int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 3;
    private int x;
    private int y;
    private int xSpeed; //velocidad de los personajes
    private int ySpeed; //velocidad de los personajes
    private GameView gameView;
    private Bitmap bmp;
    private int currentFrame = 0; //indica que posicion del sprite se muestra
    private int width;
    private int height;



    public Sprite(GameView gameView, Bitmap bmp) {
        this.gameView = gameView;
        this.bmp = bmp;
        //tamaño del bitmap / cantidad columas
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        Random rnd = new Random();
        x = rnd.nextInt(gameView.getWidth() - width);
        y = 0;
        //y = rnd.nextInt(gameView.getHeight() - height);
        xSpeed = rnd.nextInt(10)+10;
        ySpeed = rnd.nextInt(10)+10;

    }

    public boolean isCollision(float x2, float y2) {
        return x2 > x && x2 < x + width && y2 > y && y2 < y + height;
    }

    private void update() {

        if (x > gameView.getWidth() - width - xSpeed || x + xSpeed < 0) {
            xSpeed = -xSpeed;
        }
        x = x + xSpeed;
        if (y > gameView.getHeight() - height - ySpeed || y + ySpeed < 0) {
            ySpeed = -ySpeed;
        }
        y = y + ySpeed;
        if (y > gameView.getHeight() - height - ySpeed){
            gameView.removeRocky();
            if ((x <= (gameView.getWidth()/2)+width && x >= (gameView.getWidth()/2)-width*2 )){
                gameView.removeRocky();
                gameView.removeFood();
                gameView.removeRocky2();
                gameView.dog();
            }
        }

        currentFrame = ++currentFrame % BMP_COLUMNS;
    }

    public void onDraw(Canvas canvas) {
        update();
        int srcX = currentFrame * width;
        int srcY = getAnimationRow() * height;
        //rectangulos
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        //destino
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);

    }


    // direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right
    private int getAnimationRow() {
        double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);
        int direction = (int) Math.round(dirDouble) % BMP_ROWS;
        return DIRECTION_TO_ANIMATION_MAP[direction];
    }

}