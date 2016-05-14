package root.cristinakasnerapp2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import android.app.ListActivity;


import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Scores extends AppCompatActivity {

    private TableLayout tableLayoutViewScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_scores);

        tableLayoutViewScores = (TableLayout)findViewById(R.id.TableLayoutScores);
        headerRow(tableLayoutViewScores);
        topten();

    }
    private void headerRow(TableLayout table) {
        TableRow header = new TableRow(this);
        int textColor = getResources().getColor(R.color.header_top_scores_color);
        float textSize = getResources().getDimension(R.dimen.header_top_scores_size);
        addTextView(header, getResources().getString(R.string.jugador), textColor, textSize,true);
        addTextView(header, getResources().getString(R.string.tiempo), textColor, textSize,true);
        addTextView(header, getResources().getString(R.string.puntuacion), textColor, textSize,true);
        table.addView(header);
    }
    private void addTextView(TableRow tableRow, String text, int textColor,float textSize,Boolean estilo)
    {
        TextView textView = new TextView(this);
        if(estilo){
            textView.setTextSize(textSize);
            textView.setTextColor(textColor);
        }
        textView.setText(text);
        tableRow.addView(textView);
    }
    public void topten() {
        InterfazConServidor is = InterfazConServidor.getServer(this);// String figurename = CCCPreference.getFigureName(TopScores.this);
        is.getresults(CKASPreference.getPlayerIdKey(this),CKASPreference.getGameIdKey(this),true,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
// TODO Auto-generated method stub
                        try { processJSONArray(response);
                        } catch (JSONException e) { e.printStackTrace();
                        } }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("ErrorGetResults", volleyError.getMessage());
                    }


                });
    }

    private void processJSONArray (JSONArray response) throws JSONException{ for (int i=0; i<response.length(); i++){
        JSONObject score = (JSONObject) response.get(i);
        String usuario = score.getString("playername");
        String npiezas = score.getString("timeround");
        String duracion = score.getString("points");
        insertRow(tableLayoutViewScores, usuario, npiezas, duracion);
    } }

    private void insertRow(TableLayout tableLayoutRatings, String usuario, String time, String points) {
        TableRow row = new TableRow(this);
        addTextView(row,usuario, 0, 0,false);
        addTextView(row, time, 0, 0,false);
        int pointsint=Integer.parseInt(points);
        points=String.valueOf(pointsint);
        addTextView(row, points, 0, 0,false);
        tableLayoutRatings.addView(row);
    }

}
