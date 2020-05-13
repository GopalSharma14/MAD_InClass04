package com.example.inclass04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    // Assignment: 04
    // Group: 27
    
    private static final String TAG = "demo";

    private TextView min_tv;
    private TextView max_tv;
    private TextView avg_tv;
    private TextView compl_tv;

    private ProgressBar progressBar;

    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        min_tv = findViewById(R.id.tv_min);
        max_tv = findViewById(R.id.tv_max);
        avg_tv = findViewById(R.id.tv_avg);
        compl_tv = findViewById(R.id.tv_compl);

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(ProgressBar.INVISIBLE);

        seekBar = findViewById(R.id.seekbar);
        compl_tv.setText(Integer.toString(seekBar.getProgress()));

        Button calc_button = findViewById(R.id.btn_calc);

        calc_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetNumbers().execute(seekBar.getProgress());
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(TAG, "Progress is: " + progress);
                compl_tv.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    class GetNumbers extends AsyncTask<Integer, Void, ArrayList<Double>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected ArrayList<Double> doInBackground(Integer... integers) {
            return  HeavyWork.getArrayNumbers(integers[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Double> doubles) {
            super.onPostExecute(doubles);
            Log.d(TAG, "onPostExecute: " + doubles.toString());

            Collections.sort(doubles);

            double min = doubles.get(0);

            double max = doubles.get(doubles.size()-1);

            double sum = 0;
            for(int i = 0; i < doubles.size(); i++)
                sum += doubles.get(i);
            double avg = sum / doubles.size();

            progressBar.setVisibility(ProgressBar.INVISIBLE);
            findViewById(R.id.textView).setVisibility(TextView.VISIBLE);
            findViewById(R.id.textView3).setVisibility(TextView.VISIBLE);
            findViewById(R.id.textView5).setVisibility(TextView.VISIBLE);

            min_tv.setText(Double.toString(min));
            max_tv.setText(Double.toString(max));
            avg_tv.setText(Double.toString(avg));
        }
    }
}
