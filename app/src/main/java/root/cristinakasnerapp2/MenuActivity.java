package root.cristinakasnerapp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void irlogin(View view) {
        Intent intent = new Intent("android.intent.action.KAS.LOGIN");
        startActivity(intent);
    }

    public void irjugar(View view) {
        Intent intent = new Intent("android.intent.action.MAINACTIVITY");
        startActivity(intent);
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;

    }
}
