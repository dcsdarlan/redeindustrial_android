package br.com.redeindustrial;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.collection.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import br.com.redeindustrial.json.BeerResponse;

public class DetailActivity extends RICompatActivity {

    protected Toolbar toolbarTop;
    protected RequestQueue requestQueue;
    protected ImageLoader imageLoader;
    protected NetworkImageView networkImageView;
    protected TextView nameTextView, taglineTextView, descriptionTextView, firstBrewedTextView, abvTextView, ibuTextView;
    protected BeerResponse item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        requestQueue = Volley.newRequestQueue(DetailActivity.this);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        networkImageView = findViewById(R.id.networkImageView);
        nameTextView = findViewById(R.id.nameTextView);
        taglineTextView = findViewById(R.id.taglineTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        firstBrewedTextView = findViewById(R.id.firstBrewedTextView);
        abvTextView = findViewById(R.id.abvTextView);
        ibuTextView = findViewById(R.id.ibuTextView);

        item = (BeerResponse) getIntent().getExtras().getSerializable("ITEM");
        networkImageView.setImageUrl(item.image_url, imageLoader);
        nameTextView.setText(item.name);
        taglineTextView.setText(item.tagline);
        descriptionTextView.setText(item.description);
        firstBrewedTextView.setText(item.first_brewed);
        abvTextView.setText(String.valueOf(item.abv));
        ibuTextView.setText(String.valueOf(item.ibu));
    }

    @Override
    public boolean onSupportNavigateUp(){
        this.onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
