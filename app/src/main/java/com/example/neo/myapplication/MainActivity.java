package com.example.neo.myapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.transform.Result;


public class MainActivity extends ActionBarActivity {

    private JSONObject input;
    public JSONObject retAPI;
    private TextView mTxtDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ImageView mImageView;
        mTxtDisplay = (TextView) findViewById(R.id.txtDisplay);
        String iurl = "https://linux-statt-windows.org/api";

        input = getAPI(iurl);
        //editJSON(mTxtDisplay, input);
    }

    public JSONObject getAPI(String url){
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        retAPI = response;

                        Boolean logged = false;
                        try {
                            logged = retAPI.getBoolean("loggedIn");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String loggedIn = logged ? "eingeloggt" : "ausgeloggt";
                        mTxtDisplay.setText(loggedIn);
                        Log.d("[JSONELEMENT]", String.valueOf(logged));
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
        return retAPI;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void editJSON(TextView txtDisplay, JSONObject jsonIn){
        Boolean logged = false;
        try {
            logged = jsonIn.getBoolean("loggedIn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String loggedIn = logged ? "eingeloggt" : "ausgeloggt";
        txtDisplay.setText(loggedIn);
        Log.d("[JSONELEMENT]", String.valueOf(logged));
    }
}
