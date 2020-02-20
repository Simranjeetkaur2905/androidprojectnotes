package com.example.notesapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseNotes mDatabase;
    List<Notes> notesList;

    List<Notes> userlist;
    ListView listView;
Button add;
    ArrayList<String> arrayList = new ArrayList<>();

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.searchbar);
        listView = findViewById(R.id.lvnotes);
        add = findViewById(R.id.addnewnotes);

mDatabase = new DatabaseNotes(this);

userlist = new ArrayList<>();

final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);

listView.setAdapter(arrayAdapter);

loadNotes();

searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(!newText.isEmpty()){
            userlist.clear();
            for(int i = 0; i< notesList.size(); i++) {
                Notes contact = notesList.get(i);
                if (contact.nameofnote.contains(newText)) {
                    notesList.add(contact);
                }
            }
            NotesAdapter pAdapter = new NotesAdapter(MainActivity.this, R.layout.notes_layout_list, userlist, mDatabase);
            listView.setAdapter(pAdapter);
        }
        if(newText.isEmpty()) {
            NotesAdapter pAdapter = new NotesAdapter(MainActivity.this, R.layout.notes_layout_list, userlist, mDatabase);
            listView.setAdapter(pAdapter);
        }
        return  false;
    }
});

add.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this,note_adding.class);
        startActivity(intent);
    }
});

    }



    private void loadNotes() {
        Cursor cursor = mDatabase.getAllNote();
        if(cursor.moveToFirst()){
            do {
                notesList.add(new Notes(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                ));


            }while (cursor.moveToNext());
            cursor.close();
            //show item in a listView
            //we use a custom adapter to show employees

            NotesAdapter notesAdapter = new NotesAdapter(this, R.layout.notes_layout_list,notesList, mDatabase);
            listView.setAdapter(notesAdapter);

        }
    }
}
