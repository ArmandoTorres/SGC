package cac.sgc.fragments;

import android.app.Fragment;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.delacrmi.connection.SocketConnect;
import com.delacrmi.controller.Entity;
import com.delacrmi.controller.EntityManager;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import cac.sgc.R;
import cac.sgc.entities.Empresas;
import cac.sgc.mycomponents.SyncCardView;
import io.socket.client.Socket;

/**
 * Created by miguel on 19/10/15.
 */
public class SyncFragment extends Fragment {
    private static  SyncFragment outInstance = null;
    private String uri;
    private SocketConnect connect;
    private AppCompatActivity context;
    private EntityManager entityManager;

    private View view;
    private ListView listViewSync;
    private List<Vector<String>> listado;
    private SyncCardView adapter;

    public static SyncFragment init(AppCompatActivity context,EntityManager entityManager,String uri){
        if(outInstance == null){
            outInstance = new SyncFragment();
            outInstance.context = context;
            outInstance.entityManager = entityManager;
            outInstance.uri = uri;
            outInstance.socketInit();
        }
        return outInstance;
    }

    public SyncFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.sync_fragment_layout,container,false);
        initComponents();
        return view;
    }

    private void initComponents() {
        listViewSync = (ListView) view.findViewById(R.id.listViewSync);

        listado = new ArrayList<Vector<String>>();
        for (String name : entityManager.getTablesNames()){

            Vector<String> values = new Vector<String>();
            values.add(name);
            values.add(entityManager.getEntityNicName(name));

            listado.add(values);
        }

        adapter = new SyncCardView(context,listado);

        listViewSync.setAdapter(adapter);
    }

    private void socketInit(){
        outInstance.connect = new SocketConnect(outInstance.context,outInstance.uri){

            @Override
            public void onSynchronizeClient(Object... args) {
                try{
                    JSONObject obj = (JSONObject) args[0];
                    Class className = entityManager.getClassByName(obj.getString("tableName"));
                    JSONArray rows = obj.getJSONArray("result");

                    entityManager.delete(className, null, null);

                    for (int index=0;index<rows.length(); index++){
                        JSONObject row = (JSONObject) rows.getJSONObject(index);
                        ContentValues columns = new ContentValues();

                        Iterator iteratorKeys = row.keys();
                        while (iteratorKeys.hasNext()){
                            String key = iteratorKeys.next().toString();
                            String value = row.getString(key);

                            if(value != null && !value.equals("") && !value.equals(" "))
                                columns.put(key.toLowerCase(), value);
                        }

                        Entity ent = entityManager.save(className,columns);
                        Log.i(ent.getName(), ent.getColumnValueList().getAsString(ent.getPrimaryKey()));
                        Log.e(obj.getString("tableName"),row.toString());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Empresas entity = (Empresas)entityManager.findOnce(Empresas.class,"*","id_empresa = ?",new String[]{"30"});
                Log.i(entity.getName(),entity.getColumnValueList().
                        getAsString(entity.getPrimaryKey())+" "+entity.getColumnValueList().
                        getAsString(Empresas.DIRECCION_COMERCIAL));
            }

            @Override
            public void onSynchronizeServer(Object... args) {

            }

            @Override
            public void disconnect() {
                super.disconnect();
                outInstance.connect = null;
            }
        };
    }

    public Socket getSocket(){
        if(outInstance != null)
            return outInstance.connect.getSocket();
        return null;
    }

    public SocketConnect getConnect(){
        return outInstance.connect;
    }

    public JSONObject getJSONSelect(String tableName,String where, JSONArray whereValues){
        JSONArray array = new JSONArray();
        JSONObject obj  = new JSONObject();

        Iterator iterator = entityManager.initInstance(entityManager.getClassByName(tableName)).iterator();
        while (iterator.hasNext()){
            String columnName = ((Map.Entry)iterator.next()).getKey().toString();
            if(!columnName.equals(tableName + "_id"))
                array.put(columnName);
        }

        try {
            obj.put("table",tableName);
            obj.put("columns",array);

            if(where != null && !where.equals("") && !where.equals(" "))
                obj.put("where",where);

            if(whereValues != null && !whereValues.equals("") && !whereValues.equals(" "))
                obj.put("whereValue",whereValues);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  obj;
    }

}
