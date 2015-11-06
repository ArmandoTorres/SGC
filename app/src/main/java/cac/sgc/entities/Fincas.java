package cac.sgc.entities;

import com.delacrmi.controller.Entity;

/**
 * Created by miguel on 10/10/15.
 */
public class Fincas extends Entity {
    //CP_FINCA(ID_EMPRESA INTEGER, ID_FINCA INTEGER, DESCRIPCION TEXT, UBICACION TEXT)";

    public static String ID_FINCA    = "id_finca";
    public static String DESCRIPCION = "descripcion";
    public static String TABLE_NAME  = "cp_finca";
    public static String UBICACION   = "ubicacion";

    @Override
    public Fincas entityConfig() {
        setName(TABLE_NAME);
        setNickName("Finca");
        addColumn(ID_FINCA, "integer");
        addColumn(DESCRIPCION, "text");
        addColumn(UBICACION,"text");
        return this;
    }

}