package com.example.todoapi;

/********************
 *                  *
 *   DELETE Method  *
 *      API         *
 *                  *
 ********************/

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<TODO> mdata;

    public RecyclerViewAdapter() {

    }

    public RecyclerViewAdapter(Context context, List<TODO> data) {
        Log.d("check:", "constructor");
        this.context = context;
        this.mdata = data;
    }


    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_layout, parent, false);
        Log.d("check:", "recyclerOnCreate");
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        TODO task = mdata.get(position);

        holder.Task.setText(task.getmTask());
        holder.Description.setText(task.getmDescription());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(context,"Done",Toast.LENGTH_SHORT).show();
                    Log.d("POsition",""+task.getmId());
                    delete(task.getmId(), task.getmTask(),task.getmDescription());
                    mdata.remove(holder.getAdapterPosition());

                    notifyItemRemoved(holder.getAdapterPosition());
                    notifyItemRangeChanged(holder.getAdapterPosition(),mdata.size());

                }else{

                }
            }
        });

        Log.d("check:", "recycler");

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }


public void delete(int position,String title , String description){

    JSONObject postData = new JSONObject();

    try {
        postData.put("id", position);
        postData.put("title", title);
        postData.put("description", description);
        postData.put("is_completed", true);
    } catch (JSONException e) {
        e.printStackTrace();
    }

    RequestQueue requestQueue = Volley.newRequestQueue(context);
    JsonObjectRequest jsonAbjectRequest = new JsonObjectRequest(Request.Method.DELETE,
            "https://my-todo-test-app.herokuapp.com/api/todo/"+position+"/", postData, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {



            Log.d("DELETE","Successful");
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("VolleyDebug","Error retrieving data");
        }
    });

    requestQueue.add(jsonAbjectRequest);
}





    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView Task,Description;
        public CheckBox checkBox;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            Log.d("check:", "viewHolder");
            Log.d("check:", "viewHolder");
            Task = itemView.findViewById(R.id.textView);
            Description = itemView.findViewById(R.id.textView2);
            checkBox = itemView.findViewById(R.id.checkBox);

        }


        @Override
        public void onClick(View v) {
            int position = this.getAdapterPosition();
            TODO task = mdata.get(position);

            Intent intent = new Intent(context, EditTask.class);
            intent.putExtra("position",task.getmId());
            intent.putExtra("currentTask",task.getmTask());
            intent.putExtra("currentDescription",task.getmDescription());
            context.startActivity(intent);


        }
    }


}
