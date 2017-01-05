package com.seals.shubham.placementportalvolley;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class PasswordReset extends AppCompatActivity {
    EditText pass;
    EditText cnfPass;

    Button chngPass;

    CheckBox cb;

    private String reg_Id;
    private final String url = "http://shubh.noads.biz/PlacementPortalVolley/ChangePassword.php";
    RequestQueue mQueue;
    private String passW,cnfPassW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        Bundle extras = getIntent().getExtras();
        try{
            reg_Id = extras.getString("Registration_Id");
        }
        catch (NullPointerException ne){
            ne.printStackTrace();
        }

        mQueue = Volley.newRequestQueue(this);
        pass = (EditText)findViewById(R.id.PassResetFir);
        cnfPass = (EditText) findViewById(R.id.PassResetCnfPass);
        cb = (CheckBox) findViewById(R.id.PassResetChckBox);
        chngPass = (Button)findViewById(R.id.btn_Pass_Reset_Chnge);

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    pass.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
                }
                else{
                    pass.setInputType(129);
                }
            }
        });

        chngPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passW = pass.getText().toString();
                cnfPassW = cnfPass.getText().toString();
                if(passW.equals(cnfPassW)){
                    doThis();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Passwords Don't Match",Toast.LENGTH_LONG).show();
                    cnfPass.setText(null);
                }
            }
        });
    }

    public void doThis(){
        StringRequest request = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Password Updated Successfully")){
                    Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(PasswordReset.this,PasswordResettedAcknowledgement.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("Registration_Id",reg_Id);
                map.put("Password",passW);
                return map;
            }
        };
        mQueue.add(request);
    }
}
