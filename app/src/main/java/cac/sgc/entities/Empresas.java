package cac.sgc.entities;

import com.delacrmi.controller.Entity;

/**
 * Created by miguel on 10/10/15.
 */
public class Empresas extends Entity {

    public static String ID_EMPRESA = "id_empresa";
    public static String DIRECCION_COMERCIAL = "direccion_comercial";

    private boolean selected = false;

    public Empresas(){

    }

    @Override
    public Empresas entityConfig() {
        setName("pg_empresa");
        setNickName("Empresa");
        setPrimaryKey(ID_EMPRESA);
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
