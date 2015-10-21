package cac.sgc.entities;

import com.delacrmi.controller.Entity;

/**
 * Created by miguel on 10/10/15.
 */
public class Lotes extends Entity {
    //"CREATE TABLE CP_LOTE(ID_EMPRESA INTEGER, ID_FINCA INTEGER, ID_CANIAL INTEGER, ID_LOTE INTEGER, DESCIPCION TEXT)";

    public static String ID_LOTE = "id_lote";
    public static String ID_FINCA = "id_finca";
    public static String ID_CANIAL = "id_canial";
    public static String DESCRIPCION = "descripcion";

    @Override
    public Lotes entityConfig() {
        setName("cp_lote");
        setNickName("Lote");
        addColumn("id_empresa", "integer");
        addColumn("id_finca", "integer");
        addColumn("id_canial", "integer");
        addColumn("descripcion", "text");

        return this;
    }
}
