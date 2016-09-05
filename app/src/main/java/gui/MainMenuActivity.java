package gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.josemartins.pokerclient.R;

import java.io.IOException;

import logic.Player;
import logic.Utils;


public class MainMenuActivity extends AppCompatActivity {

    Button play;
    Button stats;
    Button about;
    Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        if(Utils.player == null)
            Utils.player = new Player();

        play = (Button) findViewById(R.id.playbtn);
        stats = (Button) findViewById(R.id.statsbtn);
        about = (Button) findViewById(R.id.aboutbtn);
        exit = (Button) findViewById(R.id.exitbtn);

        Utils.filePath= this.getFilesDir().getParentFile().getPath() + "/PokerStats.txt";

        try {
            Utils.readStats();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void play (View v){
        Intent i= new Intent(this, ConnectingActivity.class);
        startActivity(i);
    }

    public void stats (View v){
        Intent i= new Intent(this, StatsActivity.class);
        startActivity(i);
    }

    public void about (View v){
        Intent i= new Intent(this, AboutActivity.class);
        startActivity(i);
    }

    public void exit (View v){
        exitGame();
    }

    @Override
    public void onBackPressed() {
        exitGame();
    }

    public void exitGame(){
        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
