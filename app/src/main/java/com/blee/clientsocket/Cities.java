package com.blee.clientsocket;

import android.os.Bundle;
import android.os.Message;
import android.os.Handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Cities extends Thread {
    private String cityName;
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private static final String Ip = "10.0.2.22";
    private static  final int porta = 12345;

    private Handler handler;

    public Cities(String cityName, Handler handler) {
        this.cityName = cityName;
        this.handler = handler;
    }

    @Override
    public void run() {
        super.run();
        try {
            socket = new Socket(Ip, porta);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            out.writeUTF( cityName );
            out.flush();
            String uf = in.readUTF();
            int population = in.readInt();
            int foundation = in.readInt();
            out.close();
            in.close();
            Bundle b = new Bundle();
            b.putString("uf", uf);
            b.putInt("population", population);
            b.putInt("foundation", foundation);
            Message msg = new Message();
            msg.what = 1;
            msg.setData(b);
            handler.sendMessage(msg);

            socket.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

}