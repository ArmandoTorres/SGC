package cac.sgc.fragments;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.delacrmi.controller.Entity;
import com.delacrmi.controller.EntityManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cac.sgc.MainActivity;
import cac.sgc.R;
import cac.sgc.entities.Empleados;
import cac.sgc.entities.Vehiculos;
import cac.sgc.mycomponents.MyDialogDateListenerFactory;
import cac.sgc.mycomponents.MyOnFocusListenerFactory;

/**
 * Created by Legal on 04/10/2015.
 */
public class Formulario2 extends Fragment {

    private View view;
    private MainActivity context;
    private EditText fechaCorte, ordenQuema, editTextConductorCabezal, txtDescConductorCabezal;
    private static Formulario2 ourInstance = null;
    private Spinner listaClaveCorte, listaCabezales;
    private RelativeLayout layout;

    public Formulario2() {}

    public String getFechaCorte() {
        try {
            SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
            Date date = formater.parse(fechaCorte.getText().toString());
            return Long.toString(date.getTime());
        } catch (ParseException e) {
            Log.e("Error", "Al convertir la fecha de corte", e);
            Toast.makeText(ourInstance.context,"Error al convertir la fecha de corte.", Toast.LENGTH_SHORT).show();
            return null;
        }

    }

    public EditText getOrdenQuema() {
        return ordenQuema;
    }

    public EditText getEditTextConductorCabezal() {
        return editTextConductorCabezal;
    }

    public EditText getTxtDescConductorCabezal() {
        return txtDescConductorCabezal;
    }

    public Spinner getListaCabezales() {
        return listaCabezales;
    }

    public Spinner getListaClaveCorte() {
        return listaClaveCorte;
    }

    /**
     * Constructor que recive la actidad principal para siempre tener el contexto principal.
     *
     * @param context MainActivity que es el contexto principal de la aplicacion.
     * */
    public static Formulario2 init(MainActivity context) {
        if ( ourInstance == null ) {
            ourInstance = new Formulario2();
            ourInstance.context = context;
        }
        return ourInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.formulario2,container,false);
        try {
            initComponents();
        } catch (Exception ex) {
            Log.e("Error.","Error en formulario 2.",ex);
            Toast.makeText(this.context,"Ocurrio un error en este formulario.",Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    /**
     * Metodo utilizado para iniciar los componentes, enlazando las variables
     * con el xml y agregar los metodos que le dan funcionalidad a los
     * componentes.
     * */
    private void initComponents() throws Exception {
        ordenQuema = (EditText) view.findViewById(R.id.editTextOrdenQuema);
        fechaCorte = (EditText) view.findViewById(R.id.editTextFechaCorte);
        editTextConductorCabezal = (EditText) view.findViewById(R.id.editTextConductorCabezal);
        txtDescConductorCabezal = (EditText) view.findViewById(R.id.txtDescConductorCabezal);
        layout = (RelativeLayout) view.findViewById(R.id.formulario2);
        listaCabezales = (Spinner) view.findViewById(R.id.listaCabezales);
        listaClaveCorte = (Spinner) view.findViewById(R.id.listaClaveCorte);

        fechaCorte.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DatePickerDialog datePicker = new DatePickerDialog(getActivity(),R.style.AppTheme,new MyDialogDateListenerFactory(fechaCorte),
                            Calendar.getInstance().get(Calendar.YEAR),
                            Calendar.getInstance().get(Calendar.MONTH),
                            Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                    datePicker.show();
                } else {
                    validateFields(fechaCorte);
                }
            }
        });

        ordenQuema.setOnFocusChangeListener(new MyOnFocusListenerFactory(null));
        editTextConductorCabezal.setOnFocusChangeListener((new MyOnFocusListenerFactory(txtDescConductorCabezal)).setTitle("Conductor"));

        fechaCorte.setText(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + Calendar.getInstance().get(Calendar.MONTH) + "/" + Calendar.getInstance().get(Calendar.YEAR));

        editTextConductorCabezal.setOnFocusChangeListener((new MyOnFocusListenerFactory(txtDescConductorCabezal,
                ourInstance.context.getEntityManager(), Empleados.class, Empleados.NOMBRE, Empleados.ID_EMPLEADO)).setTitle("Empleado"));


        listaCabezales.setAdapter(findCodigosVehiculos(Vehiculos.class,"B"));

        //Llenamos la lista en pantalla con las claves de corte
        String [] clavesCorte = this.getResources().getStringArray(R.array.clave_corte);
        List<String> listaClavesCorte = Arrays.asList(clavesCorte);
        if (listaClavesCorte != null && !listaClavesCorte.isEmpty() ){
            ArrayAdapter<String> adapterClaveCorte = new ArrayAdapter<>(this.context, android.R.layout.simple_selectable_list_item,listaClavesCorte);
            listaClaveCorte.setAdapter(adapterClaveCorte);
        }

    }

    /**
     * Metodo utilizado para validar si un campo esta vacio o no. En caso de estar vacio devolvera un false.
     * @param field Campo editText a validar.
     * @return Boolean que indica true si el campo es valido y false si no lo es.
     * */
    private Boolean validateFields(EditText field){
        if (field != null){
            if ( field.getText().toString().trim().equals("") || field.getText().toString().trim().length() == 0 || field.getText().toString().trim().isEmpty() ) {
                field.setError("Campo Requerido");
                return false;
            }
        }
        return true;
    }

    public void clearForm(){
        ordenQuema.setText("");
        fechaCorte.setText("");
        editTextConductorCabezal.setText("");
        txtDescConductorCabezal.setText("");
    }

    public boolean validateForm() {
        if ( layout != null ){
            for (View a : layout.getFocusables(RelativeLayout.FOCUS_BACKWARD)){
                if ( a instanceof AppCompatEditText) {
                    EditText editText = (EditText) a;
                    a.getOnFocusChangeListener().onFocusChange(editText,false);
                    if ( editText.getError() != null ){
                        Toast.makeText(ourInstance.context, "Debe indicar la informacion para el campo " + editText.getHint(), Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private ArrayAdapter<String> findCodigosVehiculos(Class entidad, String codigoGrupo ) {

        EntityManager entityManager = ourInstance.context.getEntityManager();
        List<Entity> entidades =  entityManager.find(entidad, "*", Vehiculos.CODIGO_GRUPO + " = '" + codigoGrupo + "' and " + Vehiculos.STATUS + " = 1", null);
        List<String> listado = new ArrayList<>();
        for ( Entity a : entidades ){
            String descripcion = a.getColumnValueList().getAsString(Vehiculos.CODIGO_GRUPO);
            descripcion += String.format("%02d", Integer.parseInt(a.getColumnValueList().getAsString(Vehiculos.CODIGO_SUBGRUPO)));
            descripcion += String.format("%03d", Integer.parseInt(a.getColumnValueList().getAsString(Vehiculos.CODIGO_VEHICULO)));
            listado.add(descripcion);
        }

        if ( listado != null && !listado.isEmpty() ) {
            return new ArrayAdapter<>(this.context, android.R.layout.simple_selectable_list_item, listado);
        }else
            return new ArrayAdapter<>(this.context, android.R.layout.simple_selectable_list_item, new ArrayList<String>());
    }


}