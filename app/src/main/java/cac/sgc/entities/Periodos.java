package cac.sgc.entities;

import com.delacrmi.controller.Entity;

/**
 * Created by miguel on 10/10/15.
 */
public class Periodos extends Entity {
    //"CREATE TABLE PG_PERIODO(ID_EMPRESA INTEGER, ID_PERIODO INTEGER,FECHA_INI REAL,FECHA_FIN REAL,DESCRIPCION TEXT)"

    public static String ID_EMPRESA = "id_empresa";
    public static String ID_PERIODO = "id_periodo";
    public static String FECHA_INI  = "fecha_ini";
    public static String FECHA_FIN  = "fecha_fin";
    public static String DESCRIPCION = "descripcion";

    @Override
    public Periodos entityConfig() {
        setName("periodo");
        addColumn("id_empresa", "integer");
        addColumn("fecha_ini", "date");
        addColumn("fecha_fin", "date");
        addColumn("descripcion","text");
        return this;
    }


}
