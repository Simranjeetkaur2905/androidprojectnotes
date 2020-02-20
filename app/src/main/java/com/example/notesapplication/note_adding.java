package com.example.notesapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class note_adding extends AppCompatActivity implements View.OnClickListener {


    Calendar calendar;

    String todayDate;
    String currenttime;

    EditText nameofnote , Description, date , time;
    DatabaseNotes mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_adding);

        nameofnote = findViewById(R.id.edittextnotename);
        Description = findViewById(R.id.edittextdesc);
        date = findViewById(R.id.edittextdate);
        time = findViewById(R.id.edittexttime);

        mDatabase = new DatabaseNotes(this);

        findViewById(R.id.btnaddperson).setOnClickListener(this);

        findViewById(R.id.tvviewperson).setOnClickListener(this);


//        nameofnote.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                if(s.length() != 0){
//                    getSupportActionBar().setTitle(s);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

//        calendar = Calendar.getInstance();
//        todayDate= calendar.get(Calendar.YEAR)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DAY_OF_MONTH);
//        Log.d("DATE", "Date: "+ todayDate);
//        currenttime = pad(calendar.get(Calendar.HOUR))+":"+pad(calendar.get(Calendar.MINUTE));
//        Log.d("TIME", "Time: "+ currenttime);
//
//        date.setText(todayDate);
//        time.setText(currenttime);
//    }
//    private String pad(int time) {
//        if(time < 10)
//            return "0"+time;
//        return String.valueOf(time);
//
//    }

    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnaddperson:
                addingNotes();
                break;

            case R.id.tvviewperson:
                Intent intent = new Intent(note_adding.this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void addingNotes() {


        String first_name = nameofnote.getText().toString().trim();
        String last_name = Description.getText().toString().trim();
        String phone_number = date.getText().toString().trim();
        String address_person = time.getText().toString().trim();


        //using the calender object to get the current time

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String joiningdate = sdf.format(calendar.getTime());

        if (first_name.isEmpty()){
            nameofnote.setError("name field is empty");
            nameofnote.requestFocus();
            return;
        }
        if (last_name.isEmpty()){
            Description.setError("last field is empty");
            Description.requestFocus();
            return;
        }
        if (phone_number.isEmpty()){
            date.setError("phone field is empty");
            date.requestFocus();
            return;
        }
        if (address_person.isEmpty()){
            time.setError("address field is empty");
            time.requestFocus();
            return;
        }




        //new method
        if (mDatabase.addNote(first_name,last_name,phone_number,address_person))
            Toast.makeText(this, "Employee added", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Employee not added", Toast.LENGTH_SHORT).show();


    }
    }

