package cl.backapp.killemcat.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by xfce on 12-07-14.
 */
public class GameView extends SurfaceView implements MediaPlayer.OnCompletionListener{
    private final Bitmap bmpBlood;
    private List<Sprite> sprites = new ArrayList<Sprite>();
    private List<MediaPlayer> sounds = new ArrayList<MediaPlayer>();
    private List<MediaPlayer> soundsDog = new ArrayList<MediaPlayer>();
    private List<Dish> dish = new ArrayList<Dish>();
    private List<Rocky> rocky = new ArrayList<Rocky>();
    private List<TempSprite> temps = new ArrayList<TempSprite>();
    private Bitmap bmp;
    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private long lastClick;
    private MediaPlayer mp;
    private int soundCat;
    private int soundDog;
    boolean stop;
    private MainActivity context;
    private Context mContext;
    private int contador = 0;


    public GameView(Context context){
        super(context);
        this.mContext = context;
        gameLoopThread = new GameLoopThread(this);
        sound();
        soundDog();
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                gameLoopThread.setRunning(false);
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //arranca el loop
                createDish();
                createSprites();
                createRocky();
                //gameLoopThread.setRunning(true);
                //gameLoopThread.start();

                MainActivity obj=new MainActivity();
                stop=obj.getBoolean(); //receive status of boolean from main activity

                //stop is boolean set if backPressed in main activity
                if(!stop){
                    gameLoopThread.setRunning(true);
                    gameLoopThread.start();
                }
                else
                    gameLoopThread.setRunning(false);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }
        });
        bmpBlood = BitmapFactory.decodeResource(getResources(), R.drawable.blood1);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (System.currentTimeMillis() - lastClick > 300) {
            lastClick = System.currentTimeMillis();
            synchronized (getHolder()) {
                float x = event.getX();
                float y = event.getY();
                for (int i = sprites.size() - 1; i >= 0; i--) {
                    Sprite sprite = sprites.get(i);
                    if (sprite.isCollision(x, y)) {
                        mew();
                        sprites.remove(sprite);
                        temps.add(new TempSprite(temps, this, x, y, bmpBlood));
                        randomSprites();
                        contador = contador + 1;
                        break;
                    }
                }
            }
        }
        return true;
    }

    private void mew(){
        Random rnd = new Random();
        soundCat = rnd.nextInt(sounds.size());
        mp = sounds.get(soundCat);
        mp.start();
    }

    private void sound(){
        sounds.add(MediaPlayer.create(getContext(),R.raw.cat_small));
        sounds.add(MediaPlayer.create(getContext(),R.raw.cat_small2));
        sounds.add(MediaPlayer.create(getContext(),R.raw.cat_small3));
        sounds.add(MediaPlayer.create(getContext(),R.raw.cat_angry));
    }

    public void dog(){
        Random rnd = new Random();
        soundDog = rnd.nextInt(soundsDog.size());
        mp = soundsDog.get(soundDog);
        mp.start();
    }

    private void soundDog(){
        soundsDog.add(MediaPlayer.create(getContext(),R.raw.dog_cry));
    }

    private void createSprites() {
        sprites.add(createSprite(R.drawable.black_cat));
        /*
        sprites.add(createSprite(R.drawable.bw_cat));
        sprites.add(createSprite(R.drawable.coffe_cat));
        sprites.add(createSprite(R.drawable.whelmet_cat));
        sprites.add(createSprite(R.drawable.white_cat));
        sprites.add(createSprite(R.drawable.yellow_cat));
        sprites.add(createSprite(R.drawable.yhelmet_cat));
        */
    }

    private void randomSprites(){
        Sprite[] cats = {createSprite(R.drawable.yhelmet_cat),
                createSprite(R.drawable.bw_cat),
                createSprite(R.drawable.coffe_cat),
                createSprite(R.drawable.whelmet_cat),
                createSprite(R.drawable.white_cat),
                createSprite(R.drawable.yellow_cat),
                createSprite(R.drawable.yhelmet_cat)
        };
        Random rn = new Random();
        int cantCat = rn.nextInt(6);
        for (int i = 0; i<=cantCat;i++){
            sprites.add(cats[i]);
        }
    }

    private void createDish(){
        dish.add(createDish(R.drawable.plato));
        dish.add(createDish(R.drawable.plato1));
        dish.add(createDish(R.drawable.plato2));
        dish.add(createDish(R.drawable.plato3));
        dish.add(createDish(R.drawable.plato4));
        dish.add(createDish(R.drawable.plato5));
        dish.add(createDish(R.drawable.plato6));
    }

    private void createRocky(){
        rocky.add(createRocky(R.drawable.rocky_1));
    }

    private Sprite createSprite(int resouce) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
        return new Sprite(this,bmp);
    }

    private Dish createDish(int resouce) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
        return new Dish(this,bmp);
    }

    private Rocky createRocky(int resouce) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
        return new Rocky(this,bmp);
    }



    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        for (int i = temps.size() - 1; i >= 0; i--) {
            temps.get(i).onDraw(canvas);
        }
        for (Sprite sprite : sprites) {
            sprite.onDraw(canvas);
        }
        for (Dish dish1 : dish) {
            dish1.onDraw(canvas);
        }
        for (Rocky rocky1: rocky) {
            rocky1.onDraw(canvas);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        //mp.stop();
    }

    public void removeFood(){
        if (dish.size()>1){
            dish.remove(0);
        }else {
            gameLoopThread.setRunning(false);
            Intent intent = new Intent(mContext, FinishActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("contador",String.valueOf(contador));
            mContext.startActivity(intent);
        }
    }

    public void removeRocky(){
        rocky.remove(0);
        rocky.add(createRocky(R.drawable.rocky_2));
    }
    public void removeRocky2(){
        rocky.remove(0);
        rocky.add(createRocky(R.drawable.rocky_1));

    }

}