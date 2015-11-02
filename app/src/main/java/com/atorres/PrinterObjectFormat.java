package com.atorres;

import android.graphics.Bitmap;

public class PrinterObjectFormat {

    private BluetoothPrinterManager.PRINTER_FONT fontType;
    private BluetoothPrinterManager.PRINTER_OBJECT objectType;
    private String Text;
    private Bitmap bmp;

    public PrinterObjectFormat() {}

    public PrinterObjectFormat( BluetoothPrinterManager.PRINTER_FONT fontType, BluetoothPrinterManager.PRINTER_OBJECT objectType, String text ) {
        this.fontType = fontType;
        this.objectType = objectType;
        Text = text;
    }

    public PrinterObjectFormat( BluetoothPrinterManager.PRINTER_OBJECT objectType, Bitmap bmp ) {
        this.objectType = objectType;
        this.bmp = bmp;
    }

    public BluetoothPrinterManager.PRINTER_FONT getFontType() {
        return fontType;
    }

    public void setFontType(BluetoothPrinterManager.PRINTER_FONT fontType) {
        this.fontType = fontType;
    }

    public BluetoothPrinterManager.PRINTER_OBJECT getObjectType() {
        return objectType;
    }

    public void setObjectType(BluetoothPrinterManager.PRINTER_OBJECT objectType) {
        this.objectType = objectType;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public Bitmap getBmp() {
        return bmp;
    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }
}