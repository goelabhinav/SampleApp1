package com.example.abhinav.diceout;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //field to hold roll result text
    TextView rollResult;


    // field to hold the score
    int score;

    //field to hold scoretext value
    TextView scoreText;

    //field to hold dice value
    int die1;
    int die2;
    int die3;

    //arraylist to hold all 3 dice value
    ArrayList<Integer> dice;

    //arraylist to hold all dice images
    ArrayList<ImageView> diceImageView;


    Random rand;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice(view);
            }
        });
        // set initial score
        score = 0;

        rollResult = (TextView) findViewById(R.id.rollResult);
        scoreText = (TextView) findViewById(R.id.scoreText);


        //Toast
        Toast.makeText(getApplicationContext(), "Welcome to DiceOut!!", Toast.LENGTH_SHORT).show();

        //Initialize random number generator
        rand = new Random();

        //arraylist container for dice values
        dice = new ArrayList<Integer>();

        ImageView die1Image = (ImageView) findViewById(R.id.die1Image);
        ImageView die2Image = (ImageView) findViewById(R.id.die2Image);
        ImageView die3Image = (ImageView) findViewById(R.id.die3Image);

        diceImageView = new ArrayList<ImageView>();
        diceImageView.add(die1Image);
        diceImageView.add(die2Image);
        diceImageView.add(die3Image);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void rollDice(View v) {
        rollResult.setText("Woot Woot");

        //Roll die1
        die1 = rand.nextInt(6) + 1;
        die2 = rand.nextInt(6) + 1;
        die3 = rand.nextInt(6) + 1;


        //set dice value to arraylist
        dice.clear();
        dice.add(die1);
        dice.add(die2);
        dice.add(die3);

        for (int dieofSet = 0; dieofSet < 3; dieofSet++) {
            String imageName = "die_" + dice.get(dieofSet) + ".png";
            try {
                InputStream stream = getAssets().open(imageName);
                Drawable d = Drawable.createFromStream(stream, null);
                diceImageView.get(dieofSet).setImageDrawable(d);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        String msg;
        if(die1==die2 && die1==die3){
            //Triples shot
            int scoreDelta = die1 * 100;
            score += scoreDelta;
            msg = "You rolled a triple "+ die1 + " You scored: " + scoreDelta + " points!!";
        }else if (die1==die2 || die1==die3 || die2==die3){
            //Doubles shot
             score += 50;
            msg = "You rolled doubles for 50 points!!";
        }
        else {
            msg = "You didn't score this roll.Try again!!";
        }

        //rolled die value result
        rollResult.setText(msg);
        scoreText.setText("Score: " + score);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
