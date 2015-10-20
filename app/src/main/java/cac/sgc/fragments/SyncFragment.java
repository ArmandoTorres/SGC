package cac.sgc.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.delacrmi.controller.Entity;

import java.util.ArrayList;
import java.util.List;

import cac.sgc.MainActivity;
import cac.sgc.R;

/**
 * Created by Legal on 19/10/2015.
 */
public class SyncFragment extends Fragment {

    private static SyncFragment ourInstance = null;
    private MainActivity context;
    private View view;
    private List<String> listado;
    private ListView listViewSync;

    public static SyncFragment init(MainActivity context) {
        try {
            if (ourInstance == null) {
                ourInstance = new SyncFragment();
                ourInstance.context = context;
            }
            return ourInstance;
        } catch (Exception e) {
            Log.e("Constructor Form1", "Error en el constructor statico.",e);
            Toast.makeText(context, "Error al ejecutar el constructor de formulario.", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.sync_fragment_layout,container,false);
        initComponents();
        return view;
    }

    private void initComponents() {
        listViewSync = (ListView) view.findViewById(R.id.listViewSync);
        listado = new ArrayList<>();
        for (Class className : ourInstance.context.getEntityManager().getTables()){
            listado.add(className.getSimpleName());
        }
        listViewSync.setAdapter(new ArrayAdapter<>(ourInstance.context,R.layout.sync_fragment_layout,listado));
    }
}