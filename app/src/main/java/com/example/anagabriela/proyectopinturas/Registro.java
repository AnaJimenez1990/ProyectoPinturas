package com.example.anagabriela.proyectopinturas;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Registro extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPass;
    private EditText mNombre;
    private  EditText mApellido;
    private EditText mTel;



    public static Context context;


    private String resp="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        mEmail = (EditText) findViewById(R.id.mail);
        mPass = (EditText) findViewById(R.id.pass);
        mNombre = (EditText) findViewById(R.id.nombre);
        mApellido = (EditText) findViewById(R.id.apellido);
        mTel=(EditText) findViewById(R.id.tel);


        Button mRegistrar = (Button) findViewById(R.id.registrar);
        mRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarUsuario();

            }
        });


    }

    public static Context getAppContext()
    {
        return context;
    }
    private boolean isEmailValid(String email) {

        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {

        return password.length() > 4;
    }





    private void registrarUsuario() {
        // Reset errors.
        mEmail.setError(null);
        mPass.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmail.getText().toString();
        String password = mPass.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPass.setError(getString(R.string.error_invalid_password));
            focusView = mPass;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmail.setError(getString(R.string.error_field_required));
            focusView = mEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEmail;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            context = getAppContext();
            VolleySingleton v = VolleySingleton.getInstance();
            v.getRequestQueue().add( jorRegistrar());

        }

    }

    public JsonObjectRequest jorRegistrar (){
        String correo = mEmail.getText().toString();
        String pass = mPass.getText().toString();
        String nombre = mNombre.getText().toString();
        String apellido = mApellido.getText().toString();
        String tel = mTel.getText().toString();
        String url = "http://colorexpression.esy.es/registrarcliente.php?correo="+correo
                +"&password="+pass+"&nombre="+nombre+"&apellido="+apellido+"&tel="+tel;

        JsonObjectRequest jor = new JsonObjectRequest(
                Request.Method.GET,url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast toast = Toast.makeText(context, "Usuario Registrado", Toast.LENGTH_LONG);
                toast.show();
                resp = response.optString("IdCarrera");
                finish();

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
