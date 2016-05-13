package root.cristinakasnerapp2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kasner on 7/5/16.
 */
public class PartidasComenzadasActivity extends AppCompatActivity {
    Response.Listener<JSONArray> listenerJSON;
    Response.ErrorListener errorListenerJSON;
    ListView lista;
    String adversary;
    private List<String> list_partidas;
    private List<String> list_rond;
    private List<String> list_jug;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partidas_comenzadas);

        list_partidas =new ArrayList<String>();
        list_rond =new ArrayList<String>();
        list_jug =new ArrayList<String>();
        lista = (ListView)findViewById(R.id.gamelist);



        listenerJSON =  new Response.Listener<JSONArray>()
        {
            @Override public void onResponse(JSONArray response) {
                for(int i = 0; i < response.length(); i++){
                    JSONObject jresponse = null;
                    try {
                        jresponse = response.getJSONObject(i);
                        String partidaid = jresponse.getString(CKASPreference.PARTIDA_ID_KEY);
                        String playername = jresponse.getString("playernames");
                        if(playername.equals(CKASPreference.getPlayerNameKey(PartidasComenzadasActivity.this))){
                            continue;
                        }
                        list_partidas.add("ID:"+partidaid+"\t"+"Nombre Jugador: "+playername);
                        list_jug.add(playername);
                        list_rond.add(partidaid);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                Log.d("Response", response.toString());
                lista.setAdapter(new ArrayAdapter<String>(PartidasComenzadasActivity.this, android.R.layout.simple_list_item_1,list_partidas));
            }
        };

        errorListenerJSON=
                new Response.ErrorListener()
                {
                    @Override public void onErrorResponse(VolleyError error) {
                        Log.d("ErrorListPartidas", error.toString());
                    }
                };
        InterfazConServidor.getServer(this).openrounds(CKASPreference.GAME_ID_DEFAULT,CKASPreference.getPlayerIdKey(this),listenerJSON,errorListenerJSON);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {
                String roundid;
                int auxint;
                roundid=list_rond.get(arg2);
                adversary=list_jug.get(arg2);
                CKASPreference.setPartidaId(PartidasComenzadasActivity.this,roundid);
                final Response.ErrorListener errorListener = new Response.ErrorListener(){ @Override
                public void onErrorResponse(VolleyError error) {
                } };
                final Response.Listener<String> listener = new Response.Listener<String>(){ @Override
                public void onResponse(String response) {
                    final Response.Listener<String> listener2 = new Response.Listener<String>(){ @Override
                    public void onResponse(String response) {
                        if(response.equals("-1")){
                            Toast.makeText(PartidasComenzadasActivity.this, "Error al notificar la unión",Toast.LENGTH_SHORT).show();
                        }else{
                            Intent intent = new Intent("android.intent.action.MAINACTIVITY");
                            intent.putExtra("tipo",Board.TIPO_UNIDO);
                            intent.putExtra("adversario",adversary);
                            startActivity(intent);
                        }
                    }};
                    if(response.equals("0")){

                        Toast.makeText(PartidasComenzadasActivity.this, "Error al unirse a la partida",Toast.LENGTH_SHORT).show();
                    }else{
                        InterfazConServidor.getServer(PartidasComenzadasActivity.this).sendMessageToUser(adversary,"JOINED",C3Preference.getPlayerId(GameSelection.this),listener2,errorListener);
                    }

                } };
                InterfazConServidor.getServer(PartidasComenzadasActivity.this).addplayertoround(roundid,C3Preference.getPlayerId(GameSelection.this),listener,errorListener);

            }
        });

    }
}
