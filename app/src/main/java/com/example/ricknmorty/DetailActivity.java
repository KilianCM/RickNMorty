package com.example.ricknmorty;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    /**
     * Initializes the activity, filling in the data from the Intent.
     *
     * @param savedInstanceState Contains information about the saved state
     *                           of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Initialize the views.
        TextView characterName = findViewById(R.id.nameDetail);
        TextView gender = findViewById(R.id.genderValueDetail);
        TextView species = findViewById(R.id.speciesValueDetail);
        TextView status = findViewById(R.id.statusValueDetail);
        TextView location = findViewById(R.id.locationValueDetail);
        TextView origin = findViewById(R.id.originValueDetail);


        ImageView characterImage = findViewById(R.id.characterImageDetail);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Set the text from the Intent extra.
        characterName.setText(getIntent().getStringExtra("name"));
        gender.setText(getIntent().getStringExtra("gender"));
        species.setText(getIntent().getStringExtra("species"));
        status.setText(getIntent().getStringExtra("status"));
        origin.setText(getIntent().getStringExtra("origin"));
        location.setText(getIntent().getStringExtra("location"));


        // Load the image using the Glide library and the Intent extra.
        Glide.with(this).load(getIntent().getStringExtra("imageUrl"))
                .into(characterImage);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
