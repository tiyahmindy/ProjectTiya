package com.example.todolist;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<toDoList> todo;
    toDoList_Adapter tdAdapter;
    RecyclerView rvToDo;
    Context ctxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvToDo = (RecyclerView) findViewById(R.id.rvToDoList);
        rvToDo.setLayoutManager(new LinearLayoutManager(this));

        todo = new ArrayList<>();
        tdAdapter = new toDoList_Adapter(this,todo);

        rvToDo.setHasFixedSize(true);
        rvToDo.setAdapter(tdAdapter);

        ctxt = this;

        ambilDataAPI();
    }
    public void ambilDataAPI () {
        Handler handler = new Handler(Looper.getMainLooper());
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "https://mgm.ub.ac.id/todo.php";
                RequestQueue rqQueue = Volley.newRequestQueue(ctxt);

                JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                for (int i = 0; i < response.length(); i++) {
                                    try {
//                                        JSONArray jsonArray = response.getJSONArray("data");

                                        JSONObject jsonObject = response.getJSONObject(i);
                                        String id = jsonObject.getString("id");
                                        String what = jsonObject.getString("what");
                                        String time = jsonObject.getString("time");

                                        toDoList todoL = new toDoList(id, what, time);
                                        todo.add(todoL);

                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                tdAdapter = new toDoList_Adapter(ctxt, todo);
                                                rvToDo.setLayoutManager(new LinearLayoutManager(ctxt));
                                                rvToDo.setAdapter(tdAdapter);
                                                tdAdapter.notifyDataSetChanged();
                                            }
                                        });
                                    }catch(JSONException e){
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        });
                rqQueue.add(req);
            }
        });
        thread.start();
    }
}
