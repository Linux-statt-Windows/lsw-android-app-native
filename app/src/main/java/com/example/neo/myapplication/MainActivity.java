package com.example.neo.myapplication;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends ActionBarActivity {

    private TextView mTxtDisplay;
    private ListView catList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTxtDisplay = (TextView) findViewById(R.id.txtDisplay);
        String iurl = "https://linux-statt-windows.org/api";

        getAPI(iurl);

        catList = (ListView) findViewById(R.id.categoryListView);
    }

    public void getAPI(String url) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        editJSON(mTxtDisplay, response);
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
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

    private void editJSON(TextView txtDisplay, JSONObject jsonIn) {
        Boolean logged = false;
        try {
            logged = jsonIn.getBoolean("loggedIn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String loggedIn = logged ? "eingeloggt" : "ausgeloggt";
        txtDisplay.setText(loggedIn);
        Log.d("[JSONELEMENT]", String.valueOf(logged));

        try {
            JSONArray jsonCatList = jsonIn.getJSONArray("categories");

            ArrayAdapter arrA = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

            for (int i = 0; i < jsonCatList.length(); i++) {
                arrA.add(
                        ((JSONObject) jsonCatList.get(i)).getString("name").replace("&amp;", "&")
                );
            }

            catList.setAdapter(arrA);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
