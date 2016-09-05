package gui;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.josemartins.pokerclient.R;


import java.io.IOException;

import common.ConnectMessage;
import common.PlayerInterface;
import logic.Utils;
import network.ClientHandler;

public class ConnectingActivity extends AppCompatActivity {

    String ip;
    CheckBox newAccount;
    CheckBox myAccount;
    Button connect;
    Conn connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connecting);


        newAccount = (CheckBox) findViewById(R.id.newAccountBox);
        myAccount = (CheckBox) findViewById(R.id.myAccountBox);
        connect = (Button) findViewById(R.id.connectButton);
        connection=null;

        TextView myAccountInfo = (TextView) findViewById(R.id.accountInfo);

        StringBuilder str= new StringBuilder();


        if(Utils.stats.isAccount == false || Utils.stats.playerMoney <= 50) {
            myAccount.setEnabled(false);
            newAccount.setEnabled(false);
            newAccount.setChecked(true);
            connect.setEnabled(true);

            if(Utils.stats.isAccount == false )
                str.append("You don't have an account");
            else{
                str.append("Your account doesn't have enough money to play");
                str.append('\n');
                str.append("Required at least 50 $ (you have ");
                str.append(Utils.stats.playerMoney);
                str.append(" $)");
                str.append('\n');
                str.append("Please create a new account");
            }

        }else{
            Utils.player.setName(Utils.stats.name);
            Utils.player.setMoney(Utils.stats.playerMoney);

            str.append("Account Info:");
            str.append('\n');
            str.append("Name: ");
            str.append(Utils.player.getName());
            str.append('\n');
            str.append("Money: ");
            str.append(Utils.player.getMoney());
        }

        myAccountInfo.setText(str);
    }

    public void newAccount (View v){


        if(newAccount.isChecked()) {
            connect.setEnabled(true);
            myAccount.setChecked(false);
        }
        else
            connect.setEnabled(false);

    }

    public void myAccount (View v){

        if(myAccount.isChecked()) {
            connect.setEnabled(true);
            newAccount.setChecked(false);
        }
        else
            connect.setEnabled(false);
    }

    public void connect (View v) throws IOException {
        Utils.stats.isAccount = true;

        if(newAccount.isChecked()) {
            EditText nameText = (EditText)findViewById(R.id.clientName);
            String name=nameText.getText().toString().trim();

            Utils.player.setName(name);
            Utils.stats.name = name;

            Utils.player.setMoney(2000);
            Utils.stats.playerMoney = 2000;

            Utils.stats.resetStats();
        }


        EditText ipText = (EditText)findViewById(R.id.iP);
        ip=ipText.getText().toString();

        setContentView(R.layout.connecting_screen);
        connection = new Conn();
        connection.execute(this);

        Utils.saveStats();
    }


    class Conn extends AsyncTask<ConnectingActivity, Void, ConnectingActivity> {

        private boolean errorConnecting=false;
        private ConnectMessage connectReturn;

        @Override
        protected ConnectingActivity doInBackground(ConnectingActivity... params) {
            try {
                Utils.game = ClientHandler.initClient((PlayerInterface)Utils.player,ip,4455);
                connectReturn = Utils.game.join((PlayerInterface) Utils.player);

                if(isCancelled())
                    Utils.game.leave(Utils.player.getName());


            } catch (Exception e) {
                errorConnecting=true;
            }

            return params[0];
        }

        @Override
        protected void onPostExecute(ConnectingActivity c) {
            Intent i;


            if(errorConnecting || connectReturn != ConnectMessage.SUCESS) {
                i = new Intent(c, MainMenuActivity.class);
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (errorConnecting)
                            Toast.makeText(getApplicationContext(), "Error connecting to the server", Toast.LENGTH_LONG).show();
                        else {
                            switch (connectReturn) {
                                case NAME_CHOSEN:
                                    Toast.makeText(getApplicationContext(), "Name already chosen", Toast.LENGTH_LONG).show();
                                    break;
                                case ROUND_IN_PROGRESS:
                                    Toast.makeText(getApplicationContext(), "Round in progress", Toast.LENGTH_LONG).show();
                                    break;
                                case TABLE_FULL:
                                    Toast.makeText(getApplicationContext(), "Table is Full", Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }
                    }
                });
            }else{
                i= new Intent(c, PlayingActivity.class);
            }

            finish();
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {
        if(connection!=null) {
            connection.cancel(true);
        }

        Intent i= new Intent(this, MainMenuActivity.class);
        startActivity(i);
    }
}
