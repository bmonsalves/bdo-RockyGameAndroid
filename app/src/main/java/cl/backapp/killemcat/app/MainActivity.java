package cl.backapp.killemcat.app;


import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Window;


public class MainActivity extends ActionBarActivity {
    MediaPlayer backMusic;
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
}