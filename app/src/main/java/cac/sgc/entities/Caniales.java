package cac.sgc.entities;

import com.delacrmi.controller.Entity;

/**
 * Created by miguel on 10/10/15.
 */
public class Caniales extends Entity {
    //"CREATE TABLE CP_CANIAL(ID_EMPRESA INTEGER, ID_FINCA INTEGER,ID_CANIAL INTEGER, DESCIPCION TEXT)";

    public static String ID_EMPRESA  = "id_empresa";
    public static String ID_FINCA    = "id_finca";
    public static String ID_CANIAL      = "id_canial";
    public static String DESCRIPCION = "descripcion";
    public static String TABLE_NAME  = "cp_canial";

    @Override
    public Caniales entityConfig() {
        setName(Caniales.TABLE_NAME);
        setNickName("Cañial");
        addColumn(Caniales.ID_EMPRESA, "integer");
        addColumn(Caniales.ID_FINCA, "integer");
        addColumn(Caniales.ID_CANIAL, "integer");
        addColumn(Caniales.DESCRIPCION,"text");
        return this;
    }
}