package com.github.example.richtexteditor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.github.irshulx.richtexteditor.R;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        LinearLayout lesson = (LinearLayout) findViewById(R.id.lesson);
        lesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lessonopen();
            }
        });
    }

    private void lessonopen() {

        Intent intent = new Intent(this, NestedLessons.class);
        startActivity(intent);
    }
}
