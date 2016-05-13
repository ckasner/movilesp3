package root.cristinakasnerapp2;
import android.accounts.Account;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import root.cristinakasnerapp2.DatabaseAdapter;

public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText usernameEditText;
    private EditText passwordEditText;
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); setContentView(R.layout.activity_login);
        usernameEditText=(EditText)findViewById(R.id.loginUsername);
        passwordEditText=(EditText)findViewById(R.id.loginPassword);
        Button loginButton=(Button)findViewById(R.id.loginLoginButton);
        loginButton.setOnClickListener(this);
        Button cancelButton=(Button)findViewById(R.id.loginCancelButton);
        cancelButton.setOnClickListener(this);
        Button newUserButton=(Button)findViewById(R.id.loginNewUserButton);
        newUserButton.setOnClickListener(this); }
    private void check(){
        final String username = usernameEditText.getText().toString();
        final String password = passwordEditText.getText().toString();
        Response.Listener<String> listener = new Response.Listener<String>(){ @Override
        public void onResponse(String response) {


            if(response.equals("-1")){

                Toast.makeText(LoginActivity.this, "Fallo de autenticación",Toast.LENGTH_SHORT).show();
            }else{
                CKASPreference.setPlayerNameDefault(LoginActivity.this, username);
                CKASPreference.setPlayerPassword(LoginActivity.this, password);
                CKASPreference.setPlayerId(LoginActivity.this, response);

                Intent intent = new Intent("android.intent.action.MENUPARTIDAS");
                startActivity(intent);
            }

        } };
        Response.ErrorListener errorListener = new Response.ErrorListener(){ @Override
        public void onErrorResponse(VolleyError error) {
        } };
        InterfazConServidor.getServer(this).login(username, password,CKASPreference.getDevID(this),listener, errorListener);
     }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginLoginButton:
                check();
                break;
            case R.id.loginCancelButton:
                finish();
                break;
            case R.id.loginNewUserButton:
                startActivity(new Intent("android.intent.action.KAS.ACCOUNT"));
                break; }
    } }