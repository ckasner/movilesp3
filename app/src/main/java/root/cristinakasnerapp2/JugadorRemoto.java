package root.cristinakasnerapp2;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import es.uam.eps.multij.Evento;
import es.uam.eps.multij.Jugador;
import es.uam.eps.multij.Tablero;
import root.cristinakasnerapp2.InterfazConServidor;

/**
 * Created by Kasner on 14/5/16.
 */
public class JugadorRemoto implements Jugador{

    private String nombre;
    private Activity activity;
    public JugadorRemoto(String name , Activity act){
        this.nombre=name;
        this.activity = act;
    }
    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public boolean puedeJugar(Tablero tablero) {
        return false;
    }

    @Override
    public void onCambioEnPartida(Evento evento) {
        final Response.Listener<String> listener = new Response.Listener<String>(){ @Override
        public void onResponse(String response) {
            if(response.equals("-1")){

            }else{

            }
        }};
        final Response.ErrorListener errorlistener = new Response.ErrorListener(){ @Override
        public void onErrorResponse(VolleyError error) {
        } };
        Response.Listener<JSONObject> listenerJSON =  new Response.Listener<JSONObject>()
        {
            @Override public void onResponse(JSONObject response) {

                Log.d("RESPUESTA NEWMOVEMENT", response.toString());

            }
        };
        Response.ErrorListener errorListenerJSON=
                new Response.ErrorListener()
                {
                    @Override public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                };
        switch (evento.getTipo()) {

            case Evento.EVENTO_CAMBIO:


                break;
            case Evento.EVENTO_TURNO:


                InterfazConServidor.getServer(this.activity).newmovement(CKASPreference.getPlayerIdKey(this.activity), CKASPreference.getPartidaIdKey(this.activity).trim(), evento.getPartida().getTablero().tableroToString(), listenerJSON, errorListenerJSON);
                InterfazConServidor.getServer(this.activity).sendMessage(CKASPreference.getADname(this.activity),"//M"+evento.getPartida().getTablero().tableroToString(),CKASPreference.getPlayerIdKey(this.activity),listener,errorlistener);


                break;
            case Evento.EVENTO_FIN:
                InterfazConServidor.getServer(this.activity).newmovement(CKASPreference.getPlayerIdKey(this.activity), CKASPreference.getPartidaIdKey(this.activity), evento.getPartida().getTablero().tableroToString()+"&finished", listenerJSON, errorListenerJSON);
                InterfazConServidor.getServer(this.activity).sendMessage(CKASPreference.getADname(this.activity), "//M" + evento.getPartida().getTablero().tableroToString(), CKASPreference.getPlayerIdKey(this.activity), listener, errorlistener);
                break;

        }

    }
}
