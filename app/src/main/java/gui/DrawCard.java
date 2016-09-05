package gui;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import java.util.ArrayList;

import common.Card;

public class DrawCard extends View {


    Bitmap cardsBmp;
    int cardWidth;
    int cardHeight;
    Card card;

    public DrawCard(Context context,Bitmap cards,Card c){
        super(context);
        cardsBmp=cards;
        cardWidth=(cardsBmp.getWidth()/13);
        cardHeight=(cardsBmp.getHeight()/5);
        card=c;
    }

    public void setCard(Card c){
        card = c;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        ArrayList<Integer> pos = card.postionOnImage(cardWidth, cardHeight);

       canvas.drawBitmap(cardsBmp,new Rect(pos.get(0),pos.get(1),pos.get(2),pos.get(3)),new Rect(0,0,canvas.getWidth(),canvas.getHeight()),null);

    }




}
