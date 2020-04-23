package com.example.sistema_ventas_simple.data.util;

import android.content.Context;
import android.widget.Toast;

public class Message {
    private Context context;

    public Message(Context context) {
        this.context = context;
    }

    public void messageToast(Object textMessage){
        Toast.makeText(context, textMessage.toString(), Toast.LENGTH_SHORT).show();
    }

    public void messageToastSave(){
        messageToast("La inforamación se guardo correctamente");
    }

    public void messageToastDelete(){
        messageToast("La información se elimino correctamente");
    }


}
