package br.com.redeindustrial.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.redeindustrial.R;
import br.com.redeindustrial.json.BeerResponse;


public class BeersRecyclerViewAdapter extends RecyclerView.Adapter<BeersRecyclerViewAdapter.ViewHolder> {

    protected List<BeerResponse> itens;
    protected AppCompatActivity context;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageViewViewHolder;
        public TextView titleTextViewViewHolder;
        public TextView descriptionTextViewViewHolder;

        public ViewHolder(View view) {
            super(view);
            imageViewViewHolder = view.findViewById(R.id.imageView);
            titleTextViewViewHolder = view.findViewById(R.id.titleTextView);
            descriptionTextViewViewHolder = view.findViewById(R.id.descriptionTextView);
        }
    }

    public BeersRecyclerViewAdapter(AppCompatActivity context, List<BeerResponse> itens) {
        this.itens = itens;
        this.context = context;
    }

    @Override
    public BeersRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_search, parent, false);
        BeersRecyclerViewAdapter.ViewHolder vh = new BeersRecyclerViewAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(BeersRecyclerViewAdapter.ViewHolder holder, int position) {
        BeerResponse it = this.itens.get(position);
        holder.titleTextViewViewHolder.setText(it.name);
        holder.descriptionTextViewViewHolder.setText(it.tagline);
        //holder.ratingTextViewViewHolder.setText(it.rating);

        /*
        Bitmap batmapBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.storie);
        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), batmapBitmap);

        circularBitmapDrawable.setCircular(true);
        holder.imageViewViewHolder.setImageDrawable(circularBitmapDrawable);

         */
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }
}