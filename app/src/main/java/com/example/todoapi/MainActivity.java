package com.example.todoapi;

/********************
 *                  *
 *   GET Method     *
 *      API         *
 *                  *
 ********************/

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<TODO> todoArrayList ;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoArrayList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddTask.class);
                startActivity(intent);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonAbjectRequest = new JsonArrayRequest(Request.Method.GET,
                "https://my-todo-test-app.herokuapp.com/api/todo", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                try {
                    for(int i = 0 ; i<response.length();i++){
//                        Log.d("VolleyDebug","Array element call- "+" "+i++);
                        JSONObject obj = response.getJSONObject(i);
//                        Log.d("VolleyDebug","Object call- "+" "+i++);

                            todoArrayList.add(new TODO(obj.getString("title"),obj.getString("description"),obj.getBoolean("is_completed"),obj.getInt("id") ));

                    }
                } catch (JSONException e) {
                    Log.d("VolleyDebug","Error Printing data");
                    e.printStackTrace();
                }

                recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this,todoArrayList);
                recyclerView.setAdapter(recyclerViewAdapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VolleyDebug","Error retrieving data");
            }
        });

        requestQueue.add(jsonAbjectRequest);

    }
}