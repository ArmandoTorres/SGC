package cac.sgc.mycomponents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.delacrmi.controller.Entity;

import cac.sgc.R;

/**
 * Created by Legal on 19/10/2015.
 */
public class SyncCardView extends ArrayAdapter<Entity> {

    public SyncCardView(Context context, Entity[] objects) {
        super(context, R.layout.sync_card_view, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.sync_card_view, null);

        TextView nombreTabla = (TextView)item.findViewById(R.id.txtNombreTablaCardView);
        nombreTabla.setText(this.objects);
    }
}