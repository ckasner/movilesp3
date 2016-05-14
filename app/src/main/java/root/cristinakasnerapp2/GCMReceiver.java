package root.cristinakasnerapp2;

/**
 * Created by Kasner on 13/5/16.
 */


        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.widget.Toast;

        import com.google.android.gms.gcm.GoogleCloudMessaging;



public class GCMReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        //Al recibir el mensaje push llama
        //Obtiene el mensaje push enviado por GCM
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
        String messageType = gcm.getMessageType(intent);
        if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
            //Procesar mensaje. Se puede crear una notificación o avisar
            // a la actividad si está en primer plano del mismo modo que se
            // hace en el ejemplo de servicios

            switch (extras.getString("msgtype")){
                case "1":

                    break;
                case "2":
                    //me mandan un mensaje de que se ha unido alguien a la partida
                    if(extras.getString("content").equals("//U")) {
                        //MENSAJE DE UNION A PARTIDA
                        Intent broad = new Intent(JuegaActivity.STRING_UNIDO);
                        broad.putExtras(intent);
                        context.sendBroadcast(broad);
                    }else if(extras.getString("content").contains("//M")){

                        Intent broadc = new Intent(JuegaActivity.STRING_MOV);
                        broadc.putExtras(intent);

                        context.sendBroadcast(broadc);
                    }else if(extras.getString("content").equals("//B")){
                        Toast.makeText(context, "Tu oponente ha abandonado la partida", Toast.LENGTH_LONG).show();
                        Intent broad = new Intent(JuegaActivity.STRING_DESCONECTADO);
                        broad.putExtras(intent);
                        context.sendBroadcast(broad);
                    }else{
                        Toast.makeText(context, extras.getString("sender") + ": " + extras.getString("content"), Toast.LENGTH_LONG).show();
                    }
                    break;
                case "3":


                    break;
            }
            Log.d("Mensaje", ""+extras.toString());
        } }}