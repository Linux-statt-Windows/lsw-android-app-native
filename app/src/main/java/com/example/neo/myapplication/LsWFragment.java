package com.example.neo.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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

public class LsWFragment extends Fragment {

    private TextView mTxtDisplay;
    private ListView catList;
    private Boolean logged;
    private View parentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_layout, container, false);
        parentView = v;
        mTxtDisplay = (TextView) v.findViewById(R.id.txtDisplay);
        String iurl = "https://linux-statt-windows.org/api";

        getAPI(iurl);

        catList = (ListView) v.findViewById(R.id.categoryListView);
        return v;
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

        MySingleton.getInstance(this.getActivity().getParent().getApplicationContext()).addToRequestQueue(jsObjRequest);
    }

    private void editJSON(TextView txtDisplay, JSONObject jsonIn) {
        logged = false;
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
            ArrayAdapter<String> arrA = new ArrayAdapter<>(this.getActivity().getParent(), android.R.layout.simple_list_item_1);

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
