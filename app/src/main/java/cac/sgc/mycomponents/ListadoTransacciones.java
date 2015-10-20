package cac.sgc.mycomponents;

/**
 * Listado de transaccion por envio.
 * Created by Legal on 20/10/2015.
 */
public class ListadoTransacciones {

    private String titulo;
    private String subTitulo;

    public ListadoTransacciones(String subTitulo, String titulo) {
        this.subTitulo = subTitulo;
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getSubTitulo() {
        return subTitulo;
    }
}
