package com.seals.shubham.placementportalvolley;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText reg_Id, pass;
    Button btnLog,btnCreAcc,btnExit;
    TextView forgot_Pass;
    CheckBox cb;
    public final String url = "http://shubh.noads.biz/PlacementPortalVolley/CheckLoginData.php";
    RequestQueue mRequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        reg_Id = (EditText)findViewById(R.id.LogRegId);
        pass = (EditText)findViewById(R.id.LogPass);
        btnLog = (Button)findViewById(R.id.btnLog);
        mRequestQueue = Volley.newRequestQueue(this);
        btnExit = (Button)findViewById(R.id.btnExit);
        btnCreAcc = (Button)findViewById(R.id.CreAcc);
        cb = (CheckBox)findViewById(R.id.showPass);
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
        btnCreAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(intent);
            }
        });
        forgot_Pass = (TextView)findViewById(R.id.frgtPass);
        forgot_Pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ForgotPassword.class);
                startActivity(intent);
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder  ab = new AlertDialog.Builder(LoginActivity.this);
                ab.setMessage(getResources().getString(R.string.Exit_Prompt));
                ab.setPositiveButton(getResources().getString(R.string.Positive_Response), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ab.setNegativeButton(getResources().getString(R.string.Negative_Response), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                });
            }
        });
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
                pd.setMessage("Logging In...");
                pd.setCancelable(false);
                pd.show();
                final String Reg_Id = reg_Id.getText().toString();
                final String passW = pass.getText().toString();
                if(Reg_Id.equals("") || passW.equals("")){
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.InComplete_Form),Toast.LENGTH_LONG)
                            .show();
                    pd.dismiss();
                }
                else{
                    Toast.makeText(LoginActivity.this,"Reg_ID"+Reg_Id+" Password "+passW,Toast.LENGTH_LONG).show();
                    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            for(int i=0;i<response.length();i++){
                                try{
                                    JSONObject object = response.getJSONObject(i);
                                    String Regis_Id = object.getString("Registration_Id");
                                    String passwo = object.getString("Password");
                                    if(Reg_Id.equals(Regis_Id) && passW.equals(passwo)){
                                        Intent intent = new Intent(LoginActivity.this,AdminOrNormal.class);
                                        startActivity(intent);
                                    }
                                }catch (Exception e){
                                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                        }
                    });
                    mRequestQueue.add(jsonArrayRequest);
                }
            }
        });
    }
}
