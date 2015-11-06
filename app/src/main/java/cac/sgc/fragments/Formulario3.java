package cac.sgc.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.delacrmi.controller.Entity;
import com.delacrmi.controller.EntityManager;

import java.util.ArrayList;
import java.util.List;

import cac.sgc.MainActivity;
import cac.sgc.R;
import cac.sgc.entities.Empleados;
import cac.sgc.entities.Vehiculos;
import cac.sgc.mycomponents.MyOnFocusListenerFactory;

/**
 * Created by Legal on 04/10/2015.
 */
public class Formulario3 extends Fragment {

    private View view;
    private static Formulario3 ourInstance = null;
    private MainActivity context;
    private RelativeLayout layout;

    // Listados en pantalla.
    private Spinner listaCodigoCarreta, listaCodigoCosechadora, listaCodigoTractor;
    //EditText en pantalla.
    private EditText editTextConductorCosechadora, editTextConductorTractor, txtDescConductorCosechadora, txtDescConductorTractor;

    public Formulario3() {}

    public Spinner getListaCodigoCarreta() {
        return listaCodigoCarreta;
    }

    public Spinner getListaCodigoCosechadora() {
        return listaCodigoCosechadora;
    }

    public Spinner getListaCodigoTractor() {
        return listaCodigoTractor;
    }

    public EditText getEditTextConductorCosechadora() {
        return editTextConductorCosechadora;
    }

    public EditText getEditTextConductorTractor() {
        return editTextConductorTractor;
    }

    public static Formulario3 init(MainActivity context) {
        if ( ourInstance == null ) {
            ourInstance = new Formulario3();
            ourInstance.context = context;
        }
        return ourInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.formulario3,container,false);

        //Iniciar componentes.
        try {
            initComponents();
        } catch (Exception ex) {
            Log.e("Error","Error al cargar los componentes Formulario 3",ex);
            Toast.makeText(this.context,"Error al cargar el formulario.",Toast.LENGTH_SHORT).show();
        }

        return view;
    }


    public void initComponents() throws Exception {

        //Listas
        listaCodigoCarreta = (Spinner) view.findViewById(R.id.listaCodigoCarreta);
        listaCodigoCosechadora = (Spinner) view.findViewById(R.id.listaCodigoCosechadora);
        listaCodigoTractor = (Spinner) view.findViewById(R.id.listaCodigoTractor);

        //EditText
        editTextConductorCosechadora = (EditText) view.findViewById(R.id.editTextConductorCosechadora);
        editTextConductorTractor     = (EditText) view.findViewById(R.id.editTextConductorTractor);
        txtDescConductorCosechadora  = (EditText) view.findViewById(R.id.txtDescConductorCosechadora);
        txtDescConductorTractor      = (EditText) view.findViewById(R.id.txtDescConductorTractor);
        layout = (RelativeLayout) view.findViewById(R.id.formulario3);

        editTextConductorCosechadora.setOnFocusChangeListener((new MyOnFocusListenerFactory(txtDescConductorCosechadora,
                ourInstance.context.getEntityManager(), Empleados.class, Empleados.NOMBRE, Empleados.ID_EMPLEADO)).setTitle("Empleado"));

        editTextConductorTractor.setOnFocusChangeListener((new MyOnFocusListenerFactory(txtDescConductorTractor,
                ourInstance.context.getEntityManager(), Empleados.class, Empleados.NOMBRE, Empleados.ID_EMPLEADO)).setTitle("Empleado"));

        listaCodigoCarreta.setAdapter(findCodigosVehiculos(Vehiculos.class, "C"));
        listaCodigoCosechadora.setAdapter(findCodigosVehiculos(Vehiculos.class,"D"));
        listaCodigoTractor.setAdapter(findCodigosVehiculos(Vehiculos.class,"A"));
    }

    public void clearForm() {
        editTextConductorCosechadora.setText("");
        editTextConductorTractor.setText("");
        txtDescConductorCosechadora.setText("");
        txtDescConductorTractor.setText("");
    }

    public boolean validateForm() {
        if ( layout != null ){
            for (View a : layout.getFocusables(RelativeLayout.FOCUS_BACKWARD)){
                if ( a instanceof AppCompatEditText) {
                    EditText editText = (EditText) a;
                    a.getOnFocusChangeListener().onFocusChange(editText,false);
                    if ( editText.getError() != null ){
                        Toast.makeText(ourInstance.context,"Debe indicar la informacion para el campo "+editText.getHint(), Toast.LENGTH_SHORT).show();
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