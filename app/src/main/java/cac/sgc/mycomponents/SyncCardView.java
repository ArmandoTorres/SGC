package cac.sgc.mycomponents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.delacrmi.controller.Entity;

import java.util.ArrayList;
import java.util.List;

import cac.sgc.R;

/**
 * Created by Legal on 19/10/2015.
 */
public class SyncCardView extends ArrayAdapter<Entity> {

    private List<Entity> datos;

    public List<Entity> getDatos() {
        if ( datos == null )
            datos = new ArrayList<>();
        return datos;
    }

    public void setDatos(List<Entity> datos) {
        this.datos = datos;
    }

    public SyncCardView(Context context, Entity[] objects) {
        super(context, R.layout.sync_card_view, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
        /*LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.sync_card_view, null);

        TextView nombreTabla = (TextView)item.findViewById(R.id.txtNombreTablaCardView);
        //nombreTabla.setText(this.objects);
        */
    }

    // holder for layout elements
    static class ViewHolderSync{
        TextView titulo;
        Button btn;
    }
}