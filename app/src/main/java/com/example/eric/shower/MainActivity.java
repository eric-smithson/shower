/*
*
*/

package com.example.eric.shower;

import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import java.lang.String;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button button;

    private boolean longclick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        longclick=false;

        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longclick=true;
                return true;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            final String[] lyrics = {"Let's get down to business!",
                    "To defeat the huns!",
                    "Did they send me daughters?",
                    "When I asked for sons?",
                    "You're the saddest bunch I ever met",
                    "But you can bet, before we're through",
                    "Mister, I'll make a man",
                    "out of you",
                    "Tranquil as a forest, but on fire within",
                    "Once you find your center, you are sure to win",
                    "You're a spineless, pale, pathetic lot",
                    "And you haven't got a clue",
                    "Somehow I'll make a man",
                    "out of you",
                    "I'm never gonna catch my breath",
                    "Say goodbye to those who knew me",
                    "Boy, was I a fool in school for cutting gym",
                    "This guy's got 'em scared to death",
                    "Hope he doesn't see right through me",
                    "Now I really wish that I knew how to swim",
                    "(Be a man)",
                    "We must be swift as the coursing river",
                    "(Be a man)",
                    "With all the force of a great typhoon",
                    "(Be a man)",
                    "With all the strength of a raging fire",
                    "Mysterious as the dark side of the MOOOOOOOOON",
                    "Time is racing toward us, till the Huns arrive",
                    "Held my every order and you might survive",
                    "You're unsuited for the rage of war",
                    "So pack up, go home, you're through",
                    "How could I make a man",
                    "out of you?",
                    "(Be a man)",
                    "We must be swift as the coursing river",
                    "(Be a man)",
                    "With all the force of a great typhoon",
                    "(Be a man)",
                    "With all the strength of a raging fire",
                    "Mysterious as the dark side of the moon",
                    "(Be a man)",
                    "We must be swift as the coursing river",
                    "(Be a man)",
                    "With all the force of a great typhoon",
                    "(Be a man)",
                    "With all the strength of a raging fire",
                    "Mysterious as the dark side of the MOOOOOOOOON"
            };

            // array that contains the amount of seconds elapsed between lyrics
            // the first value can be adjusted to act as a delay
            final int[] mark = {
                    3, 17, 20, 26, 29, 34, 37, 41, 46, 51, 59,
                    67, 71 , 75, 79, 84, 86, 89, 93, 95, 97,
                    101, 102, 105, 106, 109, 110, 114, 123, 131,
                    139, 143, 147, 151, 155, 157, 159, 160, 164,
                    165, 169, 176 ,177 ,181 , 182 , 185, 186, 189
            };

            @Override
            public void onClick(View v) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
                builder.setSmallIcon(R.drawable.ic_launcher);
                // creates an rv that serves to make every message unique because the Pebble doesn't like duplicate notifications
                Random rand = new Random();
                int rv = rand.nextInt(1000);
                // Sets the title content
                builder.setContentTitle("I'll Make Man Out of You");
                builder.setContentText("\n\n\n\n\n\n\n\n\n\n\n" + rv);

                NotificationManager notifManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                notifManager.notify(42,builder.build());

                recursiveLyrics(0, mark, lyrics);
            }
        });
    }
int p;
    public void recursiveLyrics(int i, final int[] mark, final String[] lyrics) {
        p=i;
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    // Pauses the function for the amount of time between the lyrics
                    Thread.sleep((mark[p + 1] - mark[p]) * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // generates an rv to hide at the end of the notification. The pebble doesn't allow duplicate notifications so this is the work around I found.
                        Random rand = new Random();
                        int rv = rand.nextInt(10000);
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
                        builder.setSmallIcon(R.drawable.ic_launcher);

                        // sets lyric information
                        builder.setContentTitle("");
                        builder.setContentText(lyrics[p] +"\n\n\n\n\n\n"+ rv);
                        NotificationManager notifManager =
                                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        notifManager.notify(42,builder.build());
                    }
                });
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                // stops the function when the lyrics run out or when the user longpresses the button
                if(p >= lyrics.length - 1 || longclick){
                    // resets the longclick so you can start the lyrics again.
                    longclick = false;
                    return;
                }

                //recalls the function, and iterates the lyrics and time array
                recursiveLyrics(p + 1, mark, lyrics);
            }
        }.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
