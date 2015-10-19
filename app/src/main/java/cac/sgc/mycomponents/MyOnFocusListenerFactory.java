package cac.sgc.mycomponents;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.delacrmi.controller.Entity;
import com.delacrmi.controller.EntityManager;
import com.lowagie.text.pdf.hyphenation.TernaryTree;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cac.sgc.entities.Frentes;

/**
 * Created by Legal on 13/10/2015.
 */
public class MyOnFocusListenerFactory implements View.OnFocusChangeListener {

    private View target;
    private String title;
    private EntityManager entityManager;
    private Class entity;
    private String campoDescripcion, campoFiltro;

    public MyOnFocusListenerFactory ( View target ) {
        this.target = target;
    }

    public MyOnFocusListenerFactory ( View target, EntityManager entityManager, Class entity, String campoDescripcion, String campoFiltro ) {
        if ( target == null || entityManager == null || entity == null || campoDescripcion == null || campoFiltro == null || campoFiltro.equals("")) {
            throw new NullPointerException("Todos los parametros del constructor son obligatorios.");
        }
        this.target = target;
        this.entityManager = entityManager;
        this.entity = entity;
        this.campoDescripcion = campoDescripcion;
        this.campoFiltro = campoFiltro;
    }

    public MyOnFocusListenerFactory setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        EditText campo = (EditText) v;

        if (!hasFocus) {
            if ( !validateFields(campo) ) {
                if (this.target != null && target instanceof TextView )
                    ((TextView) target).setText("");
            }
        }
    }

    private Boolean validateFields(EditText field) {
        if (field != null) {
            if ( field.getText().toString().trim().equals("") || field.getText().toString().trim().length() == 0 || field.getText().toString().trim().isEmpty() ) {
                field.setError("El campo "+field.getHint()+" es Requerido.");
                return false;
            }
            try {
                if (entityManager != null) {
                    //Log.e("Entramos.", "Valor del campo: " + field.getText().toString());
                    Entity result = entityManager.findOnce(entity, "*", campoFiltro + " = ?", new String[]{field.getText().toString()});

                    //Log.e("Salimos.", "Valor del result: " + result);
                    if (result != null && result.getColumnValueList().size() > 0) {
                        ((TextView) target).setText(result.getColumnValueList().getAsString(campoDescripcion));
                    } else {
                        ((TextView) target).setText("");
                        field.setError("El " + field.getHint() + " no se encuentra registrado en la base de datos.");
                        return false;
                    }
                }
            } catch ( Exception e ) {
                Log.e("Error","Error al buscar el resultado: "+e.getMessage());
                return false;
            }
        }
        return true ;
    }
}