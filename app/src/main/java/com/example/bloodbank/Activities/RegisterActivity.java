package com.example.bloodbank.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bloodbank.R;
import com.example.bloodbank.Utils.Endpoints;
import com.example.bloodbank.Utils.VolleySingleton;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameEt,cityEt,stdEt,passwordEt,mobileEt;
    private Button submitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nameEt=findViewById(R.id.name);
        cityEt=findViewById(R.id.city);
        stdEt=findViewById(R.id.std);
        passwordEt=findViewById(R.id.password);
        mobileEt=findViewById(R.id.number);
        submitButton=findViewById(R.id.submitbutton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name,city,std,password,mobile;
                name=nameEt.getText().toString();
                city=cityEt.getText().toString();
                std=stdEt.getText().toString();
                password=passwordEt.getText().toString();
                mobile=mobileEt.getText().toString();
                if (isValid(name,city,std,password,mobile)){
                    register(name,city,std,password,mobile);
                }
            }
        });
    }

    private void message(){
        int permissionCheck= ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if (permissionCheck== PackageManager.PERMISSION_GRANTED){
            SmsManager smsManager=SmsManager.getDefault();
            smsManager.sendTextMessage("8967452310",null,"Thank you for registering with PrepTraversal , we'll also update you about our updates in future.",null,null);

        }
        else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},0);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 0:{
                if (grantResults.length>=0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    SmsManager smsManager=SmsManager.getDefault();
                    smsManager.sendTextMessage("8967452319",null,"Thank you for registering with PrepTraversal , we'll also update you about our updates in future.",null,null);

                }
                else{
                    Toast.makeText(this,"Message cannot be sent to your number",Toast.LENGTH_SHORT);
                }
            }
        }
    }

    private void register(final String name, final String city, final String std, final String password, final String number){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Endpoints.register_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Success")){
                    message();
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("city",city).apply();
                    Toast.makeText(RegisterActivity.this,response,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                    RegisterActivity.this.finish();
                }else{
                    Toast.makeText(RegisterActivity.this,response,Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this,"Something went wrong : (",Toast.LENGTH_SHORT).show();
                Log.d("VOLLEY",error.getMessage());
                
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("name",name);
                params.put("city",city);
                params.put("std",std);
                params.put("password",password);
                params.put("number",number);


                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private boolean isValid(String name,String city,String std,String password,String mobile){

        if (name.isEmpty()){
            showMessage("Name is empty");
            return false;
        }
        if (city.isEmpty()){
            showMessage("City is empty");
            return false;
        }
        if (std.isEmpty()){
            showMessage("Standard is empty");
            return false;
        }
        if (mobile.length()!=10){
            showMessage("Invalid mobile number , should be of 10 digits");
            return false;
        }
        return true;
    }

    private void showMessage(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
