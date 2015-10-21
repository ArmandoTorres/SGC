package cac.sgc.mycomponents;

import android.content.Context;
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

        View item = convertView;
        TransaccionViewHolder holder;

        if ( item == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            item = inflater.inflate(R.layout.listado_card_view, null);

            holder = new TransaccionViewHolder();
            holder.subTitle = (TextView) item.findViewById(R.id.reportSubTitle);
            holder.details  = (TextView) item.findViewById(R.id.reportDetails);

            item.setTag(holder);
        } else
            holder = (TransaccionViewHolder) item.getTag();

        holder.subTitle.setText(getItem(position).getSubTitulo());
        holder.details.setText(getItem(position).getDetalle());

        return item;
    }

    static class TransaccionViewHolder{
        TextView subTitle;
        TextView details;
    }
}
