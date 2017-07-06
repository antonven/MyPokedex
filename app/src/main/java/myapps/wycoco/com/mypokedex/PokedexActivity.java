package myapps.wycoco.com.mypokedex;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class PokedexActivity extends AppCompatActivity {

    private String url = "http://pokeapi.co/api/v2/pokemon";
    private static String TAG = PokedexActivity.class.getSimpleName();

    RecyclerView recView;
    PokelistAdapter pokelistAdapter;
    RequestQueue requestQueue;
    ArrayList<Pokemonster> pokemons = new ArrayList<>();
    EndlessRecyclerViewScrollListener recyclerViewScrollListener;
    ProgressBar progBar;
//    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex);

        requestQueue = Volley.newRequestQueue(this);
        recView = (RecyclerView)findViewById(R.id.recView);
        progBar = (ProgressBar)findViewById(R.id.progBar);

        Toast.makeText(PokedexActivity.this, "Welcome to your pokedex!", Toast.LENGTH_LONG).show();

        JsonObjectRequest();

//        tv = (TextView)findViewById(R.id.textView);



    }


    private void JsonObjectRequest(){

        Cache cache = new DiskBasedCache(getApplicationContext().getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            final String nxtUrl = response.getString("next");
                            for(int i = 0; i< jsonArray.length(); i++){
                                JSONObject pokemon = jsonArray.getJSONObject(i);

                                String pokemonName = pokemon.getString("name").toUpperCase();
                                String pokeURL = pokemon.getString("url");

                                final Pokemonster pokemonster = new Pokemonster();
                                pokemonster.setPokeID(i);
                                pokemonster.setPokeName(pokemonName);

                                JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, pokeURL,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {

                                                try {
                                                    if(response != null){
                                                        progBar.setVisibility(View.GONE);
                                                    }
                                                    JSONObject jsonObject2 = response.getJSONObject("sprites");
                                                    String picUrl = jsonObject2.getString("front_default");

                                                    pokemonster.setPokeImage(picUrl);

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                    }
                                });
                                requestQueue.add(jsonObjectRequest2);
                                pokemons.add(pokemonster);

                                pokelistAdapter = new PokelistAdapter(getApplicationContext(), pokemons);
                                RecyclerView.LayoutManager lm = new LinearLayoutManager(getApplicationContext());
                                recView.setLayoutManager(lm);
                                recView.setAdapter(pokelistAdapter);

                                recyclerViewScrollListener = new EndlessRecyclerViewScrollListener((LinearLayoutManager) lm) {
                                    @Override
                                    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                                        loadNextDataFromApi(nxtUrl);
                                    }
                                };

                            }



                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PokedexActivity.this, "oops", Toast.LENGTH_SHORT).show();
                        Log.e("VOLLEY", "ERROR");
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    private void loadNextDataFromApi(String offset){
        JsonObjectRequest jsonObjectRequest3 = new JsonObjectRequest(Request.Method.GET, offset,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JsonObjectRequest();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

            }
        }

        );
        requestQueue.add(jsonObjectRequest3);
    }


}
