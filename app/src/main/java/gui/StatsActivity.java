package gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.josemartins.pokerclient.R;

import java.io.IOException;

import logic.Utils;

public class StatsActivity extends AppCompatActivity {
    TextView stats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        stats=(TextView) findViewById(R.id.statsview);

        update();
        Log.d("ooi","oncreate");

    }

    public void update(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                StringBuilder info= new StringBuilder();

                info.append("Name: ");
                info.append(Utils.stats.name);
                info.append('\n');
                info.append("Money: ");
                info.append(Utils.stats.playerMoney);
                info.append('\n');
                info.append("Number of Wins: ");
                info.append(Utils.stats.numberOfWins);
                info.append('\n');
                info.append("Number of Loses: ");
                info.append(Utils.stats.numberOfLoses);
                info.append('\n');
                info.append("Number of all ins: ");
                info.append(Utils.stats.numberOfAllin);
                info.append('\n');
                info.append("Max Pot win: ");
                info.append(Utils.stats.maxPotWin);
                info.append('\n');
                info.append("Min Pot win: ");
                info.append(Utils.stats.minPotWin);
                info.append('\n');

                stats.setText(info);
            }
        });
    }

    public void reset (View v){

        Utils.stats.resetStats();
        update();

        try {
            Utils.saveStats();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
