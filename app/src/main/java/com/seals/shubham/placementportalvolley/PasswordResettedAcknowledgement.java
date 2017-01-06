package com.seals.shubham.placementportalvolley;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PasswordResettedAcknowledgement extends AppCompatActivity {
    TextView txt;
    Button bck_to_Log;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_resetted_acknowledgement);
        txt = (TextView)findViewById(R.id.txt_Pass_Reset_Ack);
        bck_to_Log = (Button)findViewById(R.id.btn_Pass_Reset_Ack_Bck_Log);

        bck_to_Log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasswordResettedAcknowledgement.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
