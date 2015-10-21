package cac.sgc.entities;

import com.delacrmi.controller.Entity;

/**
 * Created by miguel on 10/10/15.
 */
public class Empleados extends Entity{
    //"CREATE TABLE RH_EMPLEADO(ID_EMPRESA INTEGER,ID_EMPLEADO INTEGER, ID_PUESTO INTEGER, NOMBRE TEXT, ESTADO TEXT)";

    public static String ID_EMPLEADO = "id_empleado";
    public static String ID_EMPRESA = "id_empresa";
    public static String ID_PUESTO  = "id_puesto";
    public static String NOMBRE = "nombre";
    public static String ESTADO_EMPLEADO = "estado_empleado";
    public static String TABLE_NAME = "rh_empleado";

    @Override
    public Empleados entityConfig() {
        setName(Empleados.TABLE_NAME);
        addColumn(Empleados.ID_EMPLEADO, "integer");
        addColumn(Empleados.ID_EMPRESA, "integer");
        addColumn(Empleados.ID_PUESTO, "text");
        addColumn(Empleados.NOMBRE, "text");
        addColumn(Empleados.ESTADO_EMPLEADO,"text");
        return this;
    }
}
