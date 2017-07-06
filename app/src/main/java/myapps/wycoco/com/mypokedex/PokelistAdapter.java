package myapps.wycoco.com.mypokedex;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by dell on 7/5/2017.
 */

public class PokelistAdapter extends RecyclerView.Adapter<PokelistAdapter.ViewHolder> {


    private Context mContext;
    private ArrayList<Pokemonster> pokemonsters;

    public PokelistAdapter(Context mContext, ArrayList<Pokemonster> pokemonsters) {
        this.mContext = mContext;
        this.pokemonsters = pokemonsters;
    }

    @Override
    public PokelistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_list_item, parent, false);
        ViewHolder view = new ViewHolder(v);
        return view;
    }

    @Override
    public void onBindViewHolder(PokelistAdapter.ViewHolder holder, int position) {
        Glide.with(mContext).load(pokemonsters.get(position).getPokeImage()).into(holder.pokemonImage);
        holder.pokemonName.setText(pokemonsters.get(position).getPokeName());
//        holder.pokemonID.setText(pokemonsters.get(position).getPokeID());
    }

    @Override
    public int getItemCount() {
        return pokemonsters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView pokemonImage, pokeballImage;
        TextView pokemonName, pokemonID;
        LinearLayout linearLayout;

        public ViewHolder(final View itemView) {
            super(itemView);

            pokemonImage = (ImageView)itemView.findViewById(R.id.pokemonImage);
            pokemonName = (TextView)itemView.findViewById(R.id.pokemonName);
            pokeballImage = (ImageView)itemView.findViewById(R.id.pokeball);
            pokemonID = (TextView)itemView.findViewById(R.id.pokemonID);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.linearLayout);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent n = new Intent(mContext, PokemonInformationActivity.class);
                    n.putExtra("pokeID" , + pokemonsters.get(getAdapterPosition()).getPokeID());
                    n.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(n);

                }
            });
        }
    }
}
