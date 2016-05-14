package root.cristinakasnerapp2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;

import es.uam.eps.multij.AccionMover;
import es.uam.eps.multij.Evento;
import es.uam.eps.multij.ExcepcionJuego;
import es.uam.eps.multij.Jugador;
import es.uam.eps.multij.JugadorAleatorio;
import es.uam.eps.multij.Partida;
import es.uam.eps.multij.Tablero;
import logica.MovimientoConecta4;
import logica.TableroConecta4;
import vista.OnPlayListener;
import vista.TableroConecta4View;
import root.cristinakasnerapp2.CKASPreference;

public class JuegaActivity extends AppCompatActivity implements OnPlayListener,Jugador {


    public static String STRING_MOV="root.cristinakasnerapp2.MOV";
    Partida game;
        TableroConecta4View tableroView;
        TableroConecta4 tablero;
        ArrayList<Jugador> jugadores;
        TextView infotext;
    public static final String J_INVITADO="jinvitado";
    public static final String J_HOST="jhost";
    public static final String STRING_UNIDO = "root.cristinakasnerapp2.UNIDO";
    public static final String STRING_DESCONECTADO = "root.cristinakasnerapp2.DESCONECTADO";
    private String playerad;
    BroadcastReceiver UNIDOrecv;
    BroadcastReceiver MOVrecv;
    BroadcastReceiver DESCrecv;
    private Chronometer chronometer;
    JugadorRemoto jugadorRemoto;
    private int elapsedTime;
    private String tipo;
    public boolean TURNO;
    private Response.Listener<String> listeneraddresult;
    private Response.ErrorListener errorlisteneraddresult;
    private EditText mensaje;
    private Response.Listener<String> listenerSendText;
    private Response.ErrorListener errorlistenerSendText;
    private TextView mensajeEsperar;
    private Chronometer cronometro;
    private TextView botonEnv;


    //Partida game = new Partida();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_juega);
            Intent intent = getIntent();
            tipo = intent.getExtras().getString("tipo");


            //Jugador jugadorAleatorio = new JugadorAleatorio("Máquina");
            tableroView = (TableroConecta4View)findViewById(R.id.board);
            jugadores = new ArrayList<Jugador>();
            //infotext.setTypeface(monospace);
            tableroView.setOnPlayListener(this);
            tablero = new TableroConecta4();

            //jugadores.add(jugadorRemoto);
            game = new Partida(tablero, jugadores);
            tableroView.setPartida(game);

            tableroView.setVisibility(View.INVISIBLE);
            mensajeEsperar = (TextView) findViewById(R.id.TextEsperar);
            mensajeEsperar.setVisibility(View.VISIBLE);

            cronometro = (Chronometer) findViewById(R.id.chronometer);
            cronometro.setVisibility(View.INVISIBLE);

            botonEnv = (TextView) findViewById(R.id.botonEnviar);
            botonEnv.setVisibility(View.INVISIBLE);

            mensaje=(EditText)findViewById(R.id.textEnviar);
            mensaje.setVisibility(View.INVISIBLE);

            if(tipo.equals(JuegaActivity.J_INVITADO)){
                //el jugador se ha unido
                playerad=new String(intent.getExtras().getString("adversario"));
                CKASPreference.setADname(this,playerad);
                Toast.makeText(this,"¡Te has unido a la partida de "+playerad+"!",Toast.LENGTH_SHORT).show();
                TURNO = false;
                startGame();
            }
            else{
                TURNO    = true;
            }
            initialize();

        }

    public void initialize( ) {

        playerad = CKASPreference.getADname(this);
        UNIDOrecv = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
               
                Bundle extras = intent.getExtras();
                joinedGame(extras);
            }
        };
        MOVrecv = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Bundle extras = intent.getExtras();
                movimientoRecibido(extras);
            }
        };

        DESCrecv = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Bundle extras = intent.getExtras();
                desconectadoRecibido(extras);
            }
        };
    }

    private void desconectadoRecibido(Bundle extras) {
        Intent intent = new Intent("android.intent.action.MENUPARTIDAS");
        startActivity(intent);
    }

    private void movimientoRecibido(Bundle extras) {
        String tab = extras.getString("content");
        listeneraddresult = new Response.Listener<String>(){ @Override
        public void onResponse(String response) {
            if(response.equals("-1")) Toast.makeText(JuegaActivity.this, "Error al añadir el resultado",Toast.LENGTH_SHORT).show();
            else{
                Log.d("ADDRESULT",response);
            }
        } };
        errorlisteneraddresult = new Response.ErrorListener(){ @Override
        public void onErrorResponse(VolleyError error) {
        } };

        try {
            game.getTablero().stringToTablero(tab.substring(3));
            actualizaTablero();
            if(game.getTablero().getEstado()==Tablero.FINALIZADA){
                stopChronometer();
                InterfazConServidor.getServer(this).addresult(CKASPreference.getPlayerIdKey(this),CKASPreference.getGameIdKey(this),String.valueOf(elapsedTime),"0",listeneraddresult,errorlisteneraddresult);
                LayoutInflater layoutInflater = LayoutInflater.from(tableroView.getContext());
                View dialogView = layoutInflater.inflate(R.layout.fin_activity, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(tableroView.getContext());

                // set prompts.xml to be the layout file of the alertdialog builder
                alertDialogBuilder.setView(dialogView);

                alertDialogBuilder
                        .setMessage("Has perdido :(")
                        .setCancelable(true)
                        .setPositiveButton("Volver al menu", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                Intent intent = new Intent("android.intent.action.KAS.MENU");
                                startActivity(intent);
                                finish();
                            }
                        });
                // create an alert dialog

                AlertDialog alertD = alertDialogBuilder.create();

                alertD.show();

            }
            TURNO=true;
        } catch (ExcepcionJuego excepcionJuego) {
            excepcionJuego.printStackTrace();
        }
    }

    private void actualizaTablero() {
        (tableroView).invalidate();
    }

    @Override
        public String getNombre() {
            String name = "hi";


            return name;
        }

        @Override
        public boolean puedeJugar(Tablero tablero) {
            return true;
        }

        @Override
        public void onPlay(int fila,int columna) {
            if(TURNO==false){
                Toast.makeText(tableroView.getContext(), "No es tu turno!", Toast.LENGTH_LONG).show();
                return;
            }else if(game.getTablero().getEstado()!=Tablero.EN_CURSO){
                Toast.makeText(tableroView.getContext(), "La partida ha acabado", Toast.LENGTH_LONG).show();
                return;
            }

            try {
                game.realizaAccion(new AccionMover(this, new MovimientoConecta4(columna)));
                TURNO = false;
               /* if(tablero.compruebaGanar(fila, columna)){
                    tablero.setEstado(2);//FINALIZADA
                }*/
            } catch (ExcepcionJuego excepcionJuego) {
                Toast.makeText(tableroView.getContext(), R.string.Error, Toast.LENGTH_LONG).show();
                return;
            }

        }

        @Override
        public void onCambioEnPartida(Evento evento) {
            switch (evento.getTipo()) {

                case Evento.EVENTO_CAMBIO:
                    tableroView.invalidate();
                    break;
                case Evento.EVENTO_TURNO:
                    tableroView.invalidate();
                    break;
                case Evento.EVENTO_FIN:
                    String usnam ;
                    if(TURNO==true){
                        usnam = CKASPreference.getPlayerNameKey(JuegaActivity.this.getApplicationContext());
                    }else{
                    usnam= new String("Maquina");
                    }

                    tableroView.invalidate();
                    LayoutInflater layoutInflater = LayoutInflater.from(tableroView.getContext());
                    View dialogView = layoutInflater.inflate(R.layout.fin_activity, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(tableroView.getContext());

                    // set prompts.xml to be the layout file of the alertdialog builder
                    alertDialogBuilder.setView(dialogView);
                    stopChronometer();
                    InterfazConServidor.getServer(this).addresult(CKASPreference.getPlayerIdKey(this),CKASPreference.getGameIdKey(this),String.valueOf(elapsedTime),"1",listeneraddresult,errorlisteneraddresult);

                    // setup a dialog window
                    // TODO Poner las strings en strings.xml
                    alertDialogBuilder
                            .setMessage("Enhorabuena "+ usnam +"!! Has Ganado! :)")
                            .setCancelable(true).setPositiveButton("Volver al menu", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // get user input and set it to result
                            Intent intent = new Intent("android.intent.action.KAS.MENU");
                            startActivity(intent);
                            finish();
                        }
                    });
                    // create an alert dialog

                    AlertDialog alertD = alertDialogBuilder.create();


                    alertD.show();


                default:
                    break;
            }

        }


    private void stopChronometer (){
        chronometer.stop();
        String chronometerText = chronometer.getText().toString(); String array[] = chronometerText.split(":");
        if (array.length == 2){
            elapsedTime = Integer.parseInt(array[0]) * 60 +
                    Integer.parseInt(array[1]); } else if (array.length == 3){
            elapsedTime = Integer.parseInt(array[0]) * 60 * 60 + Integer.parseInt(array[1]) * 60 + Integer.parseInt(array[2]);
        } }

    @Override
    protected void onResume() {
        super.onResume();


        // y registramos el recibidor para recibir respuestas
        registerReceiver(UNIDOrecv, new IntentFilter(STRING_UNIDO));
        registerReceiver(MOVrecv, new IntentFilter(STRING_MOV));
        registerReceiver(DESCrecv, new IntentFilter(STRING_DESCONECTADO));
        // Truco sencillo para saber si la actividad está en primer plano
        //Board.enPrimerPlano = true;
    }

    public void joinedGame(Bundle data){
        //SI TU HAS CREADO Y ALGUIEN SE UNE
        playerad= new String(data.getString("sender"));
        CKASPreference.setADname(this,playerad);
        Toast.makeText(this,"¡"+playerad+" se ha unido!",Toast.LENGTH_SHORT).show();
        //roundinfotextview.setText("La partida contra "+adversario+" va a comenzar");
        //esMiTurno=true;
        //jugadorRemoto=new JugadorRemoto(playerad);
        startGame();
    }

    public void startGame() {


        tableroView = (TableroConecta4View)findViewById(R.id.board);

        //infotext.setTypeface(monospace);
        tableroView.setOnPlayListener(this);
        tablero = new TableroConecta4();
        jugadorRemoto = new JugadorRemoto(CKASPreference.getADname(this),this);
        if(tipo.equals(J_HOST)){
            jugadores.add(JuegaActivity.this);
            jugadores.add(jugadorRemoto);
        }else{
            jugadores.add(jugadorRemoto);
            jugadores.add(JuegaActivity.this);
        }
        mensajeEsperar = (TextView) findViewById(R.id.TextEsperar);
        mensajeEsperar.setVisibility(View.INVISIBLE);
        cronometro = (Chronometer) findViewById(R.id.chronometer);
        cronometro.setVisibility(View.VISIBLE);

        botonEnv = (TextView) findViewById(R.id.botonEnviar);
        botonEnv.setVisibility(View.VISIBLE);

        mensaje=(EditText)findViewById(R.id.textEnviar);
        mensaje.setVisibility(View.VISIBLE);
        tableroView.setVisibility(View.VISIBLE);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        if(tablero.getEstado()== Tablero.EN_CURSO){

            game.comenzar(tablero, jugadores);

        }

        if(tablero.getEstado()==Tablero.TABLAS){
            infotext.setText(R.string.Tablas);
            return;
        }
        if(tablero.getEstado()==Tablero.FINALIZADA) {





            infotext.setText("Gana: "+ jugadores.get(tablero.getTurno()).getNombre());
            return;
        }

    }

    public void enviarMensaje(View view){
        listenerSendText = new Response.Listener<String>(){ @Override
        public void onResponse(String response) {
            if(response.equals("-1")) Toast.makeText(JuegaActivity.this, "Error al añadir el resultado",Toast.LENGTH_SHORT).show();
            else{
                Log.d("ADDRESULT",response);
            }
        } };
        errorlistenerSendText = new Response.ErrorListener(){ @Override
        public void onErrorResponse(VolleyError error) {
        } };
        mensaje=(EditText)findViewById(R.id.textEnviar);
        if(mensaje.getText().toString().length()>=140){
            Toast.makeText(JuegaActivity.this, "El mensaje es mas largo que 140 caracteres",Toast.LENGTH_SHORT).show();
            return;
        }

        InterfazConServidor.getServer(this).sendMessage(CKASPreference.getADname(this),mensaje.getText().toString(),CKASPreference.getPlayerIdKey(this),listenerSendText,errorlistenerSendText);
        mensaje.setText("");
    }

    protected void onStop() {
        super.onStop();
        if (isFinishing()&& game.getTablero().getEstado()==Tablero.EN_CURSO) {

            Response.Listener<String> stoplistener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("-1"))
                        Toast.makeText(JuegaActivity.this, "Error al conectarse con el servidor", Toast.LENGTH_SHORT).show();
                    else {
                        Log.d("ONSTOP", response);
                    }
                }
            };
            Response.ErrorListener errorStopListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            };
            InterfazConServidor.getServer(this).removeplayerfromround(CKASPreference.getPartidaIdKey(this), CKASPreference.getPlayerIdKey(this), stoplistener, errorStopListener);
            InterfazConServidor.getServer(this).sendMessage(CKASPreference.getADname(this), "//B", CKASPreference.getPlayerIdKey(this), stoplistener, errorStopListener);
        }else{

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(UNIDOrecv);
        unregisterReceiver(MOVrecv);
    }



    }

