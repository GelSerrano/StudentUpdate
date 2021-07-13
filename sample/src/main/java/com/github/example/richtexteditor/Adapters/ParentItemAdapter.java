package com.github.example.richtexteditor.Adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.example.richtexteditor.EditorActivity;
import com.github.example.richtexteditor.LessonView;
import com.github.example.richtexteditor.Models.ChildItem;
import com.github.example.richtexteditor.Models.ParentItem;
import com.github.example.richtexteditor.NestedLessons;
import com.github.irshulx.richtexteditor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ParentItemAdapter
        extends RecyclerView.Adapter<ParentItemAdapter.ParentViewHolder> {

    // An object of RecyclerView.RecycledViewPool
    // is created to share the Views
    // between the child and
    // the parent RecyclerViews
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<ParentItem> itemList;
    private List<ChildItem> citemList;
    private Context context;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public ParentItemAdapter(List<ParentItem> itemList, Context context)
    {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ParentViewHolder onCreateViewHolder( @NonNull ViewGroup viewGroup,int i)
    {

        // Here we inflate the corresponding
        // layout of the parent item
        View view = LayoutInflater .from(viewGroup.getContext()).inflate(
                        R.layout.parent_item, viewGroup, false);

        return new ParentViewHolder(view);
    }

    @Override
    public void onBindViewHolder( @NonNull ParentViewHolder parentViewHolder, int p_position)
    {
        // Create an instance of the ParentItem
        // class for the given position
        ParentItem parentItem  = itemList.get(p_position);

        // For the created instance,
        // get the title and set it
        // as the text for the TextView
        Log.d("parentAdapter", parentItem.getParentItemTitle()+" title");

        parentViewHolder.ParentItemTitle.setText(parentItem.getParentItemTitle());

        // Create a layout manager
        // to assign a layout
        // to the RecyclerView.

        // Here we have assigned the layout
        // as LinearLayout with vertical orientation
        LinearLayoutManager layoutManager  = new LinearLayoutManager(
                parentViewHolder.ChildRecyclerView.getContext(),LinearLayoutManager.VERTICAL,false);

        // Since this is a nested layout, so
        // to define how many child items
        // should be prefetched when the
        // child RecyclerView is nested
        // inside the parent RecyclerView,
        // we use the following method
        layoutManager.setInitialPrefetchItemCount( parentItem.getChildItemList().size());

        // Create an instance of the child
        // item view adapter and set its
        // adapter, layout manager and RecyclerViewPool
        ChildItemAdapter childItemAdapter  = new ChildItemAdapter(parentItem.getChildItemList(), context,
                new ChildItemAdapter.DetailsAdapterListener() {
            @Override
            public void editOnClick(View v, int c_position, String week, String cont) {
                Log.d("ParentAdapter", "pos is " + c_position + " week " + week +
                        parentItem.getParentItemTitle()+" title");
                Log.d("ParentAdapter", "pos is cont " + cont);

                Intent intent = new Intent(context, EditorActivity.class);
                intent.putExtra("content", cont);
                intent.putExtra("source", "edit_lesson");
                Log.d("activitySRC","on parent item is edit lesson");
                intent.putExtra("edit_week", week);
                v.getContext().startActivity(intent);
            }

            @Override
            public void viewOnClick(View v, int c_position, String week, String cont) {
                Log.d("ParentAdapter", "pos is " + c_position + " week ");
                Log.d("ParentAdapter", "pos is cont " + cont);

                Intent intent = new Intent(context, LessonView.class);
                intent.putExtra("content", cont);
                v.getContext().startActivity(intent);
            }

            @Override
            public void deleteOnClick(View v, int c_position, String week, String cont) {
                Log.d("ParentAdapter", "pos is " + c_position + " week ");
                Log.d("ParentAdapter", "pos is cont " + cont);
                //query to firebase then delete item on db

                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Lessons");
                String wk = week.toLowerCase().replaceAll("\\s", "");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()) {
                            // data.getRef().removeValue();

                            Log.d("del_btn", "data key" + data.getKey()); //this is prelim midterm finals
                            Log.d("del_btn", "data children" + data.getChildren());
                            Log.d("del_btn", "snap children" + snapshot.getChildren());
                            Log.d("del_btn", "snap key" + snapshot.getKey());

                            for (DataSnapshot datac : data.getChildren()) {
                               // databaseReference.child(data.getKey()).child(datac.getKey()).child("serializedtext").setValue(editor.getContentAsSerialized());
                                //firebase.child(id).removeValue();
                                Log.d("del_btn_cond",wk.equals(datac.getKey())+" ");
                                if(wk.equals(datac.getKey())){
                                    databaseReference.child(data.getKey()).child(datac.getKey()).removeValue();
                                    Toast.makeText(context, "successfully deleted!", Toast.LENGTH_LONG).show();
                                    //TODO: show alert here
                                    Intent intent = new Intent(context, NestedLessons.class);
                                    v.getContext().startActivity(intent);
                                }
                                Log.d("del_btn", "week is " + wk);
                                Log.d("del_btn", "    data key c" + datac.getKey()); //this is week10
                                Log.d("del_btn", "    data children c" + datac.getChildren());
                                Log.d("del_btn", "    snap children data" + data.getChildren());
                                Log.d("del_btn", "    snap key data" + data.getKey()); //this is period Prelim Midterm Finals
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        }
        );
        parentViewHolder.ChildRecyclerView.setLayoutManager(layoutManager);
        parentViewHolder.ChildRecyclerView.setAdapter(childItemAdapter);
        parentViewHolder.ChildRecyclerView.setRecycledViewPool(viewPool);
    }

    // This method returns the number
    // of items we have added in the
    // ParentItemList i.e. the number
    // of instances we have created
    // of the ParentItemList
    @Override
    public int getItemCount()
    {
        return itemList.size();
    }

    // This class is to initialize
    // the Views present in
    // the parent RecyclerView
    class ParentViewHolder extends RecyclerView.ViewHolder {

        private TextView ParentItemTitle;
        private RecyclerView ChildRecyclerView;

        ParentViewHolder(final View itemView)
        {
            super(itemView);

            ParentItemTitle = itemView.findViewById(R.id.parent_item_title);
            ChildRecyclerView  = itemView.findViewById(R.id.child_recyclerview);
        }
    }
}
