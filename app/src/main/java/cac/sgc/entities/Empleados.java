package cac.sgc.entities;

import com.delacrmi.controller.Entity;

/**
 * Created by miguel on 10/10/15.
 */
public class Empleados extends Entity{
    //"CREATE TABLE RH_EMPLEADO(ID_EMPRESA INTEGER,ID_EMPLEADO INTEGER, ID_PUESTO INTEGER, NOMBRE TEXT, ESTADO TEXT)";

    public static String ID_EMPLEADO = "id_empleado";
    public static String EMPRESA = "id_empresa";
    public static String NOMBRE = "nombre";

    @Override
    public Empleados entityConfig() {
        setName("rh_empleado");
        addColumn("id_empresa", "integer");
        addColumn("id_puesto", "text");
        addColumn("nombre", "text");
        addColumn("estado_empleado","text");

        return this;
    }
}
