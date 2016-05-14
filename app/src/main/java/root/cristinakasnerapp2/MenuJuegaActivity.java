package root.cristinakasnerapp2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by Kasner on 7/5/16.
 */
public class MenuJuegaActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugar_menu);
        //Toast.makeText(MenuJuegaActivity.this, CKASPreference.getDevID(this),Toast.LENGTH_SHORT).show();
    }

    public void irPartidasComenzadas(View view) {
        Intent intent = new Intent("android.intent.action.KAS.PARTIDASCOMENZADAS");
        startActivity(intent);
    }


    public void irjugar(View view) {

        Response.Listener<String> listener = new Response.Listener<String>(){ @Override
        public void onResponse(String response) {


            if(response.equals("-1")){

                Toast.makeText(MenuJuegaActivity.this, "Error al crear partida",Toast.LENGTH_SHORT).show();
            }else{
                CKASPreference.setRoundId(MenuJuegaActivity.this, response);

                //InterfazConServidor.getServer(MenuJuegaActivity.this).openrounds(CKASPreference.getGameIdKey(MenuJuegaActivity.this), CKASPreference.getPlayerIdKey(MenuJuegaActivity.this), listenerJSON,listenerJSONerror);


                Intent intent = new Intent("android.intent.action.MAINACTIVITY");
                intent.putExtra("tipo", JuegaActivity.J_HOST);
                startActivity(intent);
            }

        } };
        Response.ErrorListener errorListener = new Response.ErrorListener(){ @Override
        public void onErrorResponse(VolleyError error) {
        } };
        InterfazConServidor.getServer(this).newround(CKASPreference.getPlayerIdKey(this), CKASPreference.getGameIdKey(this),listener, errorListener);





    }



}