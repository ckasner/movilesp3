package root.cristinakasnerapp2;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;


public class MenuActivity extends AppCompatActivity {
    String SENDERIDGCM = "125442492416";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        /*SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                String name = "hum";

                if (key.equals(CKASPreference.PLAYER_NAME_KEY)){
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    if (sharedPreferences.contains(CKASPreference.PLAYER_NAME_KEY))
                        name = sharedPreferences.getString(CKASPreference.PLAYER_NAME_KEY, CKASPreference.PLAYER_NAME_DEFAULT);
                    CKASPreference.setPlayerNameDefault(getApplicationContext() , name);

                }





            }
        };
        prefs.registerOnSharedPreferenceChangeListener(listener);*/
        if (checkPlayServices()){

            if ( CKASPreference.getDevID(this).equals("unregistered") ) {

                registerInBackground();
            }
        }
    }

    public void irlogin(View view) {
        Intent intent = new Intent("android.intent.action.KAS.LOGIN");
        startActivity(intent);
    }
    public void irpunt(View view) {
        if(!CKASPreference.getPlayerNameKey(this).equals("Def")) {
            Intent intent = new Intent("android.intent.action.KAS.RATINGS");
            startActivity(intent);
        }else {
            Toast.makeText(MenuActivity.this, "Debe registrarse para ver las puntuaciones",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent("android.intent.action.KAS.LOGIN");
            startActivity(intent);
        }
    }

    public void irpartidas(View view) {

        //Toast.makeText(this, CKASPreference.getPlayerNameKey(this)+ CKASPreference.getPlayerPass(this),
               // Toast.LENGTH_LONG).show();
        if(!CKASPreference.getPlayerNameKey(this).equals("Def")){

            Intent intent = new Intent("android.intent.action.MENUPARTIDAS");
            startActivity(intent);

        }else {

            Intent intent = new Intent("android.intent.action.KAS.LOGIN");
            startActivity(intent);
        }
    }




    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.Prefs:
                Intent intent = new Intent("android.intent.action.KAS.PREF");
                startActivity(intent);
                return true; }
        return super.onOptionsItemSelected(item); }


    private void registerInBackground() { new AsyncTask() {
        @Override
        protected Object doInBackground(Object[] params) {
            String id = "";
            try {

                GoogleCloudMessaging gcm = GoogleCloudMessaging .getInstance(MenuActivity.this);


                id = gcm.register(SENDERIDGCM);


                CKASPreference.setDevID(MenuActivity.this,id);

            } catch (IOException ex) {
                Log.d("GCM REGISTER:", "Error registro en GCM:" + ex.getMessage());
            }
            return id; }
    }.execute(null, null, null);
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i("ERROR", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true; }
}
