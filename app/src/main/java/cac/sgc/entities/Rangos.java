package cac.sgc.entities;

import com.delacrmi.controller.Entity;

/**
 * Created by Legal on 19/10/2015.
 */
public class Rangos extends Entity {

    public static String EMPRESA      = "id_empresa";
    public static String PERIODO      = "id_periodo";
    public static String DISPOSITIVO  = "dispositivo";
    public static String ENVIO_DESDE  = "envio_desde";
    public static String ENVIO_HASTA  = "envio_hasta";
    public static String ENVIO_ACTUAL = "envio_actual";
    public static String TICKET_DESDE = "ticket_desde";
    public static String TICKET_HASTA = "ticket_hasta";
    public static String TICKET_ACTUAL = "ticket_actual";
    public static String STATUS       = "status";
    public static String CORRELATIVO  = "correlativo";
    public static String TABLE_NAME   = "ba_rango_envio";

    @Override
    public Rangos entityConfig() {
        setName(Rangos.TABLE_NAME);
        setNickName("Rango");
        setPrimaryKey(Rangos.CORRELATIVO);
        addColumn(Rangos.EMPRESA, "integer");
        addColumn(Rangos.PERIODO,"integer");
        addColumn(Rangos.DISPOSITIVO, "text");
        addColumn(Rangos.ENVIO_DESDE, "integer");
        addColumn(Rangos.ENVIO_HASTA, "integer");
        addColumn(Rangos.ENVIO_ACTUAL,"integer");
        addColumn(Rangos.TICKET_DESDE,"integer");
        addColumn(Rangos.TICKET_HASTA,"integer");
        addColumn(Rangos.TICKET_ACTUAL,"integer");
        addColumn(Rangos.STATUS,"text");
        return this;
    }
}
