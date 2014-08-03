package cl.backapp.killemcat.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;


public class InitActivity extends ActionBarActivity {

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_init);

        Button jugar = (Button) findViewById(R.id.jugar);
        jugar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                new executeGame().execute();
                return false;
            }
        });

        Button salir = (Button) findViewById(R.id.salir);
        salir.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                finish();
                return false;
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        if(pDialog != null)
            pDialog.dismiss();
    }



    class executeGame extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            pDialog = new ProgressDialog(InitActivity.this);
            pDialog.setMessage("cargando...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);

            pDialog.show();
            if(pDialog != null)
                pDialog.dismiss();
        }


        protected String doInBackground(String... args) {
            return null;
        }

        protected void onPostExecute(String file_url) {

            pDialog.dismiss();
            Thread thread = new Thread(new Runnable(){
                @Override
                public void run() {

                        Intent intent = new Intent(InitActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                }
            });

            thread.start();
            while(thread.isAlive()){

            }


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.init, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
