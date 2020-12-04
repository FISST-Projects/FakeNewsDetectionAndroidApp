package com.example.fakenewsdetection.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fakenewsdetection.R;
import com.example.fakenewsdetection.ResultActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class SearchFragment extends Fragment {

    private  String API_URL = "ENTER_API_URL_HERE";
    private Intent  intent;

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_search, container, false);
        return root;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        final Button button = view.findViewById(R.id.check);
        final EditText editText = view.findViewById(R.id.editTextNewsInput);
        final ProgressBar spinner = (ProgressBar)view.findViewById(R.id.progressBar2);
        spinner.setVisibility(View.GONE);

        final RequestQueue queue = Volley.newRequestQueue(getContext());
        intent = new Intent(getContext(),ResultActivity.class);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, API_URL,null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        if(!response.isNull("title")){
                            try {
                                intent.putExtra("title",response.getString("title"));
                                Log.d("title",response.getString("title"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if(!response.isNull("score")){
                            try {
                                intent.putExtra("score",response.getString("score"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if(!response.isNull("link")){
                            try {
                                intent.putExtra("link",response.getString("link"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if(!response.isNull("label")){
                            try {
                                Log.d("label",response.getString("label"));
                                intent.putExtra("label",response.getString("label"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        editText.setVisibility(View.VISIBLE);
                        button.setVisibility(View.VISIBLE);
                        spinner.setVisibility(View.GONE);
                        startActivity(intent);




                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                }){
            @Override
            public String getBodyContentType() {
                return super.getBodyContentType();
            }

            @Override
            public byte[] getBody() {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("text",editText.getText().toString());
                    return jsonObject.toString().getBytes("utf-8");
                } catch (JSONException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                return super.getBody();
            }
        };


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = editText.getText().toString();
                if(inputText.isEmpty()){
                    editText.setError("Please enter News");
                    editText.requestFocus();
                }
                else{
                    editText.setVisibility(View.GONE);
                    button.setVisibility(View.GONE);
                    spinner.setVisibility(View.VISIBLE);
                    intent.putExtra("news",inputText);
                    queue.add(jsonObjectRequest);
                }



            }
        });
    }

}
