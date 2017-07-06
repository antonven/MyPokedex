package myapps.wycoco.com.mypokedex;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by dell on 7/5/2017.
 */

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Pokemonster> pokemonsters;



    public PokemonAdapter(Context mContext, ArrayList<Pokemonster> pokemonsters) {
        this.mContext = mContext;
        this.pokemonsters = pokemonsters;
    }



    @Override
    public PokemonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_information_item, parent, false);
        ViewHolder view = new ViewHolder(v);
        return view;
    }

    @Override
    public void onBindViewHolder(PokemonAdapter.ViewHolder holder, int position) {

        Glide.with(mContext).load(pokemonsters.get(position).getPokeImage()).into(holder.pokemonBigImage);
        holder.pokemonBigName.setText(pokemonsters.get(position).getPokeName());
        holder.pokeType.setText(pokemonsters.get(position).getPokeType());
        holder.pokeHeight.setText(pokemonsters.get(position).getPokeHeight());
        holder.pokeWeight.setText(pokemonsters.get(position).getPokeWeight());

    }

    @Override
    public int getItemCount() {
        return pokemonsters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView pokemonBigImage;
        TextView pokemonBigName, pokeType, pokeHeight, pokeWeight;

        public ViewHolder(View itemView) {
            super(itemView);

            pokemonBigImage = (ImageView)itemView.findViewById(R.id.pokemonBigImage);
            pokemonBigName = (TextView)itemView.findViewById(R.id.pokemonBigName);
            pokeType = (TextView)itemView.findViewById(R.id.pokeType);
            pokeHeight = (TextView)itemView.findViewById(R.id.pokeHeight);
            pokeWeight = (TextView)itemView.findViewById(R.id.pokeWeight);
        }
    }
}
