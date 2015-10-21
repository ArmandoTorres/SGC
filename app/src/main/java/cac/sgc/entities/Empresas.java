package cac.sgc.entities;

import com.delacrmi.controller.Entity;

/**
 * Created by miguel on 10/10/15.
 */
public class Empresas extends Entity {

    public static String ID_EMPRESA = "id_empresa";
    public static String DIRECCION_COMERCIAL = "direccion_comercial";
    public static String TABLE_NAME = "pg_empresa";

    private boolean selected = false;

    public Empresas(){

    }

    @Override
    public Empresas entityConfig() {
        setName(Empresas.TABLE_NAME);
        addColumn(ID_EMPRESA,"integer");
        addColumn(DIRECCION_COMERCIAL,"text");
        return this;
    }

    public void setSelected(boolean selected){
        this.selected = selected;
    }
    public boolean isselected(){
        return selected;
    }
}