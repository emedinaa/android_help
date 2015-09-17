package co.edu.unab.mgads.atenea;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class Splash extends ActionBarActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        //Imagen de Fondo del Splash
        String  urlCoverMainSplash;
        urlCoverMainSplash = "http://ateneaprueba.soydetic.com/modules/image/Splash.jpg";
        ImageView  imageViewCoverMainSplash;
        imageViewCoverMainSplash = (ImageView) findViewById(R.id.imageViewCoverSplash);
        Picasso.with(getApplicationContext()).load(urlCoverMainSplash).into(imageViewCoverMainSplash);

        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.imageViewCoverSplash);
    }

    public void IngresarUsuario(View v){
        Button button = (Button) v;
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

}
