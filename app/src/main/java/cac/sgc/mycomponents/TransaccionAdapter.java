package cac.sgc.mycomponents;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
import cac.sgc.R;

/**
 * Created by Legal on 20/10/2015.
 */
public class TransaccionAdapter extends ArrayAdapter<ListadoTransacciones> {


    public TransaccionAdapter(Context context, List<ListadoTransacciones> objects) {
        super(context, R.layout.listado_card_view, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.listado_card_view,null);

        TextView subTitle = (TextView) item.findViewById(R.id.reportSubTitle);
        subTitle.setText(getItem(position).getSubTitulo());
        return item;
    }
}
