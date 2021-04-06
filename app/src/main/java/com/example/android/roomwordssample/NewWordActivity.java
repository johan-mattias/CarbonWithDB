package com.example.android.roomwordssample;

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Activity for entering a word.
 */

public class NewWordActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener, View.OnClickListener{

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText mEditWordView;
    private EditText editTextCircumference, editTextHeight, editTextResult;
    private CheckBox forkedStem;
    private Button button;
    private Spinner spinner;

    String tree = "";

    String[] tree_types = { "Ask", "Al", "Asp", "Bok", "Björk", "Ek", "Gran", "Lärk", "Tall" };
    private WordViewModel mWordViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        editTextCircumference = findViewById(R.id.editTextCircumference);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextResult = findViewById(R.id.editTextResult);
        forkedStem = findViewById(R.id.forkedStem);
        button = findViewById(R.id.button);
        button.setOnClickListener(this);
        mWordViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(WordViewModel.class);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(view -> {
            /*Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(editTextResult.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String word = editTextResult.getText().toString();
                replyIntent.putExtra(EXTRA_REPLY, word);
                setResult(RESULT_OK, replyIntent);
            }*/
            /* TODO: change the hardcoded height and circum values to the input values*/
            Word word = new Word(editTextResult.getText().toString(), "555", "20");
            mWordViewModel.insert(word);
            finish();
        });

        spinner = (Spinner) findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter adapter_spin
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                tree_types);

        adapter_spin.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        spinner.setAdapter(adapter_spin);
    }

    @Override
    public void onClick(View v) {
        int circumference = Integer.parseInt(editTextCircumference.getText().toString());
        double diameter = (circumference / 3.14) / 100;
        double height = Integer.parseInt(editTextHeight.getText().toString());
        boolean fork = forkedStem.isChecked();
        double VS = 0.0;
        double VG = 0.0;
        double VT = 0.0;
        Log.d("YOLO", "test");

        tree = (String) spinner.getSelectedItem();
        Log.d("YOLO", tree);

        switch (tree) {
            case "Ask":
                if (fork) {
                    double temp1 = 0.03310 * (diameter * diameter);
                    double temp2 = 0.03593 * ((diameter * diameter) * height);
                    double temp3 = 0.04127 * (diameter * height);
                    VT = temp1 + temp2 + temp3;
                } else {
                    double temp1 = 0.03310 * (diameter * diameter);
                    double temp2 = 0.03246 * ((diameter * diameter) * height);
                    double temp3 = 0.04127 * (diameter * height);
                    VT = temp1 + temp2 + temp3;
                }
                break;

            case "Al":
                if (fork) {
                    String missingFunction = "Missing Function";
                    /*float toast = Toast.makeText(applicationContext, missingFunction, Toast.LENGTH_LONG);
                    toast.show();*/
                } else {
                    double temp1 = 0.1926 * (diameter * diameter);
                    double temp2 = 0.01631 * ((diameter * diameter) * height);
                    double temp3 = 0.003755 * (diameter * (height * height));
                    double temp4 = 0.02756 * (diameter * height);
                    double temp5 = 0.000499 * ((diameter * diameter) * (height * height));
                    VT = temp1 + temp2 + temp3 - temp4 + temp5;
                }
                break;
        /*
            "Asp" -> {
                if (forkedStem.isChecked) {
                    val missingFunction = "Missing Function"
                    val toast = Toast.makeText(applicationContext, missingFunction, Toast.LENGTH_LONG)
                    toast.show()
                } else {
                    if (diameter < 8) {
                        var temp1 = 0.0355 * ((diameter * diameter) * height)
                        var temp2 = 0.0205 * (diameter * height)
                        var temp3 = 0.2177 * diameter
                        VT = (temp1 + temp2 + temp3) - 0.0397
                    } else {
                        var temp1 = (-0.04755)
                        var temp2 = 0.0699 * diameter
                        var temp3 = (-0.00023 * (diameter * diameter))
                        var temp4 = 0.00004 * ((diameter * diameter) * height)
                        VT = (temp1 + temp2 + temp3 + temp4) * 1000
                    }
                }
            }

            "Bok" -> {
                if (forkedStem.isChecked) {
                    var temp1 = 0.01936 * ((diameter * diameter) * height)
                    var temp2 = (-0.24212 * (diameter * height))
                    var temp3 = (-0.0003486 * ((diameter * diameter) * (height * height)))
                    VG = temp1 + temp2 + temp3

                    if (VG < 0) {
                        VG = 0.0
                    }

                    var temp4 = 0.01681 * ((diameter * diameter) * height)
                    var temp5 = 0.12368 * (diameter * diameter)
                    var temp6 = 0.0004701 * ((diameter * diameter) * (height * height))
                    var temp7 = 0.00622 * (diameter * (height * height))
                    VS = temp4 + temp5 + temp6 + temp7
                } else {
                    var temp5 = 0.02080 * ((diameter * diameter) * height)
                    var temp6 = (-0.24212 * (diameter * height))
                    var temp7 = (-0.0003486 * ((diameter * diameter) * (height * height)))
                    VG = temp5 + temp6 + temp7

                    if (VG < 0) {
                        VG = 0.0
                    }

                    var temp1 = 0.01275 * ((diameter * diameter) * height)
                    var temp2 = 0.12368 * (diameter * diameter)
                    var temp3 = 0.0004701 * ((diameter * diameter) * (height * height))
                    var temp4 = 0.00622 * (diameter * (height * height))
                    VS = temp1 + temp2 + temp3 + temp4
                }

                VT = VS + VG

            }

            "Björk" -> { // vet inte hur jag ska få in dessa
                var temp1 = 0.09595 * (diameter * diameter)
                var temp2 = 0.02375 * ((diameter * diameter) * height)
                var temp3 = 0.01221 * (diameter * (height * height))
                var temp4 = 0.03636 * (height * height)
                var temp5 = 0.004605 * (diameter * height) //*B
                VT = temp1 + temp2 + temp3 - temp4 - temp5
            }

            "Ek" -> {
                if (forkedStem.isChecked) {

                    var temp1 = 0.02729 * (diameter * (2 * height))
                    var temp2 = (-0.3178 * (diameter * height))
                    var temp3 = (-0.0006658 * (diameter * (2 * height))) // are these supposed to be raised by 2?
                    VG = temp1 + temp2 + temp3

                    if (VG < 0) {
                        VG = 0.0
                    }

                    if (height >= 10) {
                        var temp1 = 0.03829 * ((diameter * diameter) * height)
                        var temp2 = 0.08772 * (diameter * height)
                        var temp3 = (-0.04905 * (diameter * diameter))
                        VS = temp1 + temp2 + temp3
                    } else {
                        var temp1 = 0.03829 * ((diameter * diameter) * height)
                        var temp2 = 0.08772 * (diameter * height)
                        var temp3 = (-0.04905 * (diameter * diameter))
                        var temp4 = (1 - height / 10) * (1 - height / 10)
                        var temp5 = ((0.01682 * ((diameter * diameter) * height)) + (0.01108 * diameter * height) - (0.02167 * diameter * (height * height)) + (0.04905 * (diameter * diameter)))
                        VS = (temp1 + temp2 + temp3) + temp4 * temp5
                    }

                } else {

                    var temp1 = 0.02813 * ((diameter * diameter) * height)
                    var temp2 = (-0.3178 * (diameter * height))
                    var temp3 = (-0.0006658 * ((diameter * diameter) * (height * height)))
                    VG = temp1 + temp2 + temp3

                    if (VG < 0) {
                        VG = 0.0
                        val test = "this doesnt work"
                        val toast = Toast.makeText(applicationContext, test, Toast.LENGTH_LONG)
                        toast.show()
                    }

                    if (height >= 10) {
                        var temp1 = 0.03522 * (diameter * diameter) * height
                        var temp2 = 0.08772 * (diameter * height)
                        var temp3 = 0.04905 * (diameter * diameter)
                        VS = (temp1 + temp2 - temp3)
                    } else {
                        var temp1 = 0.03522 * ((diameter * diameter) * height)
                        var temp2 = 0.08772 * (diameter * height)
                        var temp3 = 0.04905 * (diameter * diameter)
                        var temp4 = (1 - height / 10) * (1 - height / 10)
                        var temp5 = ((0.01682 * ((diameter * diameter) * height)) + (0.01108 * diameter * height) - (0.02167 * diameter * (height * height)) + (0.04905 * (diameter * diameter)))
                        VS = (temp1 + temp2 + temp3) + temp4 * temp5
                    }
                }

                VT = VS + VG

            }

            "Gran" -> { // vet inte hur jag ska göra detta
                var temp1 = 0.1086 * (diameter * diameter)
                var temp2 = 0.01712 * ((diameter * diameter) * height)
                var temp3 = 0.008905 * (diameter * (height * height))
                VT = 0.22 + temp1 + temp2 + temp3
            }

            "Lärk" -> { // vad innebär större/mindre funktion?

            }

            "Tall" -> { // vet inte hur jag ska göra detta
                var temp1 = 0.1066 * (diameter * diameter)
                var temp2 = 0.02085 * ((diameter * diameter) * height)
                var temp3 = 0.008427 * (diameter * (height * height))
                VT = 0.22 + temp1 + temp2 + temp3
            }

        }
        */
        }
        Log.d("yolo", VT+"");

        editTextResult.setText(VT+"");

    }

    @Override
    public void onItemSelected(AdapterView arg0,
                               View arg1,
                               int position,
                               long id)
    {

        Toast.makeText(getApplicationContext(),
                tree_types[position],
                Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onNothingSelected(AdapterView arg0)
    {
        // Auto-generated method stub
    }
}

