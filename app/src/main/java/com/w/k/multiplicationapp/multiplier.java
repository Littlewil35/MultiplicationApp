package com.w.k.multiplicationapp;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static com.w.k.multiplicationapp.R.id.chronometer1;

public class multiplier extends AppCompatActivity {

    int min1;
    int max1;
    int min2;
    int max2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplier);
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("97A32AA605F4DBF0F3AA307D10CF9942").build();
        adView.loadAd(adRequest);
        EditText answerBox = (EditText) findViewById(R.id.answerBox);
        answerBox.setInputType(InputType.TYPE_NULL);
        Intent intent = getIntent();
        if (intent.getStringExtra("EXTRA_MESSAGE_BOOL").contains("false")) {
            Log.d("William", "isCustom was false. Running standard procedures.");
            String settings = intent.getStringExtra("EXTRA_MESSAGE1");
            TextView Infodisplay = (TextView) findViewById(R.id.Infodisplay);
            Infodisplay.setText(settings);
        } else if (intent.getStringExtra("EXTRA_MESSAGE_BOOL").contains("true")) {
            Log.d("William", "isCustom was true. Obtaining custom vals from intent");
            String settings = intent.getStringExtra("EXTRA_MESSAGE_DESCRIP");
            TextView Infodisplay = (TextView) findViewById(R.id.Infodisplay);
            Infodisplay.setText(settings);
            min1 = Integer.parseInt(intent.getStringExtra("EXTRA_MESSAGE_MIN1"));
            max1 = Integer.parseInt(intent.getStringExtra("EXTRA_MESSAGE_MAX1"));
            min2 = Integer.parseInt(intent.getStringExtra("EXTRA_MESSAGE_MIN2"));
            max2 = Integer.parseInt(intent.getStringExtra("EXTRA_MESSAGE_MAX2"));
        }
    }

    Chronometer chrono;
    int stoppedMilliseconds = 0;
    double avg = 0;
    List<Double> scores = new ArrayList<>();
    List<String> completed = new ArrayList<>();
    List<String> checked = new ArrayList<>();

    public void generateFactors(View view) {
        TextView answerBox = (TextView) findViewById(R.id.answerBox);
        answerBox.setText("");
        TextView Infodisplay = (TextView) findViewById(R.id.Infodisplay);
        String settings = Infodisplay.getText().toString();
        if (settings.contains("1x1")) {
            Log.d("William", "settings contains 1x1");
            min1 = 0;
            max1 = 9;
            min2 = 0;
            max2 = 9;
        } else if (settings.contains("2x2")) {
            Log.d("William", "settings contains 2x2");
            min1 = 0;
            max1 = 99;
            min2 = 0;
            max2 = 99;
        } else if (settings.contains("1x2")) {
            Log.d("William", "settings contains 1x2");
            min1 = 0;
            max1 = 9;
            min2 = 0;
            max2 = 99;
        }
        Infodisplay.setText("");
        chrono = (Chronometer) findViewById(chronometer1);
        chrono.setText(R.string.blank_chrono);
        String chronoText = chrono.getText().toString();
        String array[] = chronoText.split(":");
        if (array.length == 2) {
            stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 1000
                    + Integer.parseInt(array[1]) * 1000;
        } else if (array.length == 3) {
            stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 60 * 1000
                    + Integer.parseInt(array[1]) * 60 * 1000
                    + Integer.parseInt(array[2]) * 1000;
        }
        chrono.setBase(SystemClock.elapsedRealtime() - stoppedMilliseconds);
        chrono.start();
        Random r = new Random();
        int firstFactor = r.nextInt(max1 - min1 + 1) + min1;
        int secondFactor = r.nextInt(max2 - min2 + 1) + min2;
        int track = 0;
        while ((completed.contains(String.valueOf(firstFactor) + " and " + String.valueOf(secondFactor))) & track < 11) {
            Log.d("William", "Got a double. The repeated pair was " + String.valueOf(firstFactor) + " , " + String.valueOf(secondFactor));
            firstFactor = r.nextInt(max1 - min1 + 1) + min1;
            secondFactor = r.nextInt(max2 - min2 + 1) + min2;
            Log.d("William", "New generated pair is " + String.valueOf(firstFactor) + " and " + String.valueOf(secondFactor));
            track++;

        }
        if (track == 11){
            Log.d("William", "All possible matches complete!");
            Context context = getApplicationContext();
            CharSequence text = "You have completed all the factors in this set. Your average score is " + avg + " seconds.";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            toast.show();
            finish();
        }else {
            Log.d("William", "list of completed factors: " + completed.toString());
            TextView product1 = (TextView) findViewById(R.id.product1);
            product1.setText(Integer.toString(firstFactor));
            TextView product2 = (TextView) findViewById(R.id.product2);
            product2.setText(Integer.toString(secondFactor));
        }




    }


    public void checkAnswer(View view) {
        TextView answerBox = (TextView) findViewById(R.id.answerBox);
        TextView product1 = (TextView) findViewById(R.id.product1);
        if (product1.getText().toString().isEmpty() || answerBox.getText().toString().isEmpty()) {
            Log.d("William", "Nothing to check");
        } else {


            int a = Integer.parseInt(product1.getText().toString());
            TextView product2 = (TextView) findViewById(R.id.product2);
            int b = Integer.parseInt(product2.getText().toString());
            int rightAns = a * b;
            int usrAns = Integer.parseInt(answerBox.getText().toString());
            if (rightAns == usrAns) {
                if (checked.contains(String.valueOf(a) + " and " + String.valueOf(b))) {
                    TextView Infodisplay = (TextView) findViewById(R.id.Infodisplay);
                    Infodisplay.setText(R.string.already_checked);
                    Log.d("William", "Looks like you've already checked that one...let's move on to the next one.");
                } else {
                    checked.add(String.valueOf(a) + " and " + String.valueOf(b));
                    completed.add(String.valueOf(a) + " and " + String.valueOf(b));
                    Log.d("William", "List of checked answers: " + String.valueOf(checked));
                    Log.d("William", "Correct!");
                    TextView Infodisplay = (TextView) findViewById(R.id.Infodisplay);
                    chrono = (Chronometer) findViewById(chronometer1);
                    chrono.stop();

                    double elapsedSec = (SystemClock.elapsedRealtime() - chrono.getBase());
                    elapsedSec= elapsedSec/1000;
                    scores.add(elapsedSec);
                    Log.d("William", "Current list of scores: " + scores.toString());
                    int i = 0;
                    double sum = 0;
                    while (i != scores.size()) {
                        double listScore = scores.get(i);
                        sum = sum + listScore;
                        i = i + 1;
                    }
                    Log.d("William", "Value of the sum of all items in list: " + String.valueOf(sum));
                    Log.d("William", "Size of list: " + String.valueOf(scores.size()));


                    avg = sum / scores.size();
                    Log.d("William", "Average before rounding was: "+avg);
                    avg = Math.floor(avg * 100) / 100;
                    Log.d("William", "Average after rounding was: "+avg);
                    Log.d("William", "average equals: " + String.valueOf(avg));
                    if (checked.size() == max1 * max2) {
                        Log.d("William", "All possible matches complete!");
                        Infodisplay.setText("Correct! You're time was " + elapsedSec + " seconds. Your average score is " + avg + " seconds. You have now completed all the possible factors!");
                        Context context = getApplicationContext();
                        CharSequence text = "You have completed all the factors in this set. Your average score is " + avg + " seconds.";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        toast.show();
                        finish();
                    } else {
                        Infodisplay.setText("Correct! Your time was " + elapsedSec + " seconds. Your average score is " + avg + " seconds.");
                    }

                }


            } else {
                Log.d("William", "Incorrect. Try again.");
                TextView Infodisplay = (TextView) findViewById(R.id.Infodisplay);
                Infodisplay.setText(R.string.wrong);

            }
        }
    }

    public void numberButton(View view) {
        EditText answerBox = (EditText) findViewById(R.id.answerBox);
        String tag = view.getTag().toString();

        if (tag.equals("Back") && !(answerBox.getText().toString().equals(""))) {
            String boxText = answerBox.getText().toString();
            answerBox.setText(boxText.substring(0, boxText.length() - 1));

        } else if (!tag.equals("Back")){
            String contents = answerBox.getText().toString() + tag;
            answerBox.setText(contents);
            answerBox.setSelection(contents.length());
        }

    }
}




/**if (listScore >= 100) {
 int miniListScore = listScore / 100;
 listScore = listScore - (40 * miniListScore);
 Log.d("William", "new value of score after performing algorithm: " + String.valueOf(listScore));
 }


 DecimalFormat df = new DecimalFormat("#.####");
 df.setRoundingMode(RoundingMode.CEILING);
 for (Number n : Arrays.asList(12, 123.12345, 0.23, 0.1, 2341234.212431324)) {
 Double d = n.doubleValue();
 System.out.println(df.format(d));
 }


 **/