package com.example.cga;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MainActivity extends AppCompatActivity {
    private  String[] SEMESTERS,LEVELS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         LEVELS = new String[] {"100 Level", "200 Level", "300 Level", "400 Level","500 Level", "600 Level"
        ,"700 Level"};
       SEMESTERS = new String[] {"First Semester", "Second Semester"};
        ArrayAdapter<String> adapter=new ArrayAdapter<>(getApplicationContext(),R.layout.item_list,LEVELS);
        AutoCompleteTextView editTextFilledExposedDropdown =
                findViewById(R.id.autoCompleteView);
        editTextFilledExposedDropdown.setAdapter(adapter);

        //FOR SEMESTER

        ArrayAdapter<String> adapterSemester=new ArrayAdapter<>(getApplicationContext(),R.layout.item_list,SEMESTERS);
        AutoCompleteTextView editSEMESTER =
                findViewById(R.id.semesters);
        editSEMESTER.setAdapter(adapterSemester);
    }
}
