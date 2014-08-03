package cl.backapp.killemcat.app;


import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;


public class MainActivity extends ActionBarActivity {
    MediaPlayer backMusic;
    boolean stop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        GameView gameView = new GameView(this);
        setContentView(gameView);

        backMusic = MediaPlayer.create(MainActivity.this,R.raw.back);
        backMusic.setLooping(true);
        backMusic.start();
    }

    @Override
    protected void onPause(){
        super.onPause();
        backMusic.release();
        finish();
    }




    public Boolean getBoolean(){
        return stop;
    }

    public void setBoolean(Boolean bools){
        stop=bools;
    }

    @Override
    public void onBackPressed() {
// TODO Auto-generated method stub
        super.onBackPressed();

        stop=true;
        setBoolean(stop);

        try{
            Thread.sleep(200); // Be safeside and wait for Game thread to finish
        }
        catch(Exception e){
            e.printStackTrace();
        }

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(intent);

    }
}