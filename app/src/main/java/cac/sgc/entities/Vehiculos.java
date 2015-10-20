package cac.sgc.entities;

import com.delacrmi.controller.Entity;

/**
 * Created by miguel on 10/10/15.
 */
public class Vehiculos extends Entity {
    //"CREATE TABLE MQ_VEHICULO(ID_EMPRESA INTEGER,AREA INTEGER,CODIGO_GRUPO TEXT, CODIGO_SUBGRUPO INTEGER, CODIGO_VEHICULO INTEGER)";

    private static String ID_EMPRESA = "id_empresa";
    private static String ID_AREA = "id_area";
    private static String CODIGO_GRUPO = "codigo_grupo";
    private static String CODIGO_SUBGRUPO = "codigo_subgrupo";
    private static String CODIGO_VEHICULO = "codigo_vehiculo";

    @Override
    public Vehiculos entityConfig() {
        setName("mq_vehiculo");
        addColumn("codigo_grupo", "text");
        addColumn("codigo_subgrupo","integer");
        addColumn("codigo_vehiculo","integer");
        return this;
    }
}
