package com.example.anagabriela.proyectopinturas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Pedido extends AppCompatActivity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        context = getAppContext();

        VolleySingleton vs = VolleySingleton.getInstance();
        vs.getRequestQueue().add(jorObtenerListaColores());
    }

    public static Context getAppContext()
    {

        return context;
    }


    public JsonObjectRequest jorObtenerListaColores (){
        String url = "http://colorexpression.esy.es/coloresdisponibles.php";
        JsonObjectRequest jor = new JsonObjectRequest(
                Request.Method.GET,url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray array = response.getJSONArray("colores");
                    JSONObject[] objetos = new JSONObject[array.length()];
                    for(int i = 0; i < objetos.length ; i++)
                    {
                        objetos[i] = (JSONObject)array.get(i);
                    }
                    JsonAdapter ja = new JsonAdapter(getApplicationContext(),R.layout.celda,objetos);
                    ((GridView) findViewById(R.id.listacolores)).setAdapter(ja);
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }

        );
        return jor;

    }













}
