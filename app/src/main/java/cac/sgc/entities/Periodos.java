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
    public static String TABLE_NAME  = "pg_periodo";

    @Override
    public Periodos entityConfig() {
        setName(TABLE_NAME);
        setNickName("Periodos");
        addColumn(ID_PERIODO,"integer");
        addColumn(ID_EMPRESA, "integer");
        addColumn(FECHA_INI, "date");
        addColumn(FECHA_FIN, "date");
        addColumn(DESCRIPCION,"text");
        return this;
    }


}
