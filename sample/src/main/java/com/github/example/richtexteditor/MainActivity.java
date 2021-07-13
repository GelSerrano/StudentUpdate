package com.github.example.richtexteditor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.example.richtexteditor.Models.Lesson;
import com.github.example.richtexteditor.Viewholders.Viewholder_lesson;
import com.github.irshulx.richtexteditor.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference dbref;
    private RecyclerView recyclerView;
    Lesson lesson;
    //Adapter_Lessons adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);

        //retrieve db content in recyrcler view

        //recycler
        recyclerView = findViewById(R.id.lessons_recycler_prelim);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        lesson = new Lesson();

        dbref = FirebaseDatabase.getInstance().getReference().child("Lessons");
        FirebaseRecyclerOptions<Lesson> options
                = new FirebaseRecyclerOptions.Builder<Lesson>()
                .setQuery(dbref.child("Prelim"), Lesson.class)
                .build();

        FirebaseRecyclerAdapter<Lesson, Viewholder_lesson> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Lesson, Viewholder_lesson>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull Viewholder_lesson holder, int position, @NonNull Lesson model) {
                        //week title

                        holder.setitem(model.getWeek(), model.getTitle(), model.getSerializedtext());

                        //codes for knowing which card is selected gets item position then the value in the card
                        final String position_key = getRef(position).getKey(); //get firebase key

                        String week = getItem(position).getWeek();
                        String title = getItem(position).getTitle();
                        String content = getItem(position).getSerializedtext();

//                        //get current user query
                        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Lessons");
                        Query query = dbref.child("Prelim").orderByChild("week").equalTo("week"+week);

                        holder.btn_view_lesson.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //Snackbar.make(view, "view is clicked"+week+" "+title, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                        //get selected lesson then pass it to Lesson view activity

                                        Intent intent = new Intent(holder.getContext(), LessonView.class);
//                                        intent.putExtra("weekxtra", week);
//                                        intent.putExtra("titlextra", title);
                                        intent.putExtra("content", content);
                                        startActivity(intent);

                                    }
                                }
                        );

                        holder.btn_edit_lesson.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Snackbar.make(view, "view is clicked"+week+" "+title+ " positionkey "+position_key, Snackbar.LENGTH_LONG).setAction("Action", null).show();

                                    }
                                }
                        );

                        holder.btn_del_lesson.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Snackbar.make(view, "del is clicked", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                    //check which period this is then get position key to delete

                                        dbref.orderByChild("week").equalTo(week).addListenerForSingleValueEvent(
                                                new ValueEventListener() {

                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        for(DataSnapshot data: snapshot.getChildren()){
                                                            data.getRef().removeValue();
                                                            Log.d("datagetref", data.getRef()+"");
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {
                                                    }
                                                }
                                        );

//                                        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
//                                            @Override
//                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                                for(DataSnapshot ds: snapshot.getChildren()){
//
//                                                    Log.d("dbrefcond",ds.getKey().equals("week"+week)+" "+ ds.getKey()+ " week"+ week );
//                                                    if(ds.getKey().equals("week"+week)){
//                                                        //dbref.child(snapshot.getKey()).child(ds.getKey()).removeValue();
//                                                        Log.d("dbrefcond", snapshot.getKey()+ " "+ ds.getKey());
//                                                        Log.d("dbrefcondtoremove",  dbref.child(snapshot.getKey()).child(ds.getKey())+"");
//                                                        dbref.child(snapshot.getKey()).child(ds.getKey()).removeValue();
//                                                    }
//                                                    Log.d("dsref_is",ds.getRef() +"");
//                                                    Log.d("dbrefval", "ds value: "+ds.getValue());
//                                                    Log.d("dbrefkey", "ds key: "+ ds.getKey());
//                                                    Log.d("dbrefsnapval", "snap value" + snapshot.getValue());
//                                                    Log.d("dbrefsnapkey", "key value" +snapshot.getKey());
//                                                };
//
//                                            }
//
//                                            @Override
//                                            public void onCancelled(@NonNull DatabaseError error) {
//
//                                            }
//                                        });
//                                        dbref.addValueEventListener(new ValueEventListener() {
//                                            @Override
//                                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                                for (DataSnapshot data : dataSnapshot.getChildren()) {
//                                                    if(data.getKey().equals("address")){
//                                                        String orderNumber = data.getValue().toString();
//                                                        Log.d("Specific Node Value" , orderNumber);
//                                                    }
//                                                }
//                                            }
//                                            @Override
//                                            public void onCancelled(DatabaseError databaseError) {
//                                            }
//                                        });

                                        ;
                                    }
                                }
                        );



                    }

                    @NonNull
                    @Override
                    public Viewholder_lesson onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.cardview_lesson, parent, false);
                        return new Viewholder_lesson(view, MainActivity.this);
                    }

                };


        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                AlertDialog.Builder alert_builder = new AlertDialog.Builder(MainActivity.this);

                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.alertdialogue_new_lesson, null);
                alert_builder.setView(alertLayout);

                EditText et_period = alertLayout.findViewById(R.id.et_period);
                EditText et_week = alertLayout.findViewById(R.id.et_week);
                EditText et_title = alertLayout.findViewById(R.id.et_title);
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
                                if (isEmptyEditText(et_period) || isEmptyEditText(et_week) || isEmptyEditText(et_title)) {
                                    Toast.makeText(view.getContext(), "Fill in the fields are required!",
                                            Toast.LENGTH_LONG).show();
                                } else {
                                        String period = String.valueOf(et_period.getText());
                                        String weekno = String.valueOf(et_week.getText());
                                        String title = String.valueOf(et_title.getText());
                                        Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                                        intent.putExtra("periodxtra", period);
                                        intent.putExtra("weekxtra", weekno);
                                        intent.putExtra("titlextra", title);

                                        startActivity(intent);


                                }
                            }
                        }
                );

            }
        });
    }

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