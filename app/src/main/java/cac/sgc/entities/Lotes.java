package cac.sgc.entities;

import com.delacrmi.controller.Entity;

/**
 * Created by miguel on 10/10/15.
 */
public class Lotes extends Entity {
    //"CREATE TABLE CP_LOTE(ID_EMPRESA INTEGER, ID_FINCA INTEGER, ID_CANIAL INTEGER, ID_LOTE INTEGER, DESCIPCION TEXT)";

    public static String ID_LOTE     = "id_lote";
    public static String ID_FINCA    = "id_finca";
    public static String ID_CANIAL   = "id_canial";
    public static String DESCRIPCION = "descripcion";
    public static String TABLE_NAME  = "cp_lote";
    public static String ID_EMPRESA  = "id_empresa";

    @Override
    public Lotes entityConfig() {
        setName(TABLE_NAME);
        setNickName("Lote");
        addColumn(ID_LOTE, "integer");
        addColumn(ID_EMPRESA, "integer");
        addColumn(ID_FINCA, "integer");
        addColumn(ID_CANIAL, "integer");
        addColumn(DESCRIPCION, "text");
        return this;
    }
}
