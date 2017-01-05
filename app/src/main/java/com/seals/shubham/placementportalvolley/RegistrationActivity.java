package com.seals.shubham.placementportalvolley;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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

public class RegistrationActivity extends AppCompatActivity {
    RequestQueue mRequestQueue;
    EditText name,reg_Id,pass,email,mobile;
    AutoCompleteTextView branch;
    public final String[] branch_opt = {"CSE","ECE","ME","CE","EEE","PIE","MME","MCA","MSC"};
    Button btnReg,btnLog;
    public final String base_url = "http://shubh.noads.biz/PlacementPortalVolley/InsertingData.php";
    String user_name,Reg_Id,Pass,Email,Mob,Branch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mRequestQueue = Volley.newRequestQueue(this);
        branch = (AutoCompleteTextView)findViewById(R.id.RegBranch);
        name = (EditText)findViewById(R.id.RegName);
        reg_Id = (EditText)findViewById(R.id.RegRegId);
        pass = (EditText)findViewById(R.id.RegPass);
        email = (EditText)findViewById(R.id.RegEmail);
        mobile = (EditText)findViewById(R.id.RegMob);
        btnReg = (Button)findViewById(R.id.BtnReg);
        btnLog = (Button)findViewById(R.id.BtnLoginPage);
        ArrayAdapter<String> ad = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,branch_opt);
        branch.setAdapter(ad);
        branch.setThreshold(1);
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog pd = new ProgressDialog(RegistrationActivity.this);
                pd.setMessage("Registering");
                pd.show();
                pd.setCancelable(false);
                user_name = name.getText().toString();
                Reg_Id = reg_Id.getText().toString();
                Pass = pass.getText().toString();
                Email = email.getText().toString();
                Mob = mobile.getText().toString();
                Branch = branch.getText().toString();
                if(user_name.equals("") || Reg_Id.equals("") || Pass.equals("") || Email.equals("") || Mob.equals("")
                        || Branch.equals("")){
                    Toast.makeText(getApplicationContext(),"All fields are mandatory ",Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }
                else if(Mob.length()!=10){
                    Toast.makeText(getApplicationContext(),"Enter Mobile number of Appropriate Length",Toast.LENGTH_LONG).show();
                }
                else{
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, base_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                            pd.dismiss();
                            name.setText(null);
                            reg_Id.setText(null);
                            pass.setText(null);
                            email.setText(null);
                            mobile.setText(null);
                            branch.setText(null);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> param = new HashMap<>();
                            param.put("Username",user_name);
                            param.put("Registration_Id",Reg_Id);
                            param.put("Password",Pass);
                            param.put("Email",Email);
                            param.put("Mobile",Mob);
                            param.put("Branch",Branch);
                            return param;
                        }
                    };
                    mRequestQueue.add(stringRequest);
                }
            }
        });
    }
}
