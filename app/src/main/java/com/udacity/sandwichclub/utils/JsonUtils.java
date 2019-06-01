package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich;
        JSONObject name;
        String mainName="";
        List<String>alsoKnownAS=new ArrayList<>();
        String placeOfOrigin="";
        String description="";
        String image="";
        List<String> ingredients=new ArrayList<>();

        try {
            JSONObject jsonObject=new JSONObject(json);
            name=jsonObject.getJSONObject("name");
            mainName= name.getString("mainName");
            JSONArray also_know_as=name.getJSONArray("alsoKnownAs");
            if(also_know_as.length()!=0){
                for(int i=0;i<also_know_as.length();i++){
                    alsoKnownAS.add(also_know_as.getString(i));
                }
            }
            placeOfOrigin=jsonObject.getString("placeOfOrigin");
            description=jsonObject.getString("description");
            image=jsonObject.getString("image");
            JSONArray ingredients_array=jsonObject.getJSONArray("ingredients");
            if(ingredients_array.length()!=0) {
                for (int i = 0; i < ingredients_array.length(); i++) {
                    ingredients.add(ingredients_array.getString(i));
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

sandwich=new Sandwich(mainName,alsoKnownAS,placeOfOrigin,description,image,ingredients);
        return sandwich;
    }
}
