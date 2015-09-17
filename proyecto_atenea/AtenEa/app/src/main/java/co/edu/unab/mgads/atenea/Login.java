package co.edu.unab.mgads.atenea;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import co.edu.unab.mgads.atenea.entity.LoginResponse;

public class Login extends ActionBarActivity {

    private static final String TAG = "HomeActivity";
    private EditText eteUsername,etePassword;
    private View btnLogin,vLoading,tviSignIn;

    private String username, password;

    private RequestQueue queue;
    private LoginResponse loginResponse;
    private String headerCookie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Imagen de Fondo del Login
        String  urlCoverMainLogin;
        urlCoverMainLogin = "http://ateneaprueba.soydetic.com/modules/image/Login.jpg";
        ImageView imageViewCoverMainLogin;
        imageViewCoverMainLogin = (ImageView) findViewById(R.id.imageViewCoverLogin);
        Picasso.with(getApplicationContext()).load(urlCoverMainLogin).into(imageViewCoverMainLogin);

        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.imageViewCoverLogin);

        eteUsername = (EditText)findViewById(R.id.eteUsername);
        etePassword = (EditText)findViewById(R.id.etePassword);
        btnLogin = findViewById(R.id.btnIngresar);
        vLoading = findViewById(R.id.vLoading);
        tviSignIn = findViewById(R.id.tviSignIn);

        //events
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //validate
                if(validate())
                {
                    //ir al servidor
                    login();
                }
            }
        });
        tviSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoSignIn();
            }
        });
        vLoading.setVisibility(View.GONE);
    }

    public void VerificarUsuario(View v){
        Button button = (Button) v;
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

    private boolean validate() {

        username = eteUsername.getText().toString().trim();
        password = etePassword.getText().toString().trim();

        eteUsername.setError(null);
        etePassword.setError(null);
        if(username.isEmpty())
        {
            eteUsername.setError("Ingresar este campo");
            return false;
        }
        if(password.isEmpty())
        {
            etePassword.setError("Ingresar este campo");
            return false;
        }
        return true;
    }

    private void gotoSignIn() {

    }

    private void login()
    {

        vLoading.setVisibility(View.VISIBLE);
        queue = Volley.newRequestQueue(this);


        //String url = getString(R.string.url_login)+"?username="+username+"&password="+password;
        String url = getString(R.string.url_drupal_login);
        Log.i("HomeActivity", "url " + url);

        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.i("HomeActivity", "response "+response);
                        GsonBuilder gsonb = new GsonBuilder();
                        Gson gson = gsonb.create();

                        loginResponse=null;
                        try
                        {
                            loginResponse= gson.fromJson(response.toString(),LoginResponse.class);
                            if(loginResponse!=null)
                            {
                                Log.i(TAG, "loginResponse "+loginResponse.toString());

                                gotoHome();
                            }

                        }catch (Exception e)
                        {

                        }
                        vLoading.setVisibility(View.GONE);

                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.i("HomeActivity", "Error: " + error.getMessage());
                // hide the progress dialog

                vLoading.setVisibility(View.GONE);


                Toast toast = Toast.makeText( getApplicationContext(), "Verifique su E-mail UNAB y contraseña", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //return super.getParams();
                Map<String,String> nParams = new HashMap<String, String>();
                nParams.put("username", username);
                nParams.put("password", password);

                Log.d(TAG, "POST params " + nParams.toString());
                return nParams;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                //params.put("Content-Type", "application/json");
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                Map headers = response.headers;
                headerCookie = (String) headers.get("Set-Cookie");

                return super.parseNetworkResponse(response);
            }
        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(10000,
                1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjReq);
    }

    private void gotoHome() {
        loginResponse.setHeaderCookie(headerCookie);
        Log.i(TAG, "cookie "+headerCookie);
        Bundle bundle=new Bundle();
        bundle.putSerializable("ENTITY",loginResponse);
        Intent intent= new Intent(this,MainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

}