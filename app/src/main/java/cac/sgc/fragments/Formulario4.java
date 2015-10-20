package cac.sgc.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import cac.sgc.MainActivity;
import cac.sgc.R;
import cac.sgc.entities.Empleados;
import cac.sgc.mycomponents.MyOnFocusListenerFactory;

/**
 * Created by Legal on 04/10/2015.
 */
public class Formulario4 extends Fragment {

    private View view;
    private static Formulario4 ourInstance = null;
    private MainActivity context;

    private EditText editTextCodigoApuntador, txtDescCodigoApuntador;
    private Spinner listaCodigoVagones;
    private RelativeLayout layout;

    public Formulario4() {}

    public EditText getEditTextCodigoApuntador() {
        return editTextCodigoApuntador;
    }

    public Spinner getListaCodigoVagones() {
        return listaCodigoVagones;
    }

    public static Formulario4 init(MainActivity context) {
        if ( ourInstance == null ) {
            ourInstance = new Formulario4();
            ourInstance.context = context;
        }
        return ourInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.formulario4,container,false);
        editTextCodigoApuntador = (EditText) view.findViewById(R.id.editTextCodigoApuntador);
        listaCodigoVagones      = (Spinner) view.findViewById(R.id.listaCodigoVagones);
        txtDescCodigoApuntador  = (EditText) view.findViewById(R.id.txtDescCodigoApuntador);
        layout = (RelativeLayout) view.findViewById(R.id.formulario4);
        editTextCodigoApuntador.setOnFocusChangeListener((new MyOnFocusListenerFactory(txtDescCodigoApuntador,
                ourInstance.context.getEntityManager(), Empleados.class, Empleados.NOMBRE, Empleados.ID_EMPLEADO)).setTitle("Empleado"));

        return view;
    }

    public void clearForm() {
        editTextCodigoApuntador.setText("");
        txtDescCodigoApuntador.setText("");
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
}