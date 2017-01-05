package com.seals.shubham.placementportalvolley;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPassword extends AppCompatActivity {
    private TextView txtView;
    private final String url = "http://shubh.noads.biz/PlacementPortalVolley/CheckLoginData.php";
    RequestQueue mRequestQueue;
    private EditText frgtReg_Id;
    private String phone;
    private boolean flag = false;
    private Button btnResend;
    private String msg;

    private String reg_Id;
    int num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        txtView = (TextView)findViewById(R.id.frgt_Msg);
        frgtReg_Id = (EditText)findViewById(R.id.frgt_Reg_Id);

        Button btnSub = (Button)findViewById(R.id.frgt_btn_Sub);
        Button bckLog = (Button)findViewById(R.id.frgt_back_login);

        btnResend = (Button)findViewById(R.id.frgt_btn_resend);

        mRequestQueue = Volley.newRequestQueue(this);

        bckLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPassword.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resend();
            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag){
                    String send_val = ""+num;
                    String get_val = frgtReg_Id.getText().toString();
                    if(send_val.equals(get_val)){
                        Intent intent = new Intent(ForgotPassword.this,PasswordReset.class);
                        intent.putExtra("Registration_Id",reg_Id);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Value written by you donot match with Send Otp",Toast.LENGTH_LONG).show();
                        btnResend.setVisibility(View.VISIBLE);
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"finallyyy",Toast.LENGTH_LONG).show();
                    reg_Id = frgtReg_Id.getText().toString();
                    if(reg_Id.equals("")){
                        Toast.makeText(getApplicationContext(),"Filling the Box is mandatory",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Even Entered",Toast.LENGTH_LONG).show();
                        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                boolean object_found = false;
                                Toast.makeText(getApplicationContext(),"size "+response.length(),Toast.LENGTH_LONG).show();
                                for(int i=0;i<response.length();i++){
                                    try {
                                        JSONObject jsonObject = response.getJSONObject(i);
                                        String Regis_Id = jsonObject.getString("Registration_Id");
                                        if(reg_Id.equals(Regis_Id)){
                                            object_found = true;
                                            phone = jsonObject.getString("Mobile");
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                if(!object_found){
                                    Toast.makeText(getApplicationContext(),"Unable to find User with Registration_Id "+reg_Id,Toast.LENGTH_LONG).show();
                                }
                                else{
                                    String mobi = "******"+phone.substring(6);
                                    flag = true;
                                    Toast.makeText(getApplicationContext(),"Mobile Number "+mobi,Toast.LENGTH_LONG).show();
                                    msg = "A message with Otp has been Sent to your Mobile No "+mobi+" .Enter the Otp Code below";
                                    txtView.setText(msg);
                                    frgtReg_Id.setText("");
                                    frgtReg_Id.setHint("Enter Otp Code");
                                    SendPasswordResetOtp send_sms = new SendPasswordResetOtp();
                                    send_sms.set_Mobile_No(phone);
                                    send_sms.sendMessage();
                                    num = send_sms.getOtp();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                            }
                        });
                        Toast.makeText(getApplicationContext(),"Came alonggg",Toast.LENGTH_LONG).show();
                        mRequestQueue.add(jsonArrayRequest);
                    }
                }
            }
        });
    }

    public void resend(){
        txtView.setText(msg);
        frgtReg_Id.setText("");
        frgtReg_Id.setHint("Enter Otp Code");
        SendPasswordResetOtp send_sms = new SendPasswordResetOtp();
        send_sms.set_Mobile_No(phone);
        send_sms.sendMessage();
        num = send_sms.getOtp();
    }
}
