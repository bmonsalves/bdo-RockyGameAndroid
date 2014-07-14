package cl.backapp.killemcat.app;

import android.graphics.Bitmap;
import android.graphics.Canvas;


/**
 * Created by xfce on 13-07-14.
 */
public class Dish {
    private GameView gameView;
    private Bitmap bmp;
    private int width;
    private int height;

    public Dish(GameView gameView, Bitmap bmp) {
        this.gameView = gameView;
        this.bmp = bmp;
        //tamaño del bitmap / cantidad columas
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();

    }

    public void onDraw(Canvas canvas) {
        int newWidht = (gameView.getWidth()/2) - (width/2);
        canvas.drawBitmap(bmp, newWidht, gameView.getHeight()-height , null);
    }

}
