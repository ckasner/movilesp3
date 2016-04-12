package root.cristinakasnerapp2;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener; import android.widget.Button;
import android.widget.EditText; import android.widget.Toast;
public class Account extends Activity implements OnClickListener{ private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextConfirmedPassword;
    private DatabaseAdapter db;
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); setContentView(R.layout.account);
        editTextUsername = (EditText)findViewById(R.id.accountUsername); editTextPassword = (EditText)findViewById(R.id.accountPassword); editTextConfirmedPassword =
                (EditText)findViewById(R.id.accountConfirmedPassword);
        Button acceptButton = (Button)findViewById(R.id.accountAcceptButton);
        acceptButton.setOnClickListener(this);
        Button cancelButton = (Button)findViewById(R.id.accountCancelButton);
        cancelButton.setOnClickListener(this); }
    private void newAccount(){
        String name = editTextUsername.getText().toString();
        String pass = editTextPassword.getText().toString();
        String confPass = editTextConfirmedPassword.getText().toString();
        if (!pass.equals("") && !name.equals("") && pass.equals(confPass)) {
            db = new DatabaseAdapter(this);
            db.open();
            db.insertUser(name, pass);
            db.close();
            Toast.makeText(Account.this, R.string.accountFirstToastMessage,
                    Toast.LENGTH_SHORT).show();
            finish(); }
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