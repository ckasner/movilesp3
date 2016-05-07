package root.cristinakasnerapp2;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener; import android.widget.Button;
import android.widget.EditText; import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.io.InputStream;

public class Account extends Activity implements OnClickListener{ private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextConfirmedPassword;

    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); setContentView(R.layout.account);
        editTextUsername = (EditText)findViewById(R.id.accountUsername); editTextPassword = (EditText)findViewById(R.id.accountPassword); editTextConfirmedPassword =
                (EditText)findViewById(R.id.accountConfirmedPassword);
        Button acceptButton = (Button)findViewById(R.id.accountAcceptButton);
        acceptButton.setOnClickListener(this);
        Button cancelButton = (Button)findViewById(R.id.accountCancelButton);
        cancelButton.setOnClickListener(this); }
    private void newAccount(){
        final String name = editTextUsername.getText().toString();
        final String pass = editTextPassword.getText().toString();
        String confPass = editTextConfirmedPassword.getText().toString();
        if (!pass.equals("") && !name.equals("") && pass.equals(confPass)) {

// funciones de interfaz
            Response.Listener<String> listener = new Response.Listener<String>(){ @Override
            public void onResponse(String response) {


                if(response.equals("-1")){
                    Toast.makeText(Account.this, "Nombre de ususario no disponible",Toast.LENGTH_SHORT).show();
                }else{
                    CKASPreference.setPlayerNameDefault(Account.this, name);
                    CKASPreference.setPlayerPassword(Account.this, pass);
                    CKASPreference.setPlayerId(Account.this, response);
                    Intent intent = new Intent("android.intent.action.MENUPARTIDAS");
                    startActivity(intent);
                }

            } };
            Response.ErrorListener errorListener = new Response.ErrorListener(){ @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Account.this, "Errorcillo",Toast.LENGTH_SHORT).show();
            } };
            InterfazConServidor.getServer(this).account(name, pass,listener, errorListener);


             }
        else if(pass.equals("") || confPass.equals("") || name.equals("")) Toast.makeText(Account.this,
                R.string.accountSecondToastMessage,Toast.LENGTH_SHORT).show(); else if(!pass.equals(confPass))
            Toast.makeText(Account.this, R.string.accountThirdToastMessage,Toast.LENGTH_SHORT).show();
    }
    public void onClick(View v) { switch (v.getId()) {
        case R.id.accountAcceptButton:
            newAccount();
            break;
        case R.id.accountCancelButton:
            finish();
            break; }
    } }