package com.example.jsontest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView mTextViewResult;
    private RequestQueue mQueue;
    private int start = 0;
    private int end = 32;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewResult = findViewById(R.id.text_view_result);
        Button buttonParse = findViewById(R.id.button_parse);
        ImageView imageView = findViewById(R.id.image_view);

        String url = "https://image.tmdb.org/t/p/w185//4U7hpTK0XTQBKT5X60bKmJd05ha.jpg";

        Picasso.with(this).load(url).into(imageView);


        mQueue = Volley.newRequestQueue(this);

        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        });



    }

    private void showImage(String path) {

        ImageView imageView = findViewById(R.id.image_view);

        String imageUrl = "https://image.tmdb.org/t/p/w185/" + path;

        Picasso.with(this).load(imageUrl).into(imageView);
    }


    private void jsonParse() {



        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=3e71659358fcc0e15a078ffbfd22b2fc&language=en-US&page=1";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            String allPosters = "";

                            for (int i = 0; i < jsonArray.length(); i++) {

                                ImageView imageView = findViewById(R.id.image_view);

                                JSONObject movie = jsonArray.getJSONObject(i);

                                Double popularity = movie.getDouble("popularity");
                                int vote_count = movie.getInt("vote_count");
                                Boolean video = movie.getBoolean("video");
                                String poster_path = movie.getString("poster_path");
                                int id = movie.getInt("id");
                                Boolean adult = movie.getBoolean("adult");
                                String backdrop_path = movie.getString("backdrop_path");
                                String original_language = movie.getString("original_language");
                                String original_title = movie.getString("original_title");
                                String title = movie.getString("title");
                                Double vote_average = movie.getDouble("vote_average");
                                String overview = movie.getString("overview");
                                String release_date = movie.getString("release_date");

                                allPosters += poster_path;
                                mTextViewResult.append(title + "\n");
                            }

                            String firstMovie = allPosters.substring(start,end);
                            start += 32;
                            end += 32;


                            showImage(firstMovie);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
}
