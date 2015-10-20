package cac.sgc.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.delacrmi.controller.Entity;
import com.delacrmi.controller.EntityManager;
import cac.sgc.MainActivity;
import cac.sgc.R;
import cac.sgc.entities.Caniales;
import cac.sgc.entities.Fincas;
import cac.sgc.entities.Frentes;
import cac.sgc.entities.Lotes;
import cac.sgc.mycomponents.MyOnFocusListenerFactory;

/**
 * Created by Legal on 04/10/2015.
 */
public class Formulario1 extends Fragment {

    private View view;
    private EditText fteCorte, fteAlce, finca, canial, lote, txtDescFteCorte, txtDescFteAlce, txtDescFinca, txtDescCanial, txtDescLote;
    private static Formulario1 ourInstance = null;
    private MainActivity context;
    private RelativeLayout layout;

    public Formulario1 ( ) {}

    public EditText getFteCorte() {
        return fteCorte;
    }

    public EditText getFteAlce() {
        return fteAlce;
    }

    public EditText getFinca() {
        return finca;
    }

    public EditText getCanial() {
        return canial;
    }

    public EditText getLote() {
        return lote;
    }

    public static Formulario1 init(MainActivity context) {
        try {
            if (ourInstance == null) {
                ourInstance = new Formulario1();
                ourInstance.context = context;
            }
            return ourInstance;
        } catch (Exception e) {
            Log.e("Constructor Form1", e.getMessage());
            Toast.makeText(context, "Error al ejecutar el constructor de formulario.",Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.formulario1,container,false);
        initComponents();
        initMetodos();
        return view;
    }

    private void initComponents(){
        fteCorte = (EditText) view.findViewById(R.id.editTextFrenteCorte);
        txtDescFteCorte = (EditText) view.findViewById(R.id.txtDescFteCorte);
        fteAlce = (EditText) view.findViewById(R.id.editTextFteAlce);
        txtDescFteAlce = (EditText) view.findViewById(R.id.txtDescFteAlce);
        finca = (EditText) view.findViewById(R.id.editTextFinca);
        txtDescFinca = (EditText) view.findViewById(R.id.txtDescFinca);
        canial = (EditText) view.findViewById(R.id.editTextCanial);
        txtDescCanial = (EditText) view.findViewById(R.id.txtDescCanial);
        lote = (EditText) view.findViewById(R.id.editTextlote);
        txtDescLote = (EditText) view.findViewById(R.id.txtDescLote);
        layout = (RelativeLayout) view.findViewById(R.id.formulario1);
    }

    private void initMetodos() {
        fteCorte.setOnFocusChangeListener((new MyOnFocusListenerFactory(txtDescFteCorte,
                ourInstance.context.getEntityManager(), Frentes.class, Frentes.DESCRIPCION, Frentes.ID_FRENTE)).setTitle("Frente"));

        fteAlce.setOnFocusChangeListener((new MyOnFocusListenerFactory(txtDescFteAlce,
                ourInstance.context.getEntityManager(), Frentes.class,Frentes.DESCRIPCION,Frentes.ID_FRENTE)).setTitle("Frente"));

        finca.setOnFocusChangeListener((new MyOnFocusListenerFactory(txtDescFinca,
                ourInstance.context.getEntityManager(),Fincas.class,Fincas.DESCRIPCION,Fincas.FINCA)).setTitle("Finca"));

        canial.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText field = (EditText) v;
                EntityManager entityManager = ourInstance.context.getEntityManager();
                if (!hasFocus) {
                    if ( field.getText().toString().trim().equals("") || field.getText().toString().trim().length() == 0 || field.getText().toString().trim().isEmpty() ) {
                        field.setError("El campo "+field.getHint()+" es Requerido.");
                    }
                    if ( ourInstance.context.getEntityManager() != null ){
                        Entity result = entityManager.findOnce(Caniales.class, "*",
                                Caniales.ID_FINCA+" = ? and "+ Caniales.CANIAL+" = ? ",
                                new String[]{finca.getText().toString(), canial.getText().toString()});
                        if ( result != null &&  result.getColumnValueList().size() > 0){
                            txtDescCanial.setText(result.getColumnValueList().getAsString(Caniales.DESCRIPCION));
                        } else {
                            txtDescCanial.setText("");
                            field.setError("El "+field.getHint()+" no se encuentra registrado en la base de datos.");
                        }
                    }
                }
            }
        });

        lote.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText field = (EditText) v;
                EntityManager entityManager = ourInstance.context.getEntityManager();
                if (!hasFocus) {
                    if ( field.getText().toString().trim().equals("") || field.getText().toString().trim().length() == 0 || field.getText().toString().trim().isEmpty() ) {
                        field.setError("El campo "+field.getHint()+" es Requerido.");
                    }
                    if ( ourInstance.context.getEntityManager() != null ){
                        Entity result = entityManager.findOnce(Lotes.class, "*",
                                Lotes.ID_FINCA+" = ? and "+ Lotes.ID_CANIAL+" = ? and "+Lotes.ID_LOTE+" = ?",
                                new String[]{finca.getText().toString(), canial.getText().toString(), lote.getText().toString()});
                        if ( result != null &&  result.getColumnValueList().size() > 0){
                            txtDescLote.setText(result.getColumnValueList().getAsString(Lotes.DESCRIPCION));
                        } else {
                            txtDescLote.setText("");
                            field.setError("El "+field.getHint()+" no se encuentra registrado en la base de datos.");
                        }
                    }
                }
            }
        });
    }

    public boolean validateForm() {
        if ( layout != null ){
            for (View a : layout.getFocusables(RelativeLayout.FOCUS_BACKWARD)){
                if ( a instanceof AppCompatEditText ) {
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

    public void clearForm() {
        fteCorte.setText("");
        txtDescFteCorte.setText("");
        fteAlce.setText("");
        txtDescFteAlce.setText("");
        finca.setText("");
        txtDescFinca.setText("");
        canial.setText("");
        txtDescCanial.setText("");
        lote.setText("");
        txtDescLote.setText("");
    }
}