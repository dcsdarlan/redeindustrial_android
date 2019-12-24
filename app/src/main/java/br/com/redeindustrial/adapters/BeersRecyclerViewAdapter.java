package br.com.redeindustrial.adapters;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import br.com.redeindustrial.DetailActivity;
import br.com.redeindustrial.R;
import br.com.redeindustrial.json.BeerResponse;


public class BeersRecyclerViewAdapter extends RecyclerView.Adapter<BeersRecyclerViewAdapter.ViewHolder> {

    protected List<BeerResponse> itens;
    protected AppCompatActivity context;
    protected ImageLoader imageLoader;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public NetworkImageView networkImageView;
        public TextView titleTextViewViewHolder;
        public TextView descriptionTextViewViewHolder;

        public ViewHolder(View view) {
            super(view);
            networkImageView = view.findViewById(R.id.networkImageView);
            titleTextViewViewHolder = view.findViewById(R.id.titleTextView);
            descriptionTextViewViewHolder = view.findViewById(R.id.descriptionTextView);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("ITEM", itens.get(getAdapterPosition()));
            context.startActivity(intent);
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    public BeersRecyclerViewAdapter(AppCompatActivity context, ImageLoader imageLoader, List<BeerResponse> itens) {
        this.itens = itens;
        this.context = context;
        this.imageLoader = imageLoader;
    }

    @Override
    public BeersRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_beers, parent, false);
        BeersRecyclerViewAdapter.ViewHolder vh = new BeersRecyclerViewAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(BeersRecyclerViewAdapter.ViewHolder holder, int position) {
        BeerResponse it = this.itens.get(position);
        holder.titleTextViewViewHolder.setText(it.name);
        holder.descriptionTextViewViewHolder.setText(it.first_brewed);
        holder.networkImageView.setImageUrl(it.image_url, imageLoader);
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }
}