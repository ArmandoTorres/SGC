package cac.sgc.tools;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import cac.sgc.R;

/**
 * Created by miguel on 28/10/15.
 */
public class SyncAdapter extends RecyclerView.Adapter<ViewSyncHolder> {
    private List<Vector<String>> dataList;
    private Map<String,View> viewMap = new HashMap<String,View>();

    public SyncAdapter(List<Vector<String>> dataList) {
        this.dataList = dataList;
    }

    public View getViewOfTable(String tableName){
        return viewMap.get(tableName);
    }

    @Override
    public ViewSyncHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sync_card_view,null);
        ViewSyncHolder vsh = new ViewSyncHolder(view);
        return vsh;
    }

    @Override
    public void onBindViewHolder(ViewSyncHolder holder, int position) {
        Vector<String> data = dataList.get(position);
        viewMap.put(data.get(1), holder.getView());
        holder.bindTableSync(data);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
