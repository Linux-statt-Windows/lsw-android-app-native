package io.lsw.dev.linuxstattwindowsnative;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;

public class LsWFragment extends Fragment {

    private TextView mTxtDisplay;
    private ListView catList;
    private ArrayAdapter<String> categoryListAdapter;
    private ArrayList<String> categoryList;

    private Context parentContext;
    private String mainSite = "https://linux-statt-windows.org/api";
    private String[] categories = {};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(container == null)
            return null;

        View v = inflater.inflate(R.layout.fragment_layout, container, false);
        mTxtDisplay = (TextView) v.findViewById(R.id.loading_cats);

        if (getActivity() != null) {
            Log.w("[onCreateView]", "got parentContext");
            parentContext = getActivity();
        }

        // Creating catergoryListView
        catList = (ListView) v.findViewById(R.id.categoryListView);
        categoryList = new ArrayList<>();
        if (parentContext != null) {
            categoryListAdapter = new ArrayAdapter<>(parentContext, android.R.layout.simple_list_item_1, categoryList);
            catList.setAdapter(categoryListAdapter);
        } else {
            Log.w("[onCreateView]", "ParentContext == null");
        }

        // Starting Request-Queue for Data-input
        getAPI(mainSite, "categories", "name");

        return v;
    }

    public void getAPI(String url, final String loadObject, final String titleName) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        editJSON(mTxtDisplay, response, loadObject, titleName);
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                    }
                });

        if (parentContext != null) {
            MySingleton.getInstance(parentContext).addToRequestQueue(jsObjRequest);
        } else {
            Log.w("[getAPI]", "ParentContext == null");
        }
    }

    private void editJSON(TextView loadingTextView, JSONObject jsonIn, String loadObject, String titleName) {
        Boolean logged = false;
        try {
            logged = jsonIn.getBoolean("loggedIn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Integer loggedIn = logged ? R.string.is_logged_in : R.string.is_logged_out;
        Log.d("[LOGGEDIN]", String.valueOf(logged));

        try {
            JSONArray jsonCatList = jsonIn.getJSONArray(loadObject);
            categoryList.clear();
            for (int i = 0; i < jsonCatList.length(); i++) {
                categoryList.add(
                        ((JSONObject) jsonCatList.get(i)).getString(titleName).replace("&amp;", "&")
                );
            }

            // Removing "loading ..."
            loadingTextView.setVisibility(View.INVISIBLE);
            loadingTextView.setHeight(0);

            // Adding the loaded list
            catList.setVisibility(View.VISIBLE);

            categoryListAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void loadCategory(int position){
        Log.d("[CATEGORY]", String.valueOf(position));
        switch (position){
            case 0:
                getAPI(mainSite, "categories", "name");
                break;
            case 1:
                getAPI(mainSite + "/unread", "topics", "title");
                break;
            case 2:
                getAPI(mainSite + "/recent", "topics", "title");
                break;
            case 3:
                getAPI(mainSite + "/tags", "tags", "value");
                break;
            case 4:
                getAPI(mainSite + "/popular", "topics", "title");
                break;
            case 5:
                getAPI(mainSite + "/users", "users", "username");
                break;
            case 6:
                getAPI(mainSite + "/groups", "groups", "name");
                break;
            case 7:
                getAPI(mainSite + "/notifications", "notifications", "notifications");
                break;
        }
    }
}
