package cac.sgc.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import com.atorres.AndroidUtils;
import com.atorres.BarcodeEncoder;
import com.atorres.BluetoothPrinterManager;
import com.atorres.Contents;
import com.atorres.PrinterObjectFormat;
import com.delacrmi.controller.Entity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import cac.sgc.MainActivity;
import cac.sgc.R;
import cac.sgc.entities.Frentes;
import cac.sgc.entities.Transaccion;
import cac.sgc.mycomponents.ListadoTransacciones;
import cac.sgc.mycomponents.MyDialogDateListenerFactory;
import cac.sgc.mycomponents.TransaccionAdapter;

import static com.atorres.BluetoothPrinterManager.*;

/**
 * Created by Legal on 04/10/2015.
 */
public class Listado extends Fragment {

    private View view;
    private MainActivity context;
    private EditText editFiltroPorFecha;
    private ImageButton generarReporte;
    private ListView listadoTransacciones;

    private static Listado ourInstance = null;
    private BluetoothPrinterManager bluetoothPrinterManager;

    public Listado() {}

    /**
     * Constructor que recive la actidad principal
     * para siempre tener el contexto principal.
     *
     * @param context MainActivity que es el contexto principal de la aplicacion.
     * */
    public static Listado init(MainActivity context) {
        if ( ourInstance == null ) {
            ourInstance = new Listado();
            ourInstance.context = context;
        }
        return ourInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.listado, container, false);
        initComponents();
        return view;
    }

    /**
     * Metodo utilizado para iniciar los componentes, enlazando las variables
     * con el xml y agregar los metodos que le dan funcionalidad a los
     * componentes.
     * */
    private void initComponents() {
        editFiltroPorFecha      = (EditText) view.findViewById(R.id.editFiltroPorFecha);
        generarReporte          = (ImageButton) view.findViewById(R.id.generarReporte);
        listadoTransacciones    = (ListView) view.findViewById(R.id.listViewTransacciones);

        // LLenando el listado en pantalla.
        List<Entity> listado = ourInstance.context.getEntityManager().find(Transaccion.class, "*", null, null);
        List<ListadoTransacciones> resultado = new ArrayList<>();
        for (Entity a : listado ){
            String reporte  = "";
            String subTitle = "";
            String barcode  = "";
            if ( a instanceof Transaccion ) {

                // SubTitulo del reporte.
                subTitle = ((Frentes) ourInstance.context.getEntityManager()
                                              .findOnce(Frentes.class,
                                                        Frentes.TIPO_CANIA,
                                                        Frentes.ID_FRENTE+" = "+a.getColumnValueList().getAsString(Transaccion.FRENTE_CORTE),
                                                        null)).getColumnValueList().getAsString(Frentes.TIPO_CANIA);
                subTitle += " Envio NO. "+a.getColumnValueList().getAsString(Transaccion.NO_ENVIO);

                //Detalle
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                reporte += "Fecha: "+format.format(new Date(a.getColumnValueList().getAsLong(Transaccion.FECHA_CORTE)));
                reporte += " - Frente Corte: "+a.getColumnValueList().getAsString(Transaccion.FRENTE_CORTE);
                reporte += " - Frente Alce: "+a.getColumnValueList().getAsString(Transaccion.FRENTE_ALCE);
                reporte += " - Orden Quema: "+a.getColumnValueList().getAsString(Transaccion.ORDEN_QUEMA);
                reporte += " - Finca: "+ String.format("%03d", Integer.parseInt(a.getColumnValueList().getAsString(Transaccion.ID_FINCA)));
                reporte += " - Canial: "+String.format("%04d", Integer.parseInt(a.getColumnValueList().getAsString(Transaccion.ID_CANIAL)));
                reporte += " - Lote: "+a.getColumnValueList().getAsString(Transaccion.ID_LOTE);
                reporte += " - Clave Corte: "+a.getColumnValueList().getAsString(Transaccion.CLAVE_CORTE);
                reporte += " - Carreta: "+a.getColumnValueList().getAsString(Transaccion.CODIGO_CARRETA);
                reporte += " - Vagon: "+a.getColumnValueList().getAsString(Transaccion.CODIGO_VAGON);
                reporte += " - Alzadora: "+a.getColumnValueList().getAsString(Transaccion.CODIGO_COSECHADORA);
                reporte += " - Operador Alzadora: "+a.getColumnValueList().getAsString(Transaccion.OPERADOR_COSECHADORA);
                reporte += " - Tractor: "+a.getColumnValueList().getAsString(Transaccion.CODIGO_TRACTOR);
                reporte += " - Operador Tractor: "+a.getColumnValueList().getAsString(Transaccion.OPERADOR_TRACTOR);
                reporte += " - Apuntador:"+a.getColumnValueList().getAsString(Transaccion.CODIGO_APUNTADOR);
                reporte += " - Cabezal: "+a.getColumnValueList().getAsString(Transaccion.CODIGO_CABEZAL);
                reporte += " - Piloto: "+a.getColumnValueList().getAsString(Transaccion.CONDUCTOR_CABEZAL);

                //Barcode
                barcode += format.format(new Date(a.getColumnValueList().getAsLong(Transaccion.FECHA_CORTE)))+"|";
                barcode += a.getColumnValueList().getAsString(Transaccion.FRENTE_CORTE)+"|";
                barcode += a.getColumnValueList().getAsString(Transaccion.FRENTE_ALCE)+"|";
                barcode += a.getColumnValueList().getAsString(Transaccion.ORDEN_QUEMA)+"|";
                barcode += String.format("%03d", Integer.parseInt(a.getColumnValueList().getAsString(Transaccion.ID_FINCA)))+"|";
                barcode += String.format("%04d", Integer.parseInt(a.getColumnValueList().getAsString(Transaccion.ID_CANIAL)))+"|";
                barcode += a.getColumnValueList().getAsString(Transaccion.ID_LOTE)+"|";
                barcode += a.getColumnValueList().getAsString(Transaccion.CLAVE_CORTE)+"|";
                barcode += a.getColumnValueList().getAsString(Transaccion.CODIGO_CARRETA)+"|";
                barcode += a.getColumnValueList().getAsString(Transaccion.CODIGO_VAGON)+"|";
                barcode += a.getColumnValueList().getAsString(Transaccion.CODIGO_COSECHADORA)+"|";
                barcode += a.getColumnValueList().getAsString(Transaccion.OPERADOR_COSECHADORA)+"|";
                barcode += a.getColumnValueList().getAsString(Transaccion.CODIGO_TRACTOR)+"|";
                barcode += a.getColumnValueList().getAsString(Transaccion.OPERADOR_TRACTOR)+"|";
                barcode += a.getColumnValueList().getAsString(Transaccion.CODIGO_APUNTADOR)+"|";
                barcode += a.getColumnValueList().getAsString(Transaccion.CODIGO_CABEZAL)+"|";
                barcode += a.getColumnValueList().getAsString(Transaccion.CONDUCTOR_CABEZAL)+";";

                ListadoTransacciones lt = new ListadoTransacciones(null,subTitle,reporte, barcode);

                BarcodeEncoder qrCodeEncoder = new BarcodeEncoder(barcode, null,
                        Contents.Type.TEXT, BarcodeFormat.PDF_417.toString(), 500);

                Bitmap bitmap;
                try {
                    bitmap = qrCodeEncoder.encodeAsBitmap();
                    lt.setBmp(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }

                resultado.add( lt );
            }
        }

        TransaccionAdapter adapter = new TransaccionAdapter(ourInstance.context, resultado);
        listadoTransacciones.setAdapter(adapter);
        listadoTransacciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            printBluetoothReport((ListadoTransacciones) parent.getItemAtPosition(position));
            }
        });

        editFiltroPorFecha.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DatePickerDialog datePicker = new DatePickerDialog(getActivity(),R.style.AppTheme,new MyDialogDateListenerFactory(editFiltroPorFecha),
                            Calendar.getInstance().get(Calendar.YEAR),
                            Calendar.getInstance().get(Calendar.MONTH),
                            Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                    datePicker.show();
                }
            }
        });

    }

    public BluetoothPrinterManager getBluetoothPrinterManager() {
        if ( bluetoothPrinterManager == null ) {
            bluetoothPrinterManager = new BluetoothPrinterManager(context);
        }
        return bluetoothPrinterManager;
    }

    public void printBluetoothReport ( ListadoTransacciones detailsToShow ) {

        //Verificamos si existe alguna impresora seleccionada.
        if ( !getBluetoothPrinterManager().isDeviceSelected() ) {
            //De no existe algun dispositivo seleccionado se llama a la lista para que el usuario seleccione un dispositivo.
            // 1-) Creamos el dialogo.
            final Dialog dialog = new Dialog(ourInstance.context);
            dialog.setContentView(R.layout.custom_alert_dialog);
            dialog.setTitle("Elegir dispositivo");
            // 2-) Enlazamos los botones y lista.
            Button customAlertDialogButton   = (Button) dialog.findViewById(R.id.customAlertDialogButton);
            ListView customAlertDialogList   = (ListView) dialog.findViewById(R.id.customAlertDialogList);
            //3-) Al presionar el boton ocultamos el dialog.
            customAlertDialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            //4-) Conseguimos la lista de dispositivos.
            ArrayAdapter<String> adapter = getBluetoothPrinterManager().getBluetoothDevices();
            if ( adapter != null ) {
                customAlertDialogList.setAdapter(adapter);
                customAlertDialogList.setItemChecked(0, true);
                customAlertDialogList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                customAlertDialogList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        getBluetoothPrinterManager().setBluetoothDeviceSelected(position);
                    }
                });
                dialog.show();
            }
        }

        // Solo si se selecciono un dispositivo procedemos a imprimir el reporte
        if ( getBluetoothPrinterManager().isDeviceSelected() ) {

            //Creamos el arreglo a imprimir.
            List<PrinterObjectFormat> paramToPrint = new ArrayList<>();

            //try {

                Bitmap bitmap = detailsToShow.getBmp();

                /*File archivo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "barcode.png");
                FileOutputStream fos = new FileOutputStream(archivo);
                bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
                fos.close();
                Bitmap newBitmap = BitmapFactory.decodeFile(archivo.getAbsolutePath());*/
                PrinterObjectFormat pof = new PrinterObjectFormat();
                pof.setBmp(bitmap);
                pof.setObjectType(PRINTER_OBJECT.PICTURE);
                paramToPrint.add(pof);
            /*} catch ( FileNotFoundException f){
                f.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }*/

            //paramToPrint.add(new PrinterObjectFormat(PRINTER_FONT.NORMAL, PRINTER_FONT_SIZE.FOUR, PRINTER_OBJECT.TEXT, true, detailsToShow.getTitulo()));
           // paramToPrint.add(new PrinterObjectFormat(PRINTER_FONT.NORMAL, PRINTER_FONT_SIZE.ONE, PRINTER_OBJECT.TEXT, false, detailsToShow.getSubTitulo()));
            //paramToPrint.add(new PrinterObjectFormat(PRINTER_FONT.NORMAL, PRINTER_FONT_SIZE.ONE, PRINTER_OBJECT.TEXT, false, detailsToShow.getDetalle()));

            //Imprimimos el objeto.
            getBluetoothPrinterManager().print(paramToPrint);

        } else
            AndroidUtils.showAlertMsg(ourInstance.context,"Notificación","Debe seleccionar un dispositivo bluetooth para continuar con la impresion.");
    }
}