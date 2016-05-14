package root.cristinakasnerapp2;

/**
 * Created by Kasner on 7/5/16.
 */
 import java.util.HashMap;
 import java.util.Map;
 import org.json.JSONArray;
 import org.json.JSONObject;

 import com.android.volley.Request;
 import com.android.volley.RequestQueue;
 import com.android.volley.Response.ErrorListener;
 import com.android.volley.Response.Listener;
 import com.android.volley.toolbox.JsonArrayRequest;
 import com.android.volley.toolbox.JsonObjectRequest;
 import com.android.volley.toolbox.StringRequest;
 import com.android.volley.toolbox.Volley;
        import android.content.Context; import android.util.Log;
public class InterfazConServidor {
    private static final String BASE_URL = "http://ptha.ii.uam.es/dadm2016/";
    private static final String ACCOUNT_PHP = "account.php";
    private static final String NEWROUND_PHP = "newround.php";
    private static final String OPENROUND_PHP = "openrounds.php";
    private static final String DEBUG_TAG = "InterfazConServidor"; private RequestQueue queue;
    private static final String SENDMSG_PHP = "sendmsg.php";
    private static final String NEWMOVEMENT_PHP = "newmovement.php";
    private static final String ADDPLAYER_PHP = "addplayertoround.php";
    private static InterfazConServidor serverInterface;
    private static final String ADDRESULT_PHP = "addresult.php";
    private static final String GETRESULTS_PHP = "getresults.php";
    private static final String REMOVEPLAYER_PHP = "removeplayerfromround.php";

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



    public void openrounds(String gameid,String playerid, Listener<JSONArray> callback,
                       ErrorListener errorCallback) {
        String url = BASE_URL + OPENROUND_PHP + "?"
                + CKASPreference.GAME_ID_KEY + "="
                + gameid +"&"+ CKASPreference.PLAYER_ID_KEY +"="+ playerid; Log.d(DEBUG_TAG, url);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, callback, errorCallback);
        queue.add(jsObjRequest); }


    public void sendMessage(final String to, final String msg,final String playerid ,Listener<String> callback, ErrorListener errorCallback) {
        String url = BASE_URL + SENDMSG_PHP;
        Log.d(DEBUG_TAG, url);
        StringRequest request = new StringRequest(Request.Method.POST, url, callback, errorCallback) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("to", to);
                params.put("msg", msg);
                params.put(CKASPreference.PLAYER_ID_KEY, playerid);
                return params;
            }
        };
        queue.add(request);
    }

    public void addplayertoround(final String roundid, final String playerid ,Listener<String> callback, ErrorListener errorCallback) {
        String url = BASE_URL + ADDPLAYER_PHP; Log.d(DEBUG_TAG, url);
        StringRequest request = new StringRequest(Request.Method.POST, url, callback, errorCallback) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(CKASPreference.PLAYER_ID_KEY, playerid);
                params.put(CKASPreference.ROUND_ID_KEY, roundid);
                return params;
            }
        };
        queue.add(request); }

    public void newmovement(String playerid, String roundid, String codeboard, Listener<JSONObject> callback, ErrorListener errorCallback) {
        String url = BASE_URL + NEWMOVEMENT_PHP + "?"
                + CKASPreference.PLAYER_ID_KEY + "="
                + playerid +"&"
                +CKASPreference.ROUND_ID_KEY+"="+roundid
                +"&codedboard="+codeboard+
                "&json";
        Log.d(DEBUG_TAG, url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(url,null, callback, errorCallback);
        queue.add(jsObjRequest);
    }

    public void addresult(final String playerid, final String gameid,final String roundtime,final String points ,Listener<String> callback, ErrorListener errorCallback) {
        String url = BASE_URL + ADDRESULT_PHP; Log.d(DEBUG_TAG, url);
        StringRequest request = new StringRequest(Request.Method.POST, url, callback, errorCallback) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(CKASPreference.PLAYER_ID_KEY, playerid);
                params.put("gameid", gameid);
                params.put("roundtime", roundtime);

                params.put("points", points);
                return params;
            }
        };
        queue.add(request); }


    public void getresults(String playerid,String gameid,Boolean groupbyuser, Listener<JSONArray> callback, ErrorListener errorCallback) {
        String url = BASE_URL + GETRESULTS_PHP + "?";
        if(groupbyuser){
            url=url+"groupbyuser&";
        }
        url=url+ CKASPreference.PLAYER_ID_KEY + "="
                + playerid +"&"
                +CKASPreference.GAME_ID_KEY+"="+gameid
                +"&json";
        Log.d(DEBUG_TAG, url);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, callback, errorCallback);
        queue.add(jsObjRequest);
    }

    public void removeplayerfromround(final String roundid, final String playerid ,Listener<String> callback, ErrorListener errorCallback) {
        String url = BASE_URL + REMOVEPLAYER_PHP; Log.d(DEBUG_TAG, url);
        StringRequest request = new StringRequest(Request.Method.POST, url, callback, errorCallback) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(CKASPreference.PLAYER_ID_KEY, playerid);
                params.put(CKASPreference.ROUND_ID_KEY, roundid);
                return params;
            }
        };
        queue.add(request); }
}
