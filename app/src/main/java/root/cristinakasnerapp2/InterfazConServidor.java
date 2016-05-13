package root.cristinakasnerapp2;

/**
 * Created by Kasner on 7/5/16.
 */
 import java.util.HashMap;
 import java.util.Map;
 import org.json.JSONArray;
 import com.android.volley.Request;
 import com.android.volley.RequestQueue;
 import com.android.volley.Response.ErrorListener;
 import com.android.volley.Response.Listener;
 import com.android.volley.toolbox.JsonArrayRequest;
 import com.android.volley.toolbox.StringRequest;
 import com.android.volley.toolbox.Volley;
        import android.content.Context; import android.util.Log;
public class InterfazConServidor {
    private static final String BASE_URL = "http://ptha.ii.uam.es/dadm2016/";
    private static final String ACCOUNT_PHP = "account.php";
    private static final String NEWROUND_PHP = "newround.php";
    private static final String OPENROUND_PHP = "openrounds.php";
    private static final String TOPTEN_PHP = "topten.php";
    private static final String ADD_SCORE_PHP = "addscore.php";
    private static final String DEBUG_TAG = "InterfazConServidor"; private RequestQueue queue;
    private static InterfazConServidor serverInterface;
    private InterfazConServidor(Context context) {
        queue = Volley.newRequestQueue(context); }
    public static InterfazConServidor getServer(Context context) { if (serverInterface == null) {
        serverInterface = new InterfazConServidor(context); }
        return serverInterface; }
    public void account(final String playername, final String playerpassword,
                        Listener<String> callback, ErrorListener errorCallback) {
        String url = BASE_URL + ACCOUNT_PHP; Log.d(DEBUG_TAG, url);
        StringRequest request = new StringRequest(Request.Method.POST, url, callback, errorCallback) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(CKASPreference.PLAYER_NAME_KEY, playername);
                params.put(CKASPreference.PLAYER_PASS_KEY, playerpassword);
                return params;
            }
        };
        queue.add(request);

    }
    public void login(final String playername, final String playerpassword,final String gcmregid,
                      Listener<String> callback, ErrorListener errorCallback) {
        String url = BASE_URL + ACCOUNT_PHP; Log.d(DEBUG_TAG, url);
        StringRequest request = new StringRequest(Request.Method.POST, url, callback, errorCallback) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(CKASPreference.PLAYER_NAME_KEY, playername);
                params.put(CKASPreference.PLAYER_PASS_KEY, playerpassword);params.put(CKASPreference.DEV_ID_KEY,gcmregid);
                params.put("login", "");
                return params;
            }
        };
        queue.add(request); }

    public void newround(final String playerid, final String gameid ,Listener<String> callback, ErrorListener errorCallback) {
        String url = BASE_URL + NEWROUND_PHP; Log.d(DEBUG_TAG, url);
        StringRequest request = new StringRequest(Request.Method.POST, url, callback, errorCallback) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(CKASPreference.PLAYER_ID_KEY, playerid);
                params.put(CKASPreference.GAME_ID_KEY, gameid);
                return params;
            }
        };
        queue.add(request); }

    public void topten(String figurename, Listener<JSONArray> callback,
                       ErrorListener errorCallback) {
        String url = BASE_URL + TOPTEN_PHP + "?"
                + CKASPreference.FIGURE_NAME_KEY + "="
                + figurename + "&json"; Log.d(DEBUG_TAG, url);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, callback, errorCallback);
        queue.add(jsObjRequest); }

    public void addscore(final String playerid, String figurename,
                         String duration, String numberoftiles, String date,
                         Listener<String> callback, ErrorListener errorCallback) {
        /*COMENTAMOS PARA QUE NO PETE
        String url = BASE_URL + ADD_SCORE_PHP + "?"
                + CKASPreference.PLAYER_ID_KEY + "=" + playerid + "&"
                + CKASPreference.FIGURE_NAME_KEY + "=" + figurename + "&"
                + CKASPreference.DURATION_KEY + "=" + duration + "&"
                + CKASPreference.NUMBER_OF_TILES_KEY + "=" + numberoftiles + "&" + CKASPreference.DATE_KEY + "=" + date;
        Log.d(DEBUG_TAG, url);
        StringRequest r = new StringRequest(Request.Method.GET, url, callback, errorCallback);
        queue.add(r);
    */}

    public void openrounds(String gameid,String playerid, Listener<JSONArray> callback,
                       ErrorListener errorCallback) {
        String url = BASE_URL + OPENROUND_PHP + "?"
                + CKASPreference.GAME_ID_KEY + "="
                + gameid + CKASPreference.PLAYER_ID_KEY + playerid; Log.d(DEBUG_TAG, url);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, callback, errorCallback);
        queue.add(jsObjRequest); }

}
