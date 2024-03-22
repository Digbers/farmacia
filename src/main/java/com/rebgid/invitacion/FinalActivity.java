package com.rebgid.invitacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FinalActivity extends AppCompatActivity {
    Button btnSiguiente;
    ListView listView;
    ArrayList<String> listItems = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        listView = findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(adapter);

        btnSiguiente = findViewById(R.id.btn_listar);
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Instancia de la cola de solicitudes
                RequestQueue queue = Volley.newRequestQueue(FinalActivity.this);

                // URL de tu API
                String url = "http://localhost:8088/api/producto/findAll";

                // Crear una solicitud GET
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Aquí manejas la respuesta
                                Log.d("Response", response);
                                try {
                                    // Convertir la respuesta en un array JSON
                                    JSONArray jsonArray = new JSONArray(response);

                                    // Iterar a través del array
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        // Obtener cada objeto JSON
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        // Extraer el nombre y el precio
                                        String nombre = jsonObject.getString("pro_nombre");
                                        double precio = jsonObject.getDouble("pro_precio");

                                        // Imprimir el nombre y el precio
                                        Log.d("Nombre", nombre);
                                        Log.d("Precio", String.valueOf(precio));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Aquí manejas el error
                        Log.e("Error", error.toString());
                    }
                });

                // Agregar la solicitud a la cola
                queue.add(stringRequest);
            }
        });
    }
}