package root.cristinakasnerapp2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import android.app.ListActivity;


import java.util.ArrayList;

public class ratings extends ListActivity {
    private DatabaseAdapter db;
    private ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = fromDatabaseToStringArray();
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, R.layout.list,R.id.textlist, list);
        setListAdapter(adapter);
        ListView listView = getListView();


    }
    private ArrayList<String> fromDatabaseToStringArray (){
        ArrayList<String> list;
        db = new DatabaseAdapter(this);
        db.open();
        list = db.getAllRounds();
        db.close();
        return list;
    }
}
