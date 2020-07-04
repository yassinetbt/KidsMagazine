package com.yassinetaboubi.android.magazines;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yassinetaboubi.android.magazines.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LeaderActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List1Adapter list1Adapter;
    List<OurData> list;
    private String URL_DATA="http://41.226.11.252:11885/rank/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader);
        recyclerView=findViewById(R.id.recyclerview);
        list=new ArrayList<>();
        list1Adapter=new List1Adapter(list,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(list1Adapter);
        loadRecyclerViewData();
    }

    private void loadRecyclerViewData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest jsonArrayRequest = new StringRequest(Request.Method.GET,URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray Jarray = jsonObject.getJSONArray("score2");
                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject jsonObject1=Jarray.getJSONObject(i);
                        OurData ourdata = new OurData(jsonObject1.getString("username"),jsonObject1.getString("score1"));

                        list.add(ourdata);
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
                list1Adapter=new List1Adapter(list,getApplicationContext());
                recyclerView.setAdapter(list1Adapter);
                list1Adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}
