package com.github.example.richtexteditor.Viewholders;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.irshulx.richtexteditor.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Viewholder_lesson  extends RecyclerView.ViewHolder{


    //declare items in card_view_user
    TextView card_week, card_title;
    public Button btn_edit_lesson, btn_del_lesson, btn_view_lesson;

    Context context;

    public Viewholder_lesson(@NonNull View itemView, Context context) {
        super(itemView);
        this.context=context;
    }

    //set items to user cardview
    public void setitem(String week, String title, String serializetxt ){
        //init
        card_week = itemView.findViewById(R.id.card_tv_week);
        card_title = itemView.findViewById(R.id.card_tv_title);

        btn_del_lesson = itemView.findViewById(R.id.btn_card_del);
        btn_edit_lesson = itemView.findViewById(R.id.btn_card_edit);
        btn_view_lesson = itemView.findViewById(R.id.btn_card_view);

        //settext
        card_week.setText("Week"+week);
        card_title.setText(title);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
