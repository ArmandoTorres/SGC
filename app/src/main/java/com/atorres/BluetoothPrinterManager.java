package com.atorres;

import android.app.Dialog;
import android.content.Context;

import cac.sgc.R;
import it.custom.printer.api.android.*;
import android.content.DialogInterface;
import android.bluetooth.BluetoothDevice;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

/**
 * Created by Legal on 28/10/2015.
 * Volver la clase abstracta y agregar metodos public que pueda sobre escribirse
 * Hacer que la clase lista implemente esta clase.
 */
public class BluetoothPrinterManager {

    //ID del dispositivo elegido.
    int BLUETOOTH_DEVIDE_SELECTED;

    //Lista de dispositivos bluetooth en disponibles.
    private static BluetoothDevice[] btDeviceList = null;

    //Context donde se ejecuta la app.
    private AppCompatActivity context;

    //ListView con los Dispositivos a mostrar.
    private ListView lvDispositivos;

    public BluetoothPrinterManager(AppCompatActivity context) {
        if ( context == null )
            throw new NullPointerException("All constructor parameter's are required.");
        this.context = context;
    }

    public void initComunication() {

        //Variables a utilizar del custom dialog con el listado de dispositivos.
        lvDispositivos       = (ListView) context.findViewById(R.id.customAlertDialogList);

        //Cargamos la lista de dispositivos bluetooth disponibles.
        try {
            btDeviceList = CustomAndroidAPI.EnumBluetoothDevices();
            if ( btDeviceList == null || btDeviceList.length == 0) {
                showAlertMsg("Notificacion", "No se encontro ningun dispositivo bluetooth.");
            } else {

                //Llenamos la lista de dispositivos en la pantalla.
                ArrayList<String> listadoDispositivos = new ArrayList<>();
                for ( int i = 0; i < btDeviceList.length; i++){
                    listadoDispositivos.add(btDeviceList[i].getName());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_single_choice,listadoDispositivos);
                lvDispositivos.setAdapter(adapter);
                lvDispositivos.setItemsCanFocus(false);
                lvDispositivos.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                BLUETOOTH_DEVIDE_SELECTED = 0;
                lvDispositivos.setItemChecked(BLUETOOTH_DEVIDE_SELECTED,true);
                lvDispositivos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        BLUETOOTH_DEVIDE_SELECTED = position;
                    }
                });

                showAlertMsg("Dispositivo.", "Dispositivo seleccionado: " + btDeviceList[BLUETOOTH_DEVIDE_SELECTED].getName());
            }
        } catch (CustomException e) {
            showAlertMsg("Error!", "Al buscar la lista de dispositivos.");
            Log.e("Error","Error al buscar el dispositivo.",e);
        }

    }

    void showAlertMsg(String title,String msg)
    {
        android.app.AlertDialog.Builder dialogBuilder;
        dialogBuilder = new android.app.AlertDialog.Builder(context);

        dialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialogBuilder.setTitle(title);
        dialogBuilder.setMessage(msg);
        dialogBuilder.show();
    }
}