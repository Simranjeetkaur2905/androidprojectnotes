package com.example.notesapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class NotesAdapter extends ArrayAdapter {
    Context mContext;
    int layoutresources;
    List<Notes> notes;
    DatabaseNotes mDataBase;

    public NotesAdapter(@NonNull Context mContext, int layoutresources,List<Notes> notes , DatabaseNotes mDatabase) {

        super(mContext, layoutresources , notes);
        this.mContext = mContext;
        this.layoutresources = layoutresources;
        this.notes = notes;
        this.mDataBase = mDatabase;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(layoutresources, null);
        TextView firstname = v.findViewById(R.id.tv_nameofnote);
        TextView lastname = v.findViewById(R.id.tv_description);
        TextView phone = v.findViewById(R.id.tv_date);
        TextView address = v.findViewById(R.id.tv_time);

        final Notes person = notes.get(position);
        firstname.setText(person.getNameofnote());
        lastname.setText(person.getDescription());
        phone.setText(person.getDate());
        address.setText(person.getTime());

        v.findViewById(R.id.btn_edit_person).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePerson(person);
            }
        });
        v.findViewById(R.id.btn_delete_person).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote(person);
            }
        });



return v;

    }


    private void deleteNote(final Notes note) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Are you sure?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*
                String sql = "DELETE FROM employees WHERE id = ?";
                mDatabase.execSQL(sql,new Integer[]{employee.getId()});

                 */

                if(mDataBase.deleteNote(note.getId()))
                    loadPersons();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void loadPersons() {

        Cursor cursor = mDataBase.getAllNote();

        if(cursor.moveToFirst()){
            notes.clear();
            do{
                notes.add(new Notes(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                ));
            }while (cursor.moveToNext());

            cursor.close();
        }
        notifyDataSetChanged();

    }

    private void updatePerson(final Notes note) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.dialog_notes_layout, null);
        builder.setView(v);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();


        final EditText etnotesname = v.findViewById(R.id.edittextnotename);
        final EditText etdes = v.findViewById(R.id.edittextdesc);
        final EditText etphone = v.findViewById(R.id.edittextdate);
        final EditText etaddress = v.findViewById(R.id.edittexttime);

        // String[] deptarray = mContext.getResources().getStringArray(R.array.departments);
        // int position = Arrays.asList(deptarray).indexOf(employee.getDept());

        etnotesname.setText(note.getNameofnote());
        etdes.setText(note.getDescription());
        etphone.setText(note.getDate());
        etaddress.setText(note.getTime());

        v.findViewById(R.id.btnupdateperson).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String notes_name = etnotesname.getText().toString().trim();
                String descr = etdes.getText().toString().trim();
                String phone_number = etphone.getText().toString().trim();
                String address_person = etaddress.getText().toString().trim();



//                if(first_name.isEmpty()){
//                    etfirstname.setError("name field is empty");
//                    etfirstname.requestFocus();
//                    return;
//                }
//
//                if(last_name.isEmpty()){
//                    etlastname.setError("name field is empty");
//                    etlastname.requestFocus();
//                    return;
//                }
//
//                if(address_person.isEmpty()){
//                    etaddress.setError("name field is empty");
//                    etaddress.requestFocus();
//                    return;
//                }
//                if(phone_number.isEmpty()){
//                    etphone.setError("name field is empty");
//                    etphone.requestFocus();
//                    return;
//                }
/*
                String sql = " UPDATE employees SET name =?,salary =?,department=? WHERE id = ?";
              mDatabase.execSQL(sql,new String[]{ name,salary,dept, String.valueOf(employee.getId())});
                Toast.makeText(mContext, "employee update", Toast.LENGTH_SHORT).show();

 */
                if(mDataBase.updateNote(note.getId(),notes_name,descr,phone_number,address_person)){
                    Toast.makeText(mContext, "employee update", Toast.LENGTH_SHORT).show();
                    loadPersons();
                }
                else
                    Toast.makeText(mContext, "employee not update", Toast.LENGTH_SHORT).show();

                // loadEmployees();
                alertDialog.dismiss();

            }
        });



    }
    }
