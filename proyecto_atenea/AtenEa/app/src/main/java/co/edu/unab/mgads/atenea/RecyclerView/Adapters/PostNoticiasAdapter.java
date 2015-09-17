package co.edu.unab.mgads.atenea.RecyclerView.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.edu.unab.mgads.atenea.R;
import co.edu.unab.mgads.atenea.entity.PostNoticiaResponse;


public class PostNoticiasAdapter extends ArrayAdapter {

    // Atributos
    private RequestQueue requestQueue;
    JsonObjectRequest jsArrayRequest;
    private static final String URL_BASE = "http://ateneaprueba.soydetic.com";
    private static final String URL_JSON = "/json/noticias";
    private static final String URL_IMAGE = "http://ateneaprueba.soydetic.com/sites/default/files/imagenes/";
    private static final String TAG = "PostAdapter";
    List<PostNoticiaResponse> items;

    public PostNoticiasAdapter(Context context) {
        super(context,0);

        // Crear nueva cola de peticiones
        requestQueue= Volley.newRequestQueue(context);

        // Nueva petición JSONObject
        jsArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL_BASE + URL_JSON,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        items = parseJson(response);
                        notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error Respuesta en JSON: " + error.getMessage());

                    }
                }
        );

        // Añadir petición a la cola
        requestQueue.add(jsArrayRequest);
    }

    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Referencia del view procesado
        View listItemView;

        //Comprobando si el View no existe
        listItemView = null == convertView ? layoutInflater.inflate(
                R.layout.postnoticias,
                parent,
                false) : convertView;


        // Obtener el item actual
        PostNoticiaResponse item = items.get(position);

        // Obtener Views
        TextView textoTitulo = (TextView) listItemView.
                findViewById(R.id.textoTitulo);
        TextView textoDescripcion = (TextView) listItemView.
                findViewById(R.id.textoDescripcion);
        final ImageView imagenPost = (ImageView) listItemView.
                findViewById(R.id.imagenPost);

        // Actualizar los Views
        textoTitulo.setText(item.getTitulo());
        textoDescripcion.setText(item.getDescripcion());

        // Petición para obtener la imagen
        ImageRequest request = new ImageRequest(
                URL_IMAGE + item.getImagen(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        imagenPost.setImageBitmap(bitmap);
                    }
                }, 0, 0, null,null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        imagenPost.setImageResource(R.drawable.error);
                        Log.d(TAG, "Error en respuesta Bitmap: " + error.getMessage());
                    }
                });

        // Añadir petición a la cola
        requestQueue.add(request);


        return listItemView;
    }

    public List<PostNoticiaResponse> parseJson(JSONObject jsonObject){
        // Variables locales
        List<PostNoticiaResponse> posts = new ArrayList<>();
        JSONArray jsonArray= null;

        try {
            // Obtener el array del objeto
            jsonArray = jsonObject.getJSONArray("items");

            for(int i=0; i<jsonArray.length(); i++){

                try {
                    JSONObject objeto= jsonArray.getJSONObject(i);

                    PostNoticiaResponse post = new PostNoticiaResponse(
                            objeto.getString("titulo"),
                            objeto.getString("descripcion"),
                            objeto.getString("imagen"));


                    posts.add(post);

                } catch (JSONException e) {
                    Log.e(TAG, "Error de parsing: " + e.getMessage());
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return posts;
    }
}
