package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.w3c.dom.Text;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    TextView origin_text;
    TextView origin;
    TextView alsoKnownAs_text;
    TextView alsoKnownAs;
    TextView ingredients;
    TextView description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        origin_text=findViewById(R.id.origin_pl);
        origin=findViewById(R.id.origin_tv);
        alsoKnownAs_text=findViewById(R.id.alsoknownas_pl);
        alsoKnownAs=findViewById(R.id.also_known_tv);
        ingredients=findViewById(R.id.ingredients_tv);
        description=findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage()).placeholder(R.mipmap.ic_launcher)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        if(!sandwich.getPlaceOfOrigin().isEmpty()){
            origin.setText(sandwich.getPlaceOfOrigin());
        }
        else{
            origin_text.setVisibility(View.GONE);
            origin.setVisibility(View.GONE);
        }
        if(sandwich.getAlsoKnownAs().isEmpty()){
            alsoKnownAs_text.setVisibility(View.GONE);
            alsoKnownAs.setVisibility(View.GONE);
        }
        else{
            List<String> str=sandwich.getAlsoKnownAs();
            String alsoKnown=str.toString().replace("[","").replace("]","");
            alsoKnownAs.setText(alsoKnown);

        }
        description.setText(sandwich.getDescription());

        List<String>ingredient=sandwich.getIngredients();
        String ingredient1= ingredient.toString().replace("[","").replace("]","");
        ingredients.setText(ingredient1);

    }
}
