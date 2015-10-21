package cac.sgc.entities;

import com.delacrmi.controller.Entity;

/**
 * Created by miguel on 10/10/15.
 */
public class Fincas extends Entity {
    //CP_FINCA(ID_EMPRESA INTEGER, ID_FINCA INTEGER, DESCRIPCION TEXT, UBICACION TEXT)";

    public static String FINCA = "id_finca";
    public static String DESCRIPCION = "descripcion";

    @Override
    public Fincas entityConfig() {
        setName("cp_finca");
        setNickName("Finca");
        addColumn("descripcion", "text");
        addColumn("ubicacion","text");

        return this;
    }
}
