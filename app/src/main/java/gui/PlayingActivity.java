package gui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.josemartins.pokerclient.R;


import java.util.ArrayList;

import common.Action;
import common.Card;
import common.NotificationType;
import common.Rank;
import common.Suit;
import logic.Utils;
import network.NotificationHandler;

public class PlayingActivity extends AppCompatActivity implements NotificationHandler{

    //UI
    Button playButton;
    Button startButton;
    TextView infoView;
    TextView raiseValue;
    TextView timeView;
    TextView endRoundView;
    CheckBox raiseBox;
    CheckBox callCheckBox;
    CheckBox foldBox;
    CheckBox allInBox;
    SeekBar raiseSlider;
    RelativeLayout layout;
    Button leave;
    //cards
    Bitmap cardsBmp;
    DrawCard card1;
    DrawCard card2;
    DrawCard handCard1;
    DrawCard handCard2;
    DrawCard handCard3;
    DrawCard handCard4;
    DrawCard handCard5;

    boolean myTurn;
    boolean starting;
    boolean endRound;
    int currMaxBet=0;
    int counter=0;
    Action actionSelected=Action.NONE;


    private Handler handler = new Handler();

    private Runnable myTurnTask = new Runnable() {
        @Override
        public void run() {

            timeView.setText("Time: " + counter);

            if(counter > 1) {
                counter--;
                handler.postDelayed(this, 1000);
            }
            else if(counter == 1){//time OUT
                timeView.setVisibility(View.INVISIBLE);
                playButton.setVisibility(View.INVISIBLE);
                playButton.setEnabled(false);
                actionSelected=Action.FOLD;
                myTurn=false;
                new PlayerAction().execute(raiseSlider.getProgress()+currMaxBet);
                counter--;
            }

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        playButton=(Button) findViewById(R.id.playbtn);
        startButton=(Button) findViewById(R.id.startBtn);
        infoView = (TextView) findViewById(R.id.infoview);
        raiseValue = (TextView) findViewById(R.id.raiseValue);
        raiseBox = (CheckBox) findViewById(R.id.raiseBox);
        callCheckBox = (CheckBox) findViewById(R.id.callCheckBox);
        foldBox = (CheckBox) findViewById(R.id.foldBox);
        allInBox = (CheckBox) findViewById(R.id.allInBox);
        raiseSlider = (SeekBar) findViewById(R.id.raiseSlider);
        timeView = (TextView) findViewById(R.id.timeview);
        leave = (Button) findViewById(R.id.leavebtn);
        endRoundView = (TextView) findViewById(R.id.endRound);

        RelativeLayout.LayoutParams endLayout = new  RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        endLayout.addRule(RelativeLayout.CENTER_HORIZONTAL);
        endLayout.addRule(RelativeLayout.CENTER_VERTICAL);
        endRoundView.setLayoutParams(endLayout);
        endRoundView.setVisibility(View.INVISIBLE);

        raiseSlider.setMax(Utils.player.getMoney()-currMaxBet);
        raiseSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                raiseValue.setText((progress+currMaxBet) + "/" + Utils.player.getMoney());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Utils.player.setNhandler(this);


        layout= (RelativeLayout)findViewById(R.id.relLayout);

        cardsBmp = BitmapFactory.decodeResource(getResources(),R.drawable.cards_sprite);

        //hand cards
        card1 = new DrawCard(this,cardsBmp, Utils.player.getHand().get(0));
        card2 = new DrawCard(this,cardsBmp, Utils.player.getHand().get(1));

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        int height = displaymetrics.heightPixels;

        RelativeLayout.LayoutParams card1Details = new  RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );


        card1Details.setMargins((int)(0.25*width),0,(int)(0.48*width),(int)(0.35*height));
        layout.addView((View) card1,card1Details);



      RelativeLayout.LayoutParams card2Details = new  RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        card2Details.setMargins((int)(0.53*width),0,(int)(0.20*width),(int)(0.35*height));
        layout.addView((View) card2,card2Details);

        //best 5 cards hand
        handCard1 = new DrawCard(this,cardsBmp, new Card(Suit.NONE, Rank.NONE));
        handCard2 = new DrawCard(this,cardsBmp, new Card(Suit.NONE, Rank.NONE));
        handCard3 = new DrawCard(this,cardsBmp, new Card(Suit.NONE, Rank.NONE));
        handCard4 = new DrawCard(this,cardsBmp, new Card(Suit.NONE, Rank.NONE));
        handCard5 = new DrawCard(this,cardsBmp, new Card(Suit.NONE, Rank.NONE));

        RelativeLayout.LayoutParams handcard1Details = new  RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        RelativeLayout.LayoutParams handcard2Details = new  RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        RelativeLayout.LayoutParams handcard3Details = new  RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        RelativeLayout.LayoutParams handcard4Details = new  RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        RelativeLayout.LayoutParams handcard5Details = new  RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        handcard1Details.setMargins((int)(0.20*width),0,(int)(0.62*width),(int)(0.5*height));
        layout.addView((View) handCard1,handcard1Details);

        handcard2Details.setMargins((int)(0.32*width),0,(int)(0.50*width),(int)(0.50*height));
        layout.addView((View) handCard2,handcard2Details);

        handcard3Details.setMargins((int)(0.44*width),0,(int)(0.38*width),(int)(0.50*height));
        layout.addView((View) handCard3,handcard3Details);

        handcard4Details.setMargins((int)(0.56*width),0,(int)(0.26*width),(int)(0.50*height));
        layout.addView((View) handCard4,handcard4Details);

        handcard5Details.setMargins((int)(0.68*width),0,(int)(0.14*width),(int)(0.50*height));
        layout.addView((View) handCard5,handcard5Details);

        handCard1.setVisibility(View.INVISIBLE);
        handCard2.setVisibility(View.INVISIBLE);
        handCard3.setVisibility(View.INVISIBLE);
        handCard4.setVisibility(View.INVISIBLE);
        handCard5.setVisibility(View.INVISIBLE);


        myTurn=false;
        starting=true;
        endRound=false;

        if(savedInstanceState != null){
            myTurn=savedInstanceState.getBoolean("myturn");
            starting=savedInstanceState.getBoolean("starting");
            endRound=savedInstanceState.getBoolean("endRound");
            counter=savedInstanceState.getInt("counter");
            currMaxBet=savedInstanceState.getInt("maxbet");
            actionSelected = (Action) savedInstanceState.getSerializable("action");

            switch (actionSelected){
                case RAISE:
                    raiseBox.setChecked(true);
                    break;
                case CALL_CHECK:
                    callCheckBox.setChecked(true);
                    break;
                case ALL_IN:
                    allInBox.setChecked(true);
                    break;
                case FOLD:
                    foldBox.setChecked(true);
                    break;
            }

            if(myTurn || !starting){
                startButton.setEnabled(false);
                startButton.setVisibility(View.INVISIBLE);
                if(myTurn)
                    handler.postDelayed(myTurnTask, 1000);
            }
        }

        update();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("myturn",myTurn);
        outState.putBoolean("starting",starting);
        outState.putBoolean("endRound",endRound);
        outState.putInt("counter",counter);
        outState.putInt("maxbet",currMaxBet);
        outState.putSerializable("action",actionSelected);

    }


    //update UI

    public void update() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(starting){
                    card1.setVisibility(View.INVISIBLE);
                    card2.setVisibility(View.INVISIBLE);
                    if(endRound) {
                        handCard1.setVisibility(View.VISIBLE);
                        handCard2.setVisibility(View.VISIBLE);
                        handCard3.setVisibility(View.VISIBLE);
                        handCard4.setVisibility(View.VISIBLE);
                        handCard5.setVisibility(View.VISIBLE);
                        endRoundView.setVisibility(View.VISIBLE);
                        ArrayList<Card> best5Cards = Utils.player.getbestFiveCards();
                        handCard1.setCard(best5Cards.get(0));
                        handCard2.setCard(best5Cards.get(1));
                        handCard3.setCard(best5Cards.get(2));
                        handCard4.setCard(best5Cards.get(3));
                        handCard5.setCard(best5Cards.get(4));

                        StringBuilder s = new StringBuilder();

                        if(Utils.player.getWin()){
                            s.append("---You Won---   ");
                            endRoundView.setBackgroundColor(Color.rgb(0,102,0));
                        }else{
                            s.append("---You Lost---   ");
                            endRoundView.setBackgroundColor(Color.rgb(204,0,0));
                        }
                        s.append(Utils.player.getHandRank());
                        endRoundView.setText(s);
                    }
                }else {
                    endRoundView.setVisibility(View.INVISIBLE);
                    handCard1.setVisibility(View.INVISIBLE);
                    handCard2.setVisibility(View.INVISIBLE);
                    handCard3.setVisibility(View.INVISIBLE);
                    handCard4.setVisibility(View.INVISIBLE);
                    handCard5.setVisibility(View.INVISIBLE);
                    card1.setVisibility(View.VISIBLE);
                    card2.setVisibility(View.VISIBLE);
                    card1.setCard(Utils.player.getHand().get(0));
                    card2.setCard(Utils.player.getHand().get(1));
                }
                card1.invalidate();
                card2.invalidate();

                if(myTurn) {
                    raiseSlider.setMax(Utils.player.getMoney()-currMaxBet);
                    if((raiseSlider.getProgress()+currMaxBet) > Utils.player.getMoney()) {//player não tem dinheiro para fazer raise
                        raiseBox.setEnabled(false);
                        if(actionSelected==Action.RAISE){
                            raiseBox.setChecked(false);
                            actionSelected= Action.NONE;
                        }
                    }else
                        raiseBox.setEnabled(true);

                    timeView.setVisibility(View.VISIBLE);
                    timeView.setText("Time: " + counter);
                    playButton.setVisibility(View.VISIBLE);
                    playButton.setEnabled(true);
                }

                StringBuilder s = new StringBuilder();
                s.append("Name: ");
                s.append(Utils.player.getName());
                s.append('\n');
                s.append("Money: ");
                s.append(Utils.player.getMoney());
                s.append('\n');
                s.append("CurrBet: ");
                s.append(Utils.player.getCurrentBet());
                infoView.setText(s);

                s=new StringBuilder();
                s.append((raiseSlider.getProgress()+currMaxBet));
                s.append('/');
                s.append(Utils.player.getMoney());
                raiseValue.setText(s);
            }

        });

    }


    //button handler´s

    public void start (View v){

        starting=false;
        endRound=false;

        startButton.setEnabled(false);
        startButton.setVisibility(View.INVISIBLE);

        if(Utils.player.getMoney() <= 50) {//não tenho dinheiro para continuar a jogar
            leaveTable();
            runOnUiThread(new Runnable(){
                public void run() {
                    Toast.makeText(getApplicationContext(), "Out of money",Toast.LENGTH_LONG).show();
                }
            });
            return;
        }

        Utils.player.setIngame(true);
        try {
            Utils.game.check();
        }catch (Exception e){
            leaveTable();
            runOnUiThread(new Runnable(){
                public void run() {
                    Toast.makeText(getApplicationContext(), "The Server Is Down",Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void play (View v){
        if(actionSelected!= Action.NONE) {
            timeView.setVisibility(View.INVISIBLE);
            playButton.setVisibility(View.INVISIBLE);
            playButton.setEnabled(false);
            myTurn=false;
            new PlayerAction().execute(raiseSlider.getProgress() + currMaxBet);
            counter = 0;
        }
    }


    //checkbox´s  handler´s
    public void raise (View v){
        if(raiseBox.isChecked()){
            actionSelected=Action.RAISE;
            foldBox.setChecked(false);
            callCheckBox.setChecked(false);
            allInBox.setChecked(false);
        }else{
            actionSelected=Action.NONE;
        }
    }

    public void fold (View v){
        if(foldBox.isChecked()){
            actionSelected=Action.FOLD;
            raiseBox.setChecked(false);
            callCheckBox.setChecked(false);
            allInBox.setChecked(false);
        }else{
            actionSelected=Action.NONE;
        }
    }
    public void callCheck (View v){
        if(callCheckBox.isChecked()){
            actionSelected=Action.CALL_CHECK;
            foldBox.setChecked(false);
            raiseBox.setChecked(false);
            allInBox.setChecked(false);
        }else{
            actionSelected=Action.NONE;
        }
    }
    public void allIn (View v){
        if(allInBox.isChecked()){
            actionSelected=Action.ALL_IN;
            foldBox.setChecked(false);
            callCheckBox.setChecked(false);
            raiseBox.setChecked(false);
        }else{
            actionSelected=Action.NONE;
        }
    }



    @Override
    public void notified(NotificationType n, int v) {
        Log.d("notified","" + n);
        switch (n){
            case YOURTURN:
                myTurn(v);
                break;
            case YOULOSE:
                iLose();
                break;
            case YOUWIN:
                iWin(v);
                break;
            case WAITING:
                startNextRound();
                break;
            case UPDATE:
                update();
                break;
        }
    }

    private  void iWin(int v){
        Utils.player.setMoney(Utils.player.getMoney()+v);
        Utils.stats.numberOfWins++;
        if(v < Utils.stats.minPotWin || Utils.stats.minPotWin==0)
            Utils.stats.minPotWin = v;

        if(v > Utils.stats.maxPotWin)
            Utils.stats.maxPotWin=v;


        endRound=true;
        startNextRound();
    }

    private  void iLose(){
        Utils.stats.numberOfLoses++;

        endRound=true;
        startNextRound();
    }


    private  void startNextRound()  {
        starting=true;

        Utils.player.reset();
        update();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startButton.setEnabled(true);
                startButton.setVisibility(View.VISIBLE);
            }
        });


        Utils.stats.playerMoney=Utils.player.getMoney();

        try {
           Utils.saveStats();
        }catch (Exception e){}


    }

    private  void myTurn(int maxBet){

        myTurn=true;

        currMaxBet=maxBet*2;

        if(currMaxBet == 0 )
            currMaxBet=50;

        counter = 50;

        update();

        handler.postDelayed(myTurnTask,1000);
    }



    private class PlayerAction extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer ... params) {


            try {
                Utils.game.playerAction(Utils.player.getName(), actionSelected, params[0]);
            }catch (Exception e){
                    leaveTable();
                    runOnUiThread(new Runnable(){
                        public void run() {
                            Toast.makeText(getApplicationContext(), "The Server Is Down",Toast.LENGTH_LONG).show();
                        }
                    });
            }

            if(Utils.player.getAllIn())
                Utils.stats.numberOfAllin++;



            update();
            return null;
        }

    }


    @Override
    public void onBackPressed() {
       leaveTable();

    }

    public void leave (View v)  {

        leaveTable();
    }

    public void leaveTable(){

        try {
            handler.removeCallbacks(myTurnTask);
            Utils.player.reset();
            Utils.stats.playerMoney=Utils.player.getMoney();
            Utils.saveStats();
            Utils.game.leave(Utils.player.getName());
            Utils.game = null;
        }catch (Exception e){
        }

        Intent i= new Intent(this, MainMenuActivity.class);
        startActivity(i);
        finish();
    }


}
