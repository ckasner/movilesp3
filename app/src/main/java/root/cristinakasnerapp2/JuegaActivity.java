package root.cristinakasnerapp2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

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



        Partida game;
        TableroConecta4View tableroView;
        TableroConecta4 tablero;
        ArrayList<Jugador> jugadores;
        TextView infotext;
    public static final String J_INVITADO="jinvitado";
    public static final String J_HOST="jhost";
    private Chronometer chronometer;
    private int elapsedTime;


        //Partida game = new Partida();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_juega);

            Jugador jugadorAleatorio = new JugadorAleatorio("Máquina");

            jugadores = new ArrayList<Jugador>();
            jugadores.add(JuegaActivity.this);
            jugadores.add(jugadorAleatorio);
            tableroView = (TableroConecta4View)findViewById(R.id.board);

            //infotext.setTypeface(monospace);
            tableroView.setOnPlayListener(this);
            tablero = new TableroConecta4();
            game = new Partida(tablero, jugadores);
            tableroView.setPartida(game);
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


            try {
                game.realizaAccion(new AccionMover(this, new MovimientoConecta4(columna)));
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
                    if(tablero.getTurno()==0){
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



                    // setup a dialog window
                    // TODO Poner las strings en strings.xml
                    alertDialogBuilder
                            .setMessage("Enhorabuena "+ usnam +"!! Has Ganado!\n¿Quieres volver a jugar?")
                            .setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // get user input and set it to result
                                    Intent intent = new Intent(JuegaActivity.this.getApplicationContext(), JuegaActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .setNegativeButton("No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                    // create an alert dialog

                    AlertDialog alertD = alertDialogBuilder.create();
                    stopChronometer();

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



    }

