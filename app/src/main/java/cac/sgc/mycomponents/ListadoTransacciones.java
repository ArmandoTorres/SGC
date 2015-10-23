package cac.sgc.mycomponents;

/**
 * Listado de transaccion por envio.
 * Created by Legal on 20/10/2015.
 */
public class ListadoTransacciones {

    private String titulo;
    private String subTitulo;
    private String detalle;
    private String barcode;

    public ListadoTransacciones(String titulo, String subTitulo, String detalle, String barcode) {
        this.subTitulo = subTitulo;
        this.titulo = titulo;
        this.detalle = detalle;
        this.barcode = barcode;
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

    public String getBarcode() {
        return barcode;
    }
}
