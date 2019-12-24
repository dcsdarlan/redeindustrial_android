package br.com.redeindustrial;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.collection.LruCache;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import br.com.redeindustrial.adapters.BeersRecyclerViewAdapter;
import br.com.redeindustrial.json.BeerResponse;

public class MainActivity extends RICompatActivity {

    protected Toolbar toolbarTop;
    protected RecyclerView recyclerView;
    protected RelativeLayout loadRelativeLayout, errorRelativeLayout;
    protected RequestQueue requestQueue;
    protected ImageLoader imageLoader;
    protected ArrayList<BeerResponse> beers;
    protected BeersRecyclerViewAdapter beersRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(MainActivity.this);
        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<>(50);
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }
        });

        toolbarTop = findViewById(R.id.topToolbar);
        setSupportActionBar(toolbarTop);
        loadRelativeLayout = findViewById(R.id.loadRelativeLayout);
        errorRelativeLayout = findViewById(R.id.errorRelativeLayout);
        recyclerView = findViewById(R.id.recyclerView);
        this.loadAll();
    }
    private void loadAll() {
        errorRelativeLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        loadRelativeLayout.setVisibility(View.VISIBLE);

        StringRequest listRequest = new StringRequest(Request.Method.GET,
                getString(R.string.url_webservice),
                response -> {
                    Log.i(MainActivity.class.getSimpleName(), "Listando...");
                    GsonBuilder jsonBuilder = new GsonBuilder();
                    Gson gson = jsonBuilder.create();
                    beers = (ArrayList<BeerResponse>) gson.fromJson(response, new TypeToken<List<BeerResponse>>(){}.getType());

                    LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setHasFixedSize(true);
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                            layoutManager.getOrientation());
                    recyclerView.addItemDecoration(dividerItemDecoration);
                    beersRecyclerViewAdapter = new BeersRecyclerViewAdapter(MainActivity.this, imageLoader, beers);
                    recyclerView.setAdapter(beersRecyclerViewAdapter);

                    errorRelativeLayout.setVisibility(View.GONE);
                    loadRelativeLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                },
                error -> {
                    error.printStackTrace();
                    recyclerView.setVisibility(View.GONE);
                    loadRelativeLayout.setVisibility(View.GONE);
                    errorRelativeLayout.setVisibility(View.VISIBLE);
                });
        listRequest.addMarker("list");
        requestQueue.cancelAll("list");
        requestQueue.add(listRequest);
    }

    private void reloadAll() {
        errorRelativeLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        loadRelativeLayout.setVisibility(View.VISIBLE);

        StringRequest listRequest = new StringRequest(Request.Method.GET,
                getString(R.string.url_webservice),
                response -> {
                    Log.i(MainActivity.class.getSimpleName(), "Listando...");
                    GsonBuilder jsonBuilder = new GsonBuilder();
                    Gson gson = jsonBuilder.create();

                    beers.clear();
                    beers.addAll((ArrayList<BeerResponse>) gson.fromJson(response, new TypeToken<List<BeerResponse>>(){}.getType()));
                    beersRecyclerViewAdapter.notifyDataSetChanged();

                    errorRelativeLayout.setVisibility(View.GONE);
                    loadRelativeLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                },
                error -> {
                    error.printStackTrace();
                    recyclerView.setVisibility(View.GONE);
                    loadRelativeLayout.setVisibility(View.GONE);
                    errorRelativeLayout.setVisibility(View.VISIBLE);
                });
        listRequest.addMarker("list");
        requestQueue.cancelAll("list");
        requestQueue.add(listRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                errorRelativeLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                loadRelativeLayout.setVisibility(View.VISIBLE);

                StringRequest listRequest = new StringRequest(Request.Method.GET,
                        getString(R.string.url_webservice),
                        response -> {
                            Log.i(MainActivity.class.getSimpleName(), "Listando...");
                            GsonBuilder jsonBuilder = new GsonBuilder();
                            Gson gson = jsonBuilder.create();
                            ArrayList<BeerResponse> resultBeers = (ArrayList<BeerResponse>) gson.fromJson(response, new TypeToken<List<BeerResponse>>(){}.getType());
                            beers.clear();
                            for (BeerResponse beerResponse : resultBeers) {
                                if(beerResponse.name.toLowerCase().contains(newText)) beers.add(beerResponse);
                            }
                            beersRecyclerViewAdapter.notifyDataSetChanged();
                            if(beers.size() > 0) {
                                errorRelativeLayout.setVisibility(View.GONE);
                                loadRelativeLayout.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                            } else {
                                loadRelativeLayout.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.GONE);
                                errorRelativeLayout.setVisibility(View.VISIBLE);
                            }
                        },
                        error -> {
                            error.printStackTrace();
                            recyclerView.setVisibility(View.GONE);
                            loadRelativeLayout.setVisibility(View.GONE);
                            errorRelativeLayout.setVisibility(View.VISIBLE);
                        });
                listRequest.addMarker("list");
                requestQueue.add(listRequest);
                return false;
            }
        });
        searchView.setOnCloseListener(() -> {
            reloadAll();
            return false;
        });

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public void onBackPressed() {
        requestQueue.cancelAll("list");
        super.onBackPressed();
    }
}
