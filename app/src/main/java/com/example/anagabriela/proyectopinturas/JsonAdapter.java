package com.example.anagabriela.proyectopinturas;

/**
 * Created by Ana Gabriela on 26/4/2017.
 */
import android.content.Context;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import android.widget.TextView;


import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;


public class JsonAdapter extends ArrayAdapter<JSONObject> {
    public JsonAdapter(Context context, int resource, JSONObject[] objects) {
        super(context, resource, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {

            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.celda, null);
        }
        JSONObject p = getItem(position);
        if (p != null) {
            TextView nombrecolor = (TextView) v.findViewById(R.id.nombrecolor);

            NetworkImageView muestra = (NetworkImageView) v.findViewById(R.id.muestra);
            try {
                nombrecolor.setText(p.get("color").toString());
                String UrlImagen = "http://colorexpression.esy.es/img/"+ p.get("codigo").toString()+".jpg";
                ((NetworkImageView) v.findViewById(R.id.muestra)).setImageUrl(UrlImagen,VolleySingleton.getInstance().getImageLoader());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return v;
    }
}
