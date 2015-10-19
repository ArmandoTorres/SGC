package com.delacrmi.connection;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by miguel on 13/10/15.
 */
public abstract class SocketConnect {

    private Socket socket;
    private AppCompatActivity context;

    public SocketConnect(AppCompatActivity context,String uri){
        try {
            this.context = context;
            socket = IO.socket(uri);
            socket.on("synchronizeClient", onSynchronizeClient);
            socket.on("synchronizeServer", onSynchronizeServer);
            socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(){
        socket.disconnect();
    }

    public void init(){
        socket.connect();
    }

   public Socket getSocket() {
        return socket;
    }

    public abstract void onSynchronizeClient(final Object... args);
    public abstract void onSynchronizeServer(final Object... args);

    public void sendMessage(String event,JSONObject json){
        socket.emit(event,json);
    }

    private Emitter.Listener onConnectError = new Emitter.Listener(){

        @Override
        public void call(Object... args) {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context,"Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    private Emitter.Listener onSynchronizeClient = new Emitter.Listener(){
        @Override
        public void call(final Object... args) {
            onSynchronizeClient(args);
        }
    };

    private Emitter.Listener onSynchronizeServer = new Emitter.Listener(){
        @Override
        public void call(final Object... args) {
            onSynchronizeServer(args);
        }
    };
}
