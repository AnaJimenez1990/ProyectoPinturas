package com.example.anagabriela.proyectopinturas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
    private String correo ;
    private String resp;

    public static final int ActivityID = 24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        context = getAppContext();

        VolleySingleton vs = VolleySingleton.getInstance();
        vs.getRequestQueue().add(jorObtenerListaColores());




        Button mPedido = (Button) findViewById(R.id.realizarpedido);
        mPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realizarPedido();

            }
        });

    }

    public static Context getAppContext()
    {

        return context;
    }



    private void realizarPedido(){
        VolleySingleton vsi = VolleySingleton.getInstance();
        vsi.getRequestQueue().add(jorRegistrarPedido());
        Toast toast = Toast.makeText(getApplicationContext() , "Pedido Realizado", Toast.LENGTH_LONG);
        toast.show();
    }


    public JsonObjectRequest jorRegistrarPedido (){
        Intent i = getIntent();
        correo = i.getExtras().get("Correo").toString();
        String url = "http://colorexpression.esy.es/realizarpedido.php?correo="+correo
                +"&color=1";

        JsonObjectRequest jor = new JsonObjectRequest(
                Request.Method.GET,url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                resp = response.optString("IdCarrera");
               // finish();

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }

        );
        return jor;

    }

    @Override
    public void onActivityResult(int requestcode, int resultcode,Intent da){
        if (requestcode== ActivityID){
            correo = da.getExtras().get("correo").toString();

        }
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
