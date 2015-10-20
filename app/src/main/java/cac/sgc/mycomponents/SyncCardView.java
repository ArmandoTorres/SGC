package cac.sgc.mycomponents;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;
import java.util.Vector;

import cac.sgc.R;

/**
 * Created by Legal on 19/10/2015.
 */
public class SyncCardView extends ArrayAdapter<Vector<String>> {

    //private AppCompatActivity context;

    public SyncCardView(AppCompatActivity context, List<Vector<String>>objects) {
        super(context, R.layout.sync_card_view, objects);
        //this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        //return super.getView(position, view, parent);

        ViewHolderSync holder;

        if(view == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.sync_card_view, null);
            //view = context.getLayoutInflater().inflate(R.layout.sync_card_view,parent,false);

            holder = new ViewHolderSync();
            holder.title = (TextView)view.findViewById(R.id.tv_sync_name);
            holder.btn_sync = (ImageButton)view.findViewById(R.id.btn_sync);
            holder.pgb_sync = (ProgressBar) view.findViewById(R.id.pgb_sync);

            view.setTag(holder);
        }else
            holder = (ViewHolderSync)view.getTag();

        Vector<String> item = this.getItem(position);

        holder.title.setText(item.get(0));
        holder.tableName = item.get(1);

        return view;
    }

    // holder for layout elements
    static class ViewHolderSync{
        TextView title;
        ImageButton btn_sync;
        ProgressBar pgb_sync;
        String tableName;
    }
}