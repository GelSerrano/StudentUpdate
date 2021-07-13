package com.github.example.richtexteditor.Adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.example.richtexteditor.Models.ChildItem;
import com.github.irshulx.richtexteditor.R;

import java.util.List;

public class ChildItemAdapter
        extends RecyclerView
        .Adapter<ChildItemAdapter.ChildViewHolder> {

    private List<ChildItem> ChildItemList;
    private Context context;
    public DetailsAdapterListener onClickListener;

    // Constuctor
    ChildItemAdapter(List<ChildItem> childItemList, Context context, DetailsAdapterListener listener)
    {
        this.ChildItemList = childItemList;
        this.context = context;
        this.onClickListener = listener;
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(
            @NonNull ViewGroup viewGroup, int i)
    {

        // Here we inflate the corresponding
        // layout of the child item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate( R.layout.child_item, viewGroup, false);

        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ChildViewHolder childViewHolder,int position)
    {

        // Create an instance of the ChildItem
        // class for the given position
        ChildItem childItem  = ChildItemList.get(position);

        // For the created instance, set title.
        // No need to set the image for
        // the ImageViews because we have
        // provided the source for the images
        // in the layout file itself

        childViewHolder.ChildItemContent.setText(childItem.getContent());
        childViewHolder.ChildItemWeek.setText(childItem.getChildItemTitle());
        childViewHolder.ChildItemTitle.setText(childItem.getChildItemsubtitle());
    }

    @Override
    public int getItemCount()
    {
        // This method returns the number
        // of items we have added
        // in the ChildItemList
        // i.e. the number of instances
        // of the ChildItemList
        // that have been created
        return ChildItemList.size();
    }

    // This class is to initialize
    // the Views present
    // in the child RecyclerView
    class ChildViewHolder extends RecyclerView.ViewHolder{

        TextView ChildItemWeek;
        TextView ChildItemTitle;
        TextView ChildItemContent;


        Button btn_edit, btn_view, btn_delete;

        ChildViewHolder(View itemView)
        {
            super(itemView);
            ChildItemWeek = itemView.findViewById( R.id.child_item_week);
            ChildItemTitle = itemView.findViewById( R.id.child_item_title);
            ChildItemContent = itemView.findViewById( R.id.child_item_content);
            btn_edit = itemView.findViewById(R.id.btn_card_edit);
            btn_view = itemView.findViewById(R.id.btn_card_view);
            btn_delete = itemView.findViewById(R.id.btn_card_del);

            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.editOnClick(v, getAdapterPosition(),
                            String.valueOf(ChildItemWeek.getText()),
                            String.valueOf(ChildItemContent.getText())
                    );
                }
            });
            btn_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.viewOnClick(v, getAdapterPosition(),
                            String.valueOf(ChildItemWeek.getText()),
                            String.valueOf(ChildItemContent.getText()));
                }
            });
            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.deleteOnClick(v, getAdapterPosition(),
                            String.valueOf(ChildItemWeek.getText()),
                            String.valueOf(ChildItemContent.getText()));
                }
            });
        }
    }

    public interface DetailsAdapterListener {
        void editOnClick(View v, int position, String week, String content_st);
        void viewOnClick(View v, int position, String week, String content_st);
        void deleteOnClick(View v, int position, String week, String content_st);
    }
}
