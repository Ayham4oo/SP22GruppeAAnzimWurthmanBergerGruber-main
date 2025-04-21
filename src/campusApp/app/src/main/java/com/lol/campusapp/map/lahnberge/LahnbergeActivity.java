package com.lol.campusapp.map.lahnberge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lol.campusapp.NavigableActivity;
import com.lol.campusapp.R;
import com.lol.campusapp.map.lahnberge.ConradistrActivity;
import com.lol.campusapp.map.lahnberge.HansMeerweinStrActivity;
import com.lol.campusapp.map.lahnberge.KarlVonFritschStrActivity;

public class LahnbergeActivity extends NavigableActivity {
    private Button conradi;
    private Button hans;
    private Button karl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lahnberge);
        conradi = findViewById(R.id.conradiStr);
        hans = findViewById(R.id.hansmeerweinStr);
        karl = findViewById(R.id.karlfritschStr);

        karl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity(KarlVonFritschStrActivity.class);
            }
        });

        hans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity(HansMeerweinStrActivity.class);
            }
        });

        conradi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity(ConradistrActivity.class);
            }
        });
    }
}