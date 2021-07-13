package com.github.example.richtexteditor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.example.richtexteditor.Adapters.ParentItemAdapter;
import com.github.example.richtexteditor.Models.ChildItem;
import com.github.example.richtexteditor.Models.ParentItem;
import com.github.irshulx.richtexteditor.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NestedLessons extends AppCompatActivity {

    private DatabaseReference dbref;
    String[] period = { "Prelim", "Midterm", "Final"};
    String[] title;
    String[] val1 = { "1Prelim","2Midterm","3Final"};
    EditText et_period;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_lessons);

        RecyclerView
                ParentRecyclerViewItem
                = findViewById(
                R.id.parent_recyclerview);


        // Initialise the Linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager( this);
        FloatingActionButton fab = findViewById(R.id.fab);
        // Pass the arguments
        // to the parentItemAdapter.
        // These arguments are passed
        // using a method ParentItemList()
      //  ParentItemAdapter parentItemAdapter = new ParentItemAdapter( ParentItemList());

        // Set the layout manager
        // and adapter for items
        // of the parent recyclerview
//        ParentRecyclerViewItem.setAdapter(parentItemAdapter);
//        
        //TODO: check if lessons values in db changed, then refresh this activity
        List<ParentItem> itemList = new ArrayList<>();

        ParentItemAdapter parentItemAdapter = new ParentItemAdapter( itemList, NestedLessons.this);
        ParentRecyclerViewItem.setAdapter(parentItemAdapter);
        ParentRecyclerViewItem.setLayoutManager(layoutManager);

        dbref = FirebaseDatabase.getInstance().getReference().child("Lessons");
        dbref.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // itemList.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    //Prelim Midterm Finals postSnapshot.getKey()]
                    List<ChildItem> childItemList = new ArrayList<>();
                    childItemList.clear();

                    DataSnapshot lessonsSnapshot = snapshot.child(postSnapshot.getKey());
                    for (DataSnapshot lsnSnapshot: lessonsSnapshot.getChildren()) {
                       // Log.d("itemListtitle",lsnSnapshot.child("title").getValue()+"");
                        ChildItem ci = new ChildItem("Week "+ lsnSnapshot.child("week").getValue(),
                                lsnSnapshot.child("title").getValue()+"",
                                lsnSnapshot.child("serializedtext").getValue()+"");
                        childItemList.add(ci);
                      //  Log.d("itemListChild", ci.getChildItemTitle()+" is added");

                    }
                    ParentItem item  = new ParentItem( postSnapshot.getKey().substring(1) , childItemList);
                    Log.d("itemListParent", item.getParentItemTitle()+" with children: "+item.getChildItemList().get(0));
                    itemList.add(item);

                }
//                ParentItemAdapter parentItemAdapter = new ParentItemAdapter( itemList, NestedLessons.this);
//
//                ParentRecyclerViewItem.setAdapter(parentItemAdapter);
//                ParentRecyclerViewItem.setLayoutManager(layoutManager);
                parentItemAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError);
            }
        });
        
       // NestedLessons.this.recreate(); //refresh activity

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                AlertDialog.Builder alert_builder = new AlertDialog.Builder(NestedLessons.this);

                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.alertdialogue_new_lesson, null);
                alert_builder.setView(alertLayout);
                et_period = alertLayout.findViewById(R.id.et_period);
                EditText et_week = alertLayout.findViewById(R.id.et_week);
                EditText et_title = alertLayout.findViewById(R.id.et_title);

              //  EditText et_period = alertLayout.findViewById(R.id.et_period);
                Spinner spinner1 = (Spinner)alertLayout.findViewById(R.id.spinner_period);
                ArrayAdapter<String> adapter1 =
                        new ArrayAdapter<String>(NestedLessons.this,
                                android.R.layout.simple_spinner_item, period);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//set the default according to value
                spinner1.setSelection(0);
                spinner1.setAdapter(adapter1);
                spinner1.setOnItemSelectedListener(onItemSelectedListener);

                // disallow cancel of AlertDialog on click of back button and outside touch
                // it's a way around to so it can check if edit texts are not null
                alert_builder.setCancelable(false);

                alert_builder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // leave it empty it'll be override bellow
                    }
                });

                alert_builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = alert_builder.create();
                dialog.show();

                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //save edit text value then pass it to EditorActivity
                                if (isEmptyEditText(et_week) || isEmptyEditText(et_title)) {
                                    Toast.makeText(view.getContext(), "Fill in the fields are required!",
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    String period = String.valueOf(et_period.getText());
                                    String weekno = String.valueOf(et_week.getText());
                                    String title = String.valueOf(et_title.getText());

                                    Intent intent = new Intent(getApplicationContext(), EditorActivity.class);
                                    intent.putExtra("periodxtra", period);
                                    intent.putExtra("weekxtra", weekno);
                                    intent.putExtra("titlextra", title);
                                    intent.putExtra("source","new_lesson");

                                    Log.d("extracontent", weekno+" "+period);
                                    startActivity(intent);

                                }
                            }
                        }
                );

            }
        });

    }

    AdapterView.OnItemSelectedListener onItemSelectedListener =
            new AdapterView.OnItemSelectedListener(){

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    String s1 = String.valueOf(val1[position]);
                    et_period.setText(s1);
                    Log.d("et_period", et_period.getText()+" spinner value");
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}

            };

    public static boolean isEmptyEditText(EditText eText) {
        if (TextUtils.isEmpty(eText.getText().toString())) {
            eText.setError("Required!");
            eText.requestFocus();
            return true;
        } else {
            return false;
        }
    }


}

