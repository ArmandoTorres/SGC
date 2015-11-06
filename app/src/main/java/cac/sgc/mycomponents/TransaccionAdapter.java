package cac.sgc.mycomponents;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
            holder.bmp      = (ImageView)item.findViewById(R.id.reportBarcodeImage);
            item.setTag(holder);
        } else
            holder = (TransaccionViewHolder) item.getTag();

        holder.subTitle.setText(getItem(position).getSubTitulo());
        holder.details.setText(getItem(position).getDetalle());
        Bitmap bmp = getItem(position).getBmp();
        if ( bmp != null )
            holder.bmp.setImageBitmap(bmp);

        return item;
    }

    static class TransaccionViewHolder{
        TextView subTitle;
        TextView details;
        ImageView bmp;
    }
}
