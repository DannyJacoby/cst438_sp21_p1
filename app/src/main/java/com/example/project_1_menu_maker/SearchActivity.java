package com.example.project_1_menu_maker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.project_1_menu_maker.adapters.RecipeAdapter;
import com.example.project_1_menu_maker.models.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class SearchActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/search.php?s=";
    public static final String TAG = "SearchActivity";

    List<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        RecyclerView rmRecipes = findViewById(R.id.rvRecipes);
        recipes = new ArrayList<>();

        final RecipeAdapter recipeAdapter = new RecipeAdapter(this, recipes);

        rmRecipes.setAdapter(recipeAdapter);

        rmRecipes.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(BASE_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("meals");
                    Log.i(TAG, "Result: " + results.toString());
                    recipes.addAll(Recipe.fromJsonArray(results));
                    recipeAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Recipes: "+ recipes.size());
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception" + e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });

    }
}