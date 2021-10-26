package com.example.todoapi;

/********************
 *                  *
 *   PATCH Method   *
 *      API         *
 *                  *
 ********************/

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class EditTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        Button btn = findViewById(R.id.button);
        EditText ed1 = findViewById(R.id.editTextTask);
        EditText ed2 = findViewById(R.id.editTextDescription);

        Intent intent = getIntent();
        String currentTask = intent.getStringExtra("currentTask");
        String currentDescription = intent.getStringExtra("currentDescription");
        int position = intent.getIntExtra("position",0);

        Log.d("INTENT",currentTask);
        Log.d("INTENT",currentDescription);
        Log.d("INTENT",position+" ");

        ed1.setText(currentTask);
        ed2.setText(currentDescription);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject postData = new JSONObject();

                try {
                    postData.put("id", position);
                    postData.put("title", ed1.getText().toString());
                    postData.put("description", ed2.getText().toString());
                    postData.put("is_completed", false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Log.d("PATCH",postData.toString());
                RequestQueue requestQueue = Volley.newRequestQueue(EditTask.this);
                JsonObjectRequest jsonAbjectRequest = new JsonObjectRequest(Request.Method.PATCH,
                        "https://my-todo-test-app.herokuapp.com/api/todo/"+position+"/", postData, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(EditTask.this, "Successful Changed", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditTask.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                        Log.d("PATCH","Successful");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VolleyDebug","Error retrieving data");
                    }
                });

                requestQueue.add(jsonAbjectRequest);
            }
        });

    }
}