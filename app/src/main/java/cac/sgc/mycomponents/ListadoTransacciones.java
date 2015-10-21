package cac.sgc.mycomponents;

/**
 * Listado de transaccion por envio.
 * Created by Legal on 20/10/2015.
 */
public class ListadoTransacciones {

    private String titulo;
    private String subTitulo;
    private String detalle;

    public ListadoTransacciones(String titulo, String subTitulo, String detalle) {
        this.subTitulo = subTitulo;
        this.titulo = titulo;
        this.detalle = detalle;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getSubTitulo() {
        return subTitulo;
    }

    public String getDetalle() {
        return detalle;
    }

}
