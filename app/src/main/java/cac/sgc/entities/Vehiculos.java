package cac.sgc.entities;

import com.delacrmi.controller.Entity;

/**
 * Created by miguel on 10/10/15.
 */
public class Vehiculos extends Entity {
    //"CREATE TABLE MQ_VEHICULO(ID_EMPRESA INTEGER,AREA INTEGER,CODIGO_GRUPO TEXT, CODIGO_SUBGRUPO INTEGER, CODIGO_VEHICULO INTEGER)";

    public final static String ID_EMPRESA      = "id_empresa";
    public final static String ID_AREA         = "id_area";
    public final static String CODIGO_GRUPO    = "codigo_grupo";
    public final static String CODIGO_SUBGRUPO = "codigo_subgrupo";
    public final static String CODIGO_VEHICULO = "codigo_vehiculo";
    public final static String STATUS          = "status";
    public final static String TABLE_NAME      = "mq_mae_vehiculo";
    public final static String PRIMARY_KEY     = "correlativo";

    @Override
    public Vehiculos entityConfig() {
        setName(TABLE_NAME);
        setPrimaryKey(PRIMARY_KEY);
        addColumn(ID_EMPRESA, "integer");
        addColumn(ID_AREA, "integer");
        addColumn(CODIGO_GRUPO, "text");
        addColumn(CODIGO_SUBGRUPO,"integer");
        addColumn(CODIGO_VEHICULO,"integer");
        addColumn(STATUS,"integer");
        return this;
    }

}