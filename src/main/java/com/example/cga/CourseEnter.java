package com.example.cga;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CourseEnter extends AppCompatActivity {
    private TextInputLayout noOfCourse, textInputLayout, courseCreditLoadInputLayout, courseScoreInputLayout;
    private int i = 1;
    private String generateCourse;
    private ProgressDialog dialog;
    private ContentValues row;
    private LinearLayout linearLayout;
    private SQLiteDatabase sqLiteDatabase;
    private TextInputEditText courseCode, courseTitle, courseCreditLoad, courseScore;
    private TextView result;
    String[] update;
    List<TextInputEditText> courseTitles = new ArrayList<TextInputEditText>();
    List<TextInputEditText> courseCodes = new ArrayList<TextInputEditText>();
    List<TextInputEditText> courseLoads = new ArrayList<TextInputEditText>();
    List<TextInputEditText> courseScores = new ArrayList<TextInputEditText>();
    String courseTitleId;
    private Button buttonNext;
    private Button button;
    Intent intent;
    AutoCompleteTextView semester, level;
    private TextInputLayout courseCodeInputLayout;
    String value, value1, value2, value3;
    int value3b,value2b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_enter);
        String[] SEMESTERS = new String[]{"_1st-Semester", "_2nd-Semester"};
        String[] LEVELS = new String[]{"_100-Level", "_200-Level", "_300-Level", "_400-Level", "_500-Level", "_600-Level"
                , "_700-Level"};


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.item_list, LEVELS);
        AutoCompleteTextView editTextFilledExposedDropdown =
                findViewById(R.id.autoCompleteViewForLevel);
        editTextFilledExposedDropdown.setAdapter(adapter);
        ArrayAdapter<String> adapterSemester = new ArrayAdapter<>(getApplicationContext(), R.layout.item_list, SEMESTERS);
        AutoCompleteTextView editSEMESTER =
                findViewById(R.id.semester2);
        editSEMESTER.setAdapter(adapterSemester);
        buttonNext = findViewById(R.id.secondButton);


        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseColumn();

            }
        });

    }

    void courseColumn() {
        noOfCourse = findViewById(R.id.numberOfCourses);
        if (!TextUtils.isEmpty(noOfCourse.getEditText().getText().toString())) {
            generateCourse = noOfCourse.getEditText().getText().toString();
            int courseToInt = Integer.parseInt(generateCourse);


            linearLayout = findViewById(R.id.linearLayout);


            for (i = 1; i <= courseToInt; i++) {
                textInputLayout = new TextInputLayout(this);
                courseTitle = new TextInputEditText(this);
                textInputLayout.setHelperText("Enter Course Title for Course " + i + ":");
                textInputLayout.setHelperTextEnabled(true);
                courseTitle.setId(i);
                textInputLayout.setHintTextColor(android.content.res.ColorStateList.valueOf(Color.RED));
                //courseTitle.setHintTextColor(Color.RED);
                courseTitle.setTextColor(Color.RED);

                String a = Integer.toString(i);
                courseTitleId = "courseTitle" + a;
                // update[i]=courseTitleId;

                //For Course Code

                courseCodeInputLayout = new TextInputLayout(this);
                courseCode = new TextInputEditText(this);
                courseCodeInputLayout.setHelperText("Enter Course Code for Course " + i + ":");
                courseCodeInputLayout.setHelperTextEnabled(true);
                courseCode.setAllCaps(true);

                // lastly added
                courseCode.setId(i);

//for Course Credit Load
                courseCreditLoadInputLayout = new TextInputLayout(this);
                courseCreditLoad = new TextInputEditText(this);
                courseCreditLoad.setId(i);
                courseCreditLoadInputLayout.setHelperText("Enter Course Credit or Unit for Course " + i + " only numbers" + ":");
                courseCreditLoadInputLayout.setHelperTextEnabled(true);
                courseCreditLoad.setAllCaps(true);
                courseCreditLoad.setInputType(InputType.TYPE_CLASS_NUMBER);
                courseCreditLoadInputLayout.setCounterEnabled(true);
                courseCreditLoadInputLayout.setCounterMaxLength(1);

                //for Score
                courseScoreInputLayout = new TextInputLayout(this);
                courseScore = new TextInputEditText(this);
                courseScoreInputLayout.setHelperText("Enter Score for Course " + i + " only numbers" + ":");
                courseScoreInputLayout.setHelperTextEnabled(true);
                courseScore.setAllCaps(true);
                courseScore.setId(i);
                courseScore.setInputType(InputType.TYPE_CLASS_NUMBER);
                courseScoreInputLayout.setCounterEnabled(true);
                courseScoreInputLayout.setCounterMaxLength(3);
                Button btn = new Button(this);
                for (int bt = 1; bt <= i; bt++) {
                    btn.setText("STEP " + bt);
                    btn.setVisibility(View.VISIBLE);
                    btn.setTextColor(Color.RED);
                    btn.setBackgroundColor(Color.YELLOW);
                }
                //toString methods of all the inputs
                final String courseT = courseTitle.getText().toString();
                String courseC = courseCode.getText().toString();
                String courseL = courseCreditLoad.getText().toString();
                String courseS = courseScore.getText().toString();
                courseTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.addView(btn);

                linearLayout.addView(textInputLayout);
                linearLayout.addView(courseTitle);
                linearLayout.addView(courseCodeInputLayout);
                linearLayout.addView(courseCode);
                linearLayout.addView(courseCreditLoadInputLayout);
                linearLayout.addView(courseCreditLoad);
                linearLayout.addView(courseScoreInputLayout);
                linearLayout.addView(courseScore);
               /* courseTitle.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if((event !=null && (event.getKeyCode()==KeyEvent.KEYCODE_ENTER)))

                        {
                            getEditTextValue(courseTitle);
                        }
                        return false;
                    }
                });*/
                courseTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(!hasFocus)
                        {
                            getEditTextValue(courseTitle);
                        }
                    }
                });
                courseCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(!hasFocus)
                        {
                            getEditTextValue(courseCode);
                        }
                    }
                });
                courseCreditLoad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(!hasFocus)
                        {
                            getEditTextValue(courseCreditLoad);
                        }
                    }
                });
                courseScore.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(!hasFocus)
                        {
                            getEditTextValue(courseScore);
                        }
                    }
                });
                /*for (i = 1; i <= courseToInt; i++) {
                    courseTitles.add(courseTitle);
                    courseCodes.add(courseCode);
                    courseScores.add(courseScore);
                    courseLoads.add(courseCreditLoad);
                    getEditTextValue(courseTitle);
                }*/
                //remeber to change the button id and name=
                result=new TextView(this);
                button = new Button(this);
                button.setVisibility(View.VISIBLE);
                button.setEnabled(true);
                button.setBackgroundColor(Color.MAGENTA);
                button.setText("Proceed");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog = new ProgressDialog(CourseEnter.this);
                        dialog.setTitle("GUIDE FROM VICTORHEZ!!!");
                        dialog.setMessage("For you to save your result, you must fill all fields provided above");
                        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        dialog.setCancelable(false);
                        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "OKAY", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialoga, int which) {
                                dialog.dismiss();
                                proceedMethod();
                            }
                        });
                        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "GO BACK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialoga, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();


                    }
                });

            }
            linearLayout.addView(button);
            linearLayout.addView(result);
        } else {
            Toast.makeText(this, "Enter Field: ", Toast.LENGTH_LONG).show();
            noOfCourse.setError("ENTER FIELD");

        }

    }

    private void getEditTextValue(final TextInputEditText courseTitle3) {

        int size = courseTitles.size();
        String[] stringsCT = new String[size];
        String[] stringsCC = new String[size];
        String[] stringsCL = new String[size];
        String[] stringsCS = new String[size];
        for (int l = 0; l < size; l++) {
            stringsCT[l] = courseTitles.get(l).getText().toString();
            stringsCC[l] = courseCodes.get(l).getText().toString();
            stringsCL[l] = courseLoads.get(l).getText().toString();
            stringsCS[l] = courseScores.get(l).getText().toString();
            value = stringsCT[l];//STRING VALUE OF COURSE TITLE
            value1 = stringsCC[l];//STRING VALUE OF COURSE CODE
            value2 = stringsCL[l];//STRING VALUE OF COURSE LOAD
            value3 = stringsCS[l];
            value2b=Integer.parseInt(value2);
            value3b=Integer.parseInt(value3);
           //STRING VALUE OF COURSE SCORE
            // STRING VALUE OF COURSE SCORE
        }
    }

    private void proceedMethod() {

        if (TextUtils.isEmpty(courseTitle.getText())) {
            textInputLayout.setError("Error: Enter Field");


        } else if (TextUtils.isEmpty(courseCode.getText())) {
            courseCodeInputLayout.setError("Error: Enter Field");

        } else if (TextUtils.isEmpty(courseCreditLoad.getText())) {
            courseCreditLoadInputLayout.setError("Error: Enter Field");
        } else if (TextUtils.isEmpty(courseScore.getText())) {
            courseScoreInputLayout.setError("Error: Enter Field");

        } else {
            /*semester = findViewById(R.id.semester2);
            level = findViewById(R.id.autoCompleteViewForLevel);

            final String k = semester.getText().toString();
            final String c = level.getText().toString();
            String tableName = c + k;*/
          /* String title= getEditTextValue(courseTitle);
            String code= getEditTextValue(courseCode);
            String load= getEditTextValue(courseCreditLoad);
            int loadb=Integer.parseInt(load);
            String score= getEditTextValue(courseScore);
            int scoreb=Integer.parseInt(score);*/

            DbHandler dbHandler = new DbHandler(CourseEnter.this);
            dbHandler.myDataPut(value,value2,value2b,value3b);
            Toast.makeText(CourseEnter.this, "\"Details Inserted Successfully\" ", Toast.LENGTH_SHORT).show();
            intent = new Intent(CourseEnter.this,ShowActivity.class);
            startActivity(intent);
            //USING SQLlITE DATABASE
            /*sqLiteDatabase = openOrCreateDatabase("cgpa_result", MODE_PRIVATE, null);

            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS course(courseTitle VARCHAR(200), courseCode VARCHAR(200)," +
                    "courseLoad INT,courseScore INT)");

            row = new ContentValues();
            row.put("courseTitle", "ELECHI");
            row.put("courseCode", value1);
            row.put("courseLoad", value2);
            row.put("courseScore", value3);
            sqLiteDatabase.insert("course", null, row);
*/

//Querying my Data
//            Cursor cursor = sqLiteDatabase.rawQuery("select * from course ", null);
//                cursor.moveToNext();

/*
while (cursor.moveToNext()){
    name=cursor.getString(0);
    int courseS=cursor.getInt(2);
    result.setText(name);
}
*/

//            cursor.close();
//            sqLiteDatabase.close();

            //END USING SQLlITE DATABASE

//USING GOOGLE SPREADSHEET

        }


    }
}