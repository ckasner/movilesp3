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

import root.cristinakasnerapp2.DatabaseAdapter;

public class LoginActivity extends Activity implements View.OnClickListener {
    private DatabaseAdapter db;
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
        String username = usernameEditText.getText().toString(); String password = passwordEditText.getText().toString();
        db=new DatabaseAdapter(this);
        db.open();
        boolean in = db.isRegistered(username, password); db.close();
        if (in){
            CKASPreference.setPlayerNameDefault(LoginActivity.this, username);
            CKASPreference.setPlayerPassword(LoginActivity.this, password);
            startActivity(new Intent(this, JuegaActivity.class)); finish();
        }
        else {
            new AlertDialog.Builder(this) .setTitle(R.string.loginAlertDialogTitle) .setMessage(R.string.loginAlertDialogMessage) .setNeutralButton(R.string.loginAlertDialogNeutralButtonText,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {}
                    }).show();
        } }
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