package com.example.ricknmorty;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.ViewHolder>  {

    // Member variables.
    private ArrayList<Character> mCharactersData;
    private Context mContext;

    /**
     * Constructor that passes in the sports data and the context.
     *
     * @param charactersData ArrayList containing the characters data.
     * @param context Context of the application.
     */
    CharactersAdapter(Context context, ArrayList<Character> charactersData) {
        this.mCharactersData = charactersData;
        this.mContext = context;
    }


    /**
     * Required method for creating the viewholder objects.
     *
     * @param parent The ViewGroup into which the new View will be added
     *               after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return The newly created ViewHolder.
     */
    @Override
    public CharactersAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.list_item, parent, false));
    }

    /**
     * Required method that binds the data to the viewholder.
     *
     * @param holder The viewholder into which the data should be put.
     * @param position The adapter position.
     */
    @Override
    public void onBindViewHolder(CharactersAdapter.ViewHolder holder,
                                 int position) {
        // Get current character.
        Character currentCharacter = mCharactersData.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentCharacter);
    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return mCharactersData.size();
    }

    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        // Member Variables for the TextViews
        private TextView mNameText;
        private TextView mGenderText;
        private TextView mSpeciesText;
        private TextView mStatusText;
        private ImageView mCharacterImage;

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item.xml layout file.
         */
        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            mNameText = itemView.findViewById(R.id.name);
            mGenderText = itemView.findViewById(R.id.genderValueDetail);
            mSpeciesText = itemView.findViewById(R.id.speciesValueDetail);
            mStatusText = itemView.findViewById(R.id.statusValue);
            mCharacterImage = itemView.findViewById(R.id.characterImage);

            // Set the OnClickListener to the entire view.
            itemView.setOnClickListener(this);
        }

        void bindTo(Character currentCharacter){
            // Populate the textviews with data.
            mNameText.setText(currentCharacter.getName());
            mGenderText.setText(currentCharacter.getGender());
            mSpeciesText.setText(currentCharacter.getSpecies());
            mStatusText.setText(currentCharacter.getStatus());


            // Load the images into the ImageView using the Glide library.
            Glide.with(mContext).load(
                    currentCharacter.getImageUrl()).into(mCharacterImage);
        }

        /**
         * Handle click to show DetailActivity.
         *
         * @param view View that is clicked.
         */
        @Override
        public void onClick(View view) {
            Character currentCharacter = mCharactersData.get(getAdapterPosition());
            Intent detailIntent = new Intent(mContext, DetailActivity.class);
            detailIntent.putExtra("name", currentCharacter.getName());
            detailIntent.putExtra("gender", currentCharacter.getGender());
            detailIntent.putExtra("species", currentCharacter.getSpecies());
            detailIntent.putExtra("status", currentCharacter.getStatus());
            detailIntent.putExtra("origin",currentCharacter.getOrigin());
            detailIntent.putExtra("location",currentCharacter.getLocation());

            detailIntent.putExtra("imageUrl",
                    currentCharacter.getImageUrl());
            mContext.startActivity(detailIntent);
        }
    }

}
