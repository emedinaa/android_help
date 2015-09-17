package co.edu.unab.mgads.atenea;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.edu.unab.mgads.atenea.RecyclerView.Adapters.PostNoticiasAdapter;
import co.edu.unab.mgads.atenea.adapters.PostNoticiasAdapterOk;
import co.edu.unab.mgads.atenea.entity.PostNoticiaResponse;
import co.edu.unab.mgads.atenea.view.DetailNewsActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoticiasFragment extends Fragment {
    private static final String TAG ="NoticiasFragment" ;

    // Atributos
    private RequestQueue requestQueue;
    JsonObjectRequest jsArrayRequest;
    private static final String URL_BASE = "http://ateneaprueba.soydetic.com";
    private static final String URL_JSON = "/json/noticias";
    private static final String URL_IMAGE = "http://ateneaprueba.soydetic.com/sites/default/files/imagenes/";

    // Atributos
    ListView listView;
    //ArrayAdapter adapter;
    PostNoticiasAdapterOk adapter;
    TextView mEdit;
    private PostNoticiaResponse PostNoticiaResponse;
    private List<PostNoticiaResponse> items;
    public NoticiasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_noticias, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Obtener instancia de la lista
        listView = (ListView) getActivity().findViewById(R.id.listNoticias);

        // Crear adaptador y setear
        //adapter = new PostNoticiasAdapter(getActivity());
        //listView.setAdapter(adapter);


        // Regisgrar escucha de la lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.v(TAG, "onItem click "+parent.getAdapter().getItem(position));
                PostNoticiaResponse postNoticiaResponse= (PostNoticiaResponse)parent.getAdapter().getItem(position);
                // Nuevo intent expl�cito
                Intent i = new Intent(getActivity(), DetailNewsActivity.class); //los intent solo son para actividades, dice bien claro startActivity()
                String title = postNoticiaResponse.getTitulo();
                // Setear url
                i.putExtra("TITLE", title);
                i.putExtra("ENTITY",postNoticiaResponse);

                // Iniciar actividad
                startActivity(i);
                /*Cursor ca = (Cursor) adapter.getItem(position);

                // Obtene Titulo de la entrada seleccionada
                String Titulo = ca.getString(ca.getColumnIndex(co.edu.unab.mgads.atenea.entity.PostNoticiaResponse.titulo));

                // Nuevo intent expl�cito
                Intent i = new Intent(getActivity(), Fragment2.class);

                // Setear url
                i.putExtra("Titulo", Titulo);

                // Iniciar actividad
                startActivity(i);*/
            }
        });

        loadData();

    }

    private void loadData() {

        // Crear nueva cola de peticiones
        requestQueue= Volley.newRequestQueue(getActivity());

        // Nueva petición JSONObject
        jsArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL_BASE + URL_JSON,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        items = parseJson(response);
                        populate();
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

    private void populate() {

        // Crear adaptador y setear
        adapter = new PostNoticiasAdapterOk(getActivity(),items);
        listView.setAdapter(adapter);
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
