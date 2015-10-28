package cac.sgc.fragments;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.atorres.BluetoothPrinterManager;
import com.delacrmi.controller.Entity;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BarcodePDF417;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import cac.sgc.mycomponents.TransaccionAdapter;

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
    private int vYear, vMonthOfYear,  vDayOfMonth;
    private BluetoothPrinterManager btm;

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
        editFiltroPorFecha = (EditText) view.findViewById(R.id.editFiltroPorFecha);
        generarReporte = (ImageButton) view.findViewById(R.id.generarReporte);
        listadoTransacciones = (ListView) view.findViewById(R.id.listViewTransacciones);

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

                resultado.add( new ListadoTransacciones(null,subTitle,reporte, barcode) );
            }
        }

        //Log.e("Resultado:", "Valor: " + resultado.get(0).getSubTitulo());
        TransaccionAdapter adapter = new TransaccionAdapter(ourInstance.context, resultado);
        listadoTransacciones.setAdapter(adapter);
        listadoTransacciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = ((ListadoTransacciones) parent.getItemAtPosition(position)).getSubTitulo();
                Toast.makeText(ourInstance.context, "Item seleccionado: " + selectedOption, Toast.LENGTH_SHORT).show();
                crearReportePDF((ListadoTransacciones) parent.getItemAtPosition(position));
            }
        });

        vYear = Calendar.getInstance().get(Calendar.YEAR);
        vMonthOfYear = Calendar.getInstance().get(Calendar.MONTH);
        vDayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        editFiltroPorFecha.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDialogDatePicker();
                }
            }
        });

        generarReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btm = new BluetoothPrinterManager(ourInstance.context);
                btm.initComunication();
            }
        });

    }

    /**
     * Metodo utilizado para mostrar un dialog con el calendario.
     * */
    private void showDialogDatePicker() {

        DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                vYear = year;
                vMonthOfYear = monthOfYear;
                vDayOfMonth = dayOfMonth;
                editFiltroPorFecha.setText(vDayOfMonth + "/" + vMonthOfYear + "/" + vYear);
            }
        };

        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), R.style.AppTheme,dateListener,vYear,vMonthOfYear,vDayOfMonth);
        datePicker.show();
   }

   private void crearReportePDF( ListadoTransacciones detailsToShow ) {
       //Creamos el documento
       Document documento = new Document();

       try {
           String fileName = String.format("%02d", vDayOfMonth) +
                   String.format("%02d", vMonthOfYear) +
                   vYear + Calendar.getInstance().get(Calendar.HOUR) +
                   Calendar.getInstance().get(Calendar.MINUTE) +
                   Calendar.getInstance().get(Calendar.SECOND) + ".pdf";
           //Creamos el fichero con el nombre;
           if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
               File ruta = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "SGC_PDF");
               if (ruta != null) {
                   ruta.mkdirs();
                   //Creamos el archivo
                   File fichero = new File(ruta, fileName);
                   // creamos el flujo de datos
                   FileOutputStream ficheroPDF = new FileOutputStream(fichero.getAbsolutePath());

                   //Asociamos el flujo que acabamos de crear al documentos.
                   PdfWriter.getInstance(documento, ficheroPDF);

                   //Abrimos el documento.
                   documento.open();

                   //Codigo de Barras
                   BarcodePDF417 barcode = new BarcodePDF417();
                   barcode.setText(detailsToShow.getBarcode());
                   Image img = barcode.getImage();
                   img.scalePercent(100, 50 * barcode.getYHeight());
                   documento.add(img);

                   Bitmap bmp = BitmapFactory.decodeByteArray(img.getOriginalData(),0,img.getOriginalData().length);

                   //Subtitilo del reporte
                   Paragraph p1 = new Paragraph(detailsToShow.getSubTitulo(), FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD, BaseColor.BLACK));
                   p1.setAlignment(Paragraph.ALIGN_CENTER);
                   documento.add(p1);

                   documento.add(new LineSeparator(0.5f, 100, null, 0, -5));

                   //Detalle del reporte
                   Paragraph p2 = new Paragraph(detailsToShow.getDetalle(), FontFactory.getFont(FontFactory.HELVETICA, 14, Font.NORMAL, BaseColor.BLACK));
                   p2.setAlignment(Paragraph.ALIGN_JUSTIFIED);
                   documento.add(p2);

               }
           }
       } catch (Exception e){
           Log.e("Error","Error al crear el reporte PDF",e);
           Toast.makeText(ourInstance.context,"Ocurrio un error al crear el PDF",Toast.LENGTH_SHORT).show();
       } finally {
           documento.close();
       }
   }
}