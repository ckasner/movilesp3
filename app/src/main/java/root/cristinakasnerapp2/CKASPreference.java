package root.cristinakasnerapp2;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

public class CKASPreference extends AppCompatActivity {
    public final static String PLAY_MUSIC_KEY = "music";
    public final static boolean PLAY_MUSIC_DEFAULT = true;
    public final static String FIGURE_COLOR_KEY = "colorkey";
    public final static String FIGURE_COLOR_DEF = "rojo";
    public final static String PLAYER_NAME_KEY = "playername";
    public final static String PLAYER_NAME_DEFAULT = "Persona";
    public final static String PLAYER_ID_KEY = "playerid";
    public final static String PLAYER_ID_DEFAULT = "00";
    public final static String GAME_ID_KEY = "gameid";
    public final static String GAME_ID_DEFAULT = "77";
    public final static String PARTIDA_ID_KEY = "partidaid";
    public final static String PARTIDA_ID_DEFAULT = "00";
    public final static String NUM_PLAYERS_KEY = "numberofplayers";
    public final static String NUM_PLAYERS_DEFAULT = "1";
    public final static String PLAYER_PASS_KEY = "playerpassword";
    public final static String PLAYER_PASS_DEF ="passdef";
    public final static String FIGURE_NAME_KEY = "figurename";
    public final static String FIGURE_NAME_DEFAULT = "completo";
    public final static String FIGURE_CODE_KEY = "figurecode";
    public final static String FIGURE_CODE_DEFAULT = "0011100001110011111111110111111111100111000011100";
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); setContentView(R.layout.main_void);
        FragmentManager fragmentManager = getFragmentManager(); FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        CKASPreferenceFragment fragment = new CKASPreferenceFragment(); fragmentTransaction.replace(android.R.id.content, fragment); fragmentTransaction.commit();
    }


    public static String getFigureName(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(FIGURE_NAME_KEY, FIGURE_NAME_DEFAULT);
    }
    public static void setFigureName(Context context, String figurename) {
        SharedPreferences sharedPreferences = PreferenceManager .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit(); editor.putString(CKASPreference.FIGURE_NAME_KEY, figurename); editor.commit();
    }
    public static String getFigureCode(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(FIGURE_CODE_KEY, FIGURE_CODE_DEFAULT);
    }
    public static void setFigureCode(Context context, String figurecode) {
        SharedPreferences sharedPreferences = PreferenceManager .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit(); editor.putString(CKASPreference.FIGURE_CODE_KEY, figurecode); editor.commit();
    }

    public static void setPlayerNameDefault(Context cont, String str) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cont);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PLAYER_NAME_KEY, str);
        editor.commit();
    }

    public static String getPlayerNameKey(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PLAYER_NAME_KEY, PLAYER_NAME_DEFAULT);
    }

    public static String getPlayerIdKey(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PLAYER_ID_KEY, PLAYER_ID_DEFAULT);
    }

    public static String getGameIdKey(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(GAME_ID_KEY, GAME_ID_DEFAULT);
    }
    public static String getPartidaIdKey(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PARTIDA_ID_KEY, PARTIDA_ID_DEFAULT);
    }


    public static void setPlayerPassword(Context cont, String str) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cont);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PLAYER_PASS_KEY, str);
        editor.commit();
    }

    public static void setPlayerId(Context cont, String str) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cont);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PLAYER_ID_KEY, str);
        editor.commit();
    }

    public static void setPartidaId(Context cont, String str) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cont);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PARTIDA_ID_KEY, str);
        editor.commit();
    }

    public static String getFigureColor(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(FIGURE_COLOR_KEY, FIGURE_COLOR_DEF);
    }

    public static String getPlayerPass(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PLAYER_PASS_KEY, PLAYER_PASS_DEF);
    }

    public static void setFigureColor(Context cont, String str) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(cont);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(FIGURE_COLOR_KEY, str);
        editor.commit();
    }



}