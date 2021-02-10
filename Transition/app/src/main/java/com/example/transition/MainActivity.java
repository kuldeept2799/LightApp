package com.example.transition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ImageView ImageView;

    boolean turnedOn = false;

    public static String switchvalue = "null";

    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        keeplistening();
        doTheAutoRefresh();


        ImageView = (ImageView) findViewById(R.id.imageView);


    }

    private void doTheAutoRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                keeplistening();
                doTheAutoRefresh();
            }
        }, 9000);
    }


    public void keeplistening() {
        String url = "https://testserver202.000webhostapp.com/listen.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("result");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject request = jsonArray.getJSONObject(i);

                                String value = request.getString("value");
                                switchvalue = value;
                            }
                            Toast.makeText(MainActivity.this, switchvalue, Toast.LENGTH_LONG).show();


                            if(switchvalue.equals("1")) {

                                ImageView.setImageResource(R.drawable.trans_on);
                                //((TransitionDrawable) ImageView.getDrawable()).startTransition(2500);
                                //turnedOn = true;
                            } else if(switchvalue.equals("0")) {
                                ImageView.setImageResource(R.drawable.trans_off);
                                //((TransitionDrawable) ImageView.getDrawable()).startTransition(2500);
                                //turnedOn = false;
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}