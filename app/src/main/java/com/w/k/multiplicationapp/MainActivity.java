package com.w.k.multiplicationapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Boolean isCustom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addItemsToSpinner();
    }
    public void opt1(View view){
        Log.d("William", "1x1 pressed.");
        isCustom= false;
        Intent intent = new Intent(this,multiplier.class);
        intent.putExtra("EXTRA_MESSAGE1","You have chosen 1x1! Press new to generate a pair of factors, enter your answer, and then " +
                "press check to see if your answer is correct!");

        intent.putExtra("EXTRA_MESSAGE_BOOL", String.valueOf(isCustom));
        startActivity(intent);
    }
    public void opt2(View view){
        Log.d("William", "1x2 pressed.");
        isCustom = false;
        Intent intent = new Intent(this,multiplier.class);
        intent.putExtra("EXTRA_MESSAGE1","You have chosen 1x2! Press new to generate a pair of factors, enter your answer, and then " +
                "press check to see if your answer is correct!");
        intent.putExtra("EXTRA_MESSAGE_BOOL", String.valueOf(isCustom));
        startActivity(intent);
    }
    public void opt3 (View view){
        Log.d("William", "2x2 pressed.");
        isCustom = false;
        Intent intent = new Intent(this,multiplier.class);
        intent.putExtra("EXTRA_MESSAGE1","You have chosen 2x2! Press new to generate a pair of factors, enter your answer, and then " +
                "press check to see if your answer is correct!");
        intent.putExtra("EXTRA_MESSAGE_BOOL", String.valueOf(isCustom));
        startActivity(intent);
    }
    public void customset (View view){
        Log.d("William", "Custom values selected. Ensuring values.");
        Spinner factor1min = (Spinner)findViewById(R.id.factor1min);
        Spinner factor1max = (Spinner)findViewById(R.id.factor1max);
        Spinner factor2min = (Spinner)findViewById(R.id.factor2min);
        Spinner factor2max = (Spinner)findViewById(R.id.factor2max);
        int customMin1 = Integer.parseInt(factor1min.getSelectedItem().toString());
        int customMax1 = Integer.parseInt(factor1max.getSelectedItem().toString());
        int customMin2 = Integer.parseInt(factor2min.getSelectedItem().toString());
        int customMax2 = Integer.parseInt(factor2max.getSelectedItem().toString());
        isCustom = true;
        if(customMax1 <= customMin1 || customMax2<= customMin2){
            Log.d("William", "Max must be larger than min for both factors.");
            Context context = getApplicationContext();
            CharSequence text = "The maximum must be larger than the minimum for both factors.";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }else {
            Log.d("William", "Values secured. Creating intent.");
            Intent intent = new Intent(this, multiplier.class);
            intent.putExtra("EXTRA_MESSAGE_DESCRIP", "Cutsom values selected! Press new to generate a pair of factors, enter your answer, and then " +
                    "press check to see if your answer is correct!");
            intent.putExtra("EXTRA_MESSAGE_BOOL", String.valueOf(isCustom));
            intent.putExtra("EXTRA_MESSAGE_MIN1", String.valueOf(customMin1));
            intent.putExtra("EXTRA_MESSAGE_MAX1", String.valueOf(customMax1));
            intent.putExtra("EXTRA_MESSAGE_MIN2", String.valueOf(customMin2));
            intent.putExtra("EXTRA_MESSAGE_MAX2", String.valueOf(customMax2));
            startActivity(intent);
        }
    }
    public void addItemsToSpinner() {

        Spinner factor1min = (Spinner) findViewById(R.id.factor1min);
        Spinner factor1max = (Spinner) findViewById(R.id.factor1max);
        Spinner factor2min = (Spinner) findViewById(R.id.factor2min);
        Spinner factor2max = (Spinner) findViewById(R.id.factor2max);
        List<String> list = new ArrayList<>();
        int i = 1;
        while(i!= 100) {
            list.add(String.valueOf(i));
            i = i+1;
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        factor1min.setAdapter(dataAdapter);
        factor1max.setAdapter(dataAdapter);
        factor2min.setAdapter(dataAdapter);
        factor2max.setAdapter(dataAdapter);
        factor1min.setOnItemSelectedListener(this);
        factor1max.setOnItemSelectedListener(this);
        factor2min.setOnItemSelectedListener(this);
        factor2max.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner factor1min = (Spinner)findViewById(R.id.factor1min);
        Spinner factor1max = (Spinner)findViewById(R.id.factor1max);
        Spinner factor2min = (Spinner)findViewById(R.id.factor2min);
        Spinner factor2max = (Spinner)findViewById(R.id.factor2max);
        TextView descriptioncustom = (TextView)findViewById(R.id.descriptioncustom);
        descriptioncustom.setText("Random integer between "+ factor1min.getSelectedItem()+ " and "+
        factor1max.getSelectedItem() +" multiplied by a random integer between "+ factor2min.getSelectedItem() +
        " and "+ factor2max.getSelectedItem()+".");

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }




}
