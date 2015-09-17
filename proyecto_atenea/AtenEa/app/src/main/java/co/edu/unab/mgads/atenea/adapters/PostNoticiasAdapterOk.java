package co.edu.unab.mgads.atenea.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.util.List;

import co.edu.unab.mgads.atenea.R;
import co.edu.unab.mgads.atenea.entity.PostNoticiaResponse;

/**
 * Created by emedinaa on 16/09/15.
 */
public class PostNoticiasAdapterOk extends ArrayAdapter {

    private static final String TAG = "PostAdapter";
    private List<PostNoticiaResponse> items;
    private Context context;

    public PostNoticiasAdapterOk(Context context,List<PostNoticiaResponse> items) {
        super(context, 0);
        this.context=context;
        this.items=items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
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

        /*// Petición para obtener la imagen
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
        */

        return listItemView;
    }
}
