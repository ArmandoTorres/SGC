package cac.sgc.mycomponents;

import android.graphics.Bitmap;

/**
 * Listado de transaccion por envio.
 * Created by Legal on 20/10/2015.
 */
public class ListadoTransacciones {

    private String titulo;
    private String subTitulo;
    private String detalle;
    private String barcode;
    private Bitmap bmp;

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

    public Bitmap getBmp() {
        return bmp;
    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }
}
