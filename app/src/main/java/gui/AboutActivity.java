package gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.josemartins.pokerclient.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        TextView text = (TextView) findViewById(R.id.aboutText);
        StringBuilder s = new StringBuilder();
        s.append("App developed as a project for the \"Object Oriented Programming Laboratory\" course.");
        s.append( "\nThe App allows the player to connect to a desktop application and play Poker with other people.");
        s.append("\nIt´s possible to connect up to 6 phones to the desktop application at the same time.");
        s.append( "\n\n Developers: \n Jose Martins - up201404189 \n Marcelo Ferreira - up201405323 ");


        text.setText(s);


    }
}
