package myapps.wycoco.com.mypokedex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PokemonInformationActivity extends AppCompatActivity {

//    private static final String MY_SOCKET_TIMEOUT_MS = ;
    private String url = "http://pokeapi.co/api/v2/pokemon";
    private static String TAG = PokedexActivity.class.getSimpleName();

    RecyclerView recView2;
    PokemonAdapter pokemonAdapter;
    RequestQueue requestQueue;
    ArrayList<Pokemonster> pokemons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_information);

        requestQueue = Volley.newRequestQueue(this);
        recView2 = (RecyclerView)findViewById(R.id.recView2);
        Intent intent = getIntent();
        String pokeImage = intent.getStringExtra("pokeImage");
        String pokeName = intent.getStringExtra("pokeName");
        JsonObjectRequest();
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
                            for(int i = 0; i< jsonArray.length(); i++){
                                JSONObject pokemon = jsonArray.getJSONObject(i);
                                String pokeURL = pokemon.getString("url");

                                final Pokemonster pokemonster = new Pokemonster();
                                pokemonster.setPokeName(pokemon.getString("name").toUpperCase());

                                JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, pokeURL,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {

                                                try {

                                                    Log.e("kirs", "naabot");
                                                    JSONObject jsonObject2 = response.getJSONObject("sprites");
                                                    JSONArray jsonArray1 = response.getJSONArray("types");
//                                                    JSONObject jsonObject5 = response.getJSONObject("Height");
//                                                    String pokeId = jsonObject3.toString();
                                                    String picUrl = jsonObject2.getString("front_default");
                                                    for(int o = 0; o < jsonArray1.length(); o++){
                                                        JSONObject pokemonmon = jsonArray1.getJSONObject(o);

                                                            JSONObject jsonObject4 = pokemonmon.getJSONObject("type");
                                                            String pokeType = jsonObject4.getString("name").toUpperCase();
                                                            pokemonster.setPokeType(pokeType);
                                                        Log.e("Me", "naabot nasad diri");



                                                    }
//                                                    pokemonster.setPokeID(pokeId);
                                                    pokemonster.setPokeHeight(response.getString("height"));
                                                    pokemonster.setPokeWeight(response.getString("weight"));
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

                                pokemonAdapter = new PokemonAdapter(getApplicationContext(), pokemons);
                                RecyclerView.LayoutManager lm = new LinearLayoutManager(getApplicationContext());
                                recView2.setLayoutManager(lm);
                                recView2.setAdapter(pokemonAdapter);
                            }



                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PokemonInformationActivity.this, "oops", Toast.LENGTH_SHORT).show();
                        Log.e("VOLLEY", "ERROR");
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
    }


}
