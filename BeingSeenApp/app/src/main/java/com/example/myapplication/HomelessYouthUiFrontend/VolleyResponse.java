package com.example.myapplication.HomelessYouthUiFrontend;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface VolleyResponse {
    public void onVolleySuccess(JSONObject response);
}
