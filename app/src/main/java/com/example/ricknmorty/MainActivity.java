package com.example.ricknmorty;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    // Member variables.
    private RecyclerView mRecyclerView;
    private ArrayList<Character> mCharactersData;
    private ArrayList<Character> mCharactersFullList;
    private CharactersAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerView);

        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the ArrayList that will contain the data.
        mCharactersData = new ArrayList<>();
        mCharactersFullList = new ArrayList<>();


        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new CharactersAdapter(this, mCharactersData);
        mRecyclerView.setAdapter(mAdapter);

        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }

        // Check the status of the network connection.
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        // If the network is available, connected, and the search field
        // is not empty, start a BookLoader AsyncTask.
        if (networkInfo != null && networkInfo.isConnected()) {

            Bundle queryBundle = new Bundle();
            getSupportLoaderManager().restartLoader(0, queryBundle, this);

        }
        // Otherwise update the TextView to tell the user there is no connection.
        else {

        }



        // Helper class for creating swipe to dismiss and drag and drop
        // functionality.
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT |
                        ItemTouchHelper.DOWN | ItemTouchHelper.UP,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            /**
             * Defines the drag and drop functionality.
             *
             * @param recyclerView The RecyclerView that contains the list items
             * @param viewHolder The SportsViewHolder that is being moved
             * @param target The SportsViewHolder that you are switching the
             *               original one with.
             * @return true if the item was moved, false otherwise
             */
            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                // Get the from and to positions.
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();

                // Swap the items and notify the adapter.
                Collections.swap(mCharactersData, from, to);
                mAdapter.notifyItemMoved(from, to);
                return true;
            }

            /**
             * Defines the swipe to dismiss functionality.
             *
             * @param viewHolder The viewholder being swiped.
             * @param direction The direction it is swiped in.
             */
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                 int direction) {
                // Remove the item from the dataset.
                mCharactersData.remove(viewHolder.getAdapterPosition());
                // Notify the adapter.
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });

        // Attach the helper to the RecyclerView.
        helper.attachToRecyclerView(mRecyclerView);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                // An item was selected.
                String item = parent.getItemAtPosition(pos).toString();

                mCharactersData.clear(); //vider la liste d'affichage

                //parcourir la liste entière
                for (Character c : mCharactersFullList) {
                    //si le statut correspond à la sélection
                    if (c.getStatus().equals(item)) {
                        mCharactersData.add(c); //on ajoute à la liste d'affichage
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                mCharactersData.clear(); //on vide la liste d'affichage
                for (Character c : mCharactersFullList) {
                    mCharactersData.add(c); //on ajoute tout à la liste d'affichage
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "";

        if (args != null) {
            queryString = args.getString("queryString");
        }

        return new CharacterLoader(this, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            // Convert the response into a JSON object.
            JSONObject jsonObject = new JSONObject(data);
            // Get the JSONArray of book items.
            JSONArray itemsArray = jsonObject.getJSONArray("results");

            // Initialize iterator and results fields.
            int i = 0;


            // Look for results in the items array, exiting when both the
            // title and author are found or when all items have been checked.
            while (i < itemsArray.length()) {
                // Get the current item information.
                JSONObject character = itemsArray.getJSONObject(i);
                JSONObject location = character.getJSONObject("location");
                JSONObject origin = character.getJSONObject("origin");



                try {
                    mCharactersData.add(new Character(character.getInt("id"),character.getString("name"),character.getString("status"),
                            character.getString("species"),character.getString("type"),character.getString("gender"),character.getString("image"),
                            origin.getString("name"),location.getString("name")));
                    // Notify the adapter of the change.
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Move to the next item.
                i++;
            }
            mCharactersFullList = new ArrayList<>(mCharactersData);
            mAdapter.notifyDataSetChanged();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        // Do nothing.  Required by interface.
    }
}
