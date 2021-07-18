package com.example.havadurumu;

import android.content.Context;
import android.os.Bundle;

import androidx.core.app.ComponentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class ApiRequest {

    public interface  VolleyResponseListener{

        void onError(String Error);

        void onSuccess(JSONObject jsonObject);
    }

    public interface  VolleyResponseListener1{

        void onError(String Error);

        void onSuccess(JSONArray jsonArray);
    }

    public void getData(Context context,String url,VolleyResponseListener volleyResponseListener){
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject postData = new JSONObject();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,postData,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                volleyResponseListener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseListener.onError(error.getLocalizedMessage());
            }
        });


        queue.add(jsonObjectRequest);
    }
    public void getData1(Context context,String url,VolleyResponseListener1 volleyResponseListener1){
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONArray postData = new JSONArray();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,url,postData,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                volleyResponseListener1.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseListener1.onError(error.getLocalizedMessage());
            }
        });


        queue.add(jsonArrayRequest);
    }
}