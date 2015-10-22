package cac.sgc.mycomponents;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;
import java.util.Vector;

import cac.sgc.R;
import cac.sgc.fragments.SyncFragment;

/**
 * Created by Legal on 19/10/2015.
 */
public class SyncCardView extends ArrayAdapter<Vector<String>> {

    //private AppCompatActivity context;
    private View.OnClickListener onClickListener;
    private ViewHolderSync viewHolderSync;


    public SyncCardView(AppCompatActivity context, List<Vector<String>> objects) {
        super(context, R.layout.sync_card_view, objects);
        //this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        //return super.getView(position, view, parent);


        if(view == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.sync_card_view, null);
            //view = context.getLayoutInflater().inflate(R.layout.sync_card_view,parent,false);

            viewHolderSync = new ViewHolderSync();
            viewHolderSync.title = (TextView)view.findViewById(R.id.tv_sync_name);
            viewHolderSync.btn_sync = (ImageButton)view.findViewById(R.id.btn_sync);

            viewHolderSync.pgb_sync = (ProgressBar) view.findViewById(R.id.pgb_sync);
            viewHolderSync.pgb_sync.getLayoutParams().height= 10;

            view.setTag(viewHolderSync);
            viewHolderSync.btn_sync.setTag(viewHolderSync);

        }else
            viewHolderSync = (ViewHolderSync)view.getTag();

        Vector<String> item = this.getItem(position);
        events();

        viewHolderSync.title.setText(item.get(0));
        viewHolderSync.tableName = item.get(1);
        viewHolderSync.btn_sync.setOnClickListener(onClickListener);

        return view;
    }

    /*
    *==============================================================================================
    * This method to initialize all the event
    * =============================================================================================
    */

    public void events(){
        onClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                JSONObject obj = SyncFragment.getInstance().getJSONSelect(
                        ((ViewHolderSync)v.getTag()).tableName,null,null);

                Toast.makeText(getContext(),obj.toString(),Toast.LENGTH_SHORT).show();
            }
        };
    }

    // holder for layout elements
    static class ViewHolderSync{
        TextView title;
        ImageButton btn_sync;
        ProgressBar pgb_sync;
        String tableName;
    }
}