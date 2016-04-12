package root.cristinakasnerapp2;


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {
private DatabaseAdapter db;
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
    }

    public void irlogin(View view) {
        Intent intent = new Intent("android.intent.action.KAS.LOGIN");
        startActivity(intent);
    }
    public void irpunt(View view) {
        Intent intent = new Intent("android.intent.action.KAS.RATINGS");
        startActivity(intent);
    }

    public void irjugar(View view) {
        db = new DatabaseAdapter(this);
        db.open();
        //Toast.makeText(this, CKASPreference.getPlayerNameKey(this)+ CKASPreference.getPlayerPass(this),
               // Toast.LENGTH_LONG).show();
        if(!CKASPreference.getPlayerNameKey(this).equals("Def")){
            db.close();
            Intent intent = new Intent("android.intent.action.MAINACTIVITY");
            startActivity(intent);

        }else {
            db.close();
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
}
