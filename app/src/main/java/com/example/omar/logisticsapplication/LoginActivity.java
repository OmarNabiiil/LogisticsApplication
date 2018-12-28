package com.example.omar.logisticsapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText name;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name= findViewById(R.id.txt_username);
        password= findViewById(R.id.txt_password);

        Intent i=new Intent(LoginActivity.this,HomeActivity.class);
        startActivity(i);
        finish();
    }

    public void Login(View view) {
        loginUser(name.getText().toString().trim(), password.getText().toString().trim());
    }

    public void loginUser(final String username, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        String url = Config.TEST_LOGIN_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("testres",""+response);
                try {
                    JSONObject user=new JSONObject(response);
                    Intent i=new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(i);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url

                Map<String, String> params = new HashMap<>();
                params.put("username", ""+username);
                params.put("password", ""+password);

                return params;
            }

        };

        // Adding request to request queue
        ApplicationActivity.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
