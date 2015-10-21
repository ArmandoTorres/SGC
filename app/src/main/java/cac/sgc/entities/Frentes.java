package cac.sgc.entities;

import com.delacrmi.controller.Entity;

/**
 * Created by miguel on 10/10/15.
 */
public class Frentes extends Entity {
    //"CREATE TABLE RH_FRENTE(ID_FRENTE INTEGER, DESCRIPCION TEXT)"

    public static String ID_FRENTE = "id_frente";
    public static String DESCRIPCION = "descripcion";

    @Override
    public Frentes entityConfig() {
        setName("rh_frente");
        setNickName("Frente");
        addColumn("id_frente","integer");
        addColumn("descripcion","text");
        return this;
    }
}
