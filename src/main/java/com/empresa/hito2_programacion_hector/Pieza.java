package com.empresa.hito2_programacion_hector;

import org.bson.Document;

public class Pieza {
    private int _id;
    private String nombre;
    private String numero_serie;
    private String fabricante;
    private String descripcion;
    private String marcaVehiculo;
    private String modeloVehiculo;

    public Pieza(String id, String nombre, String numeroSerie, String fabricante, String descripcion, String marcaVehiculo, String modeloVehiculo) {
        this._id = Integer.parseInt(id);
        this.nombre = nombre;
        this.numero_serie = numeroSerie;
        this.fabricante = fabricante;
        this.descripcion = descripcion;
        this.marcaVehiculo = marcaVehiculo;
        this.modeloVehiculo = modeloVehiculo;
    }

    public Pieza(Document doc) {
        Integer idValue = doc.getInteger("_id");
        this._id = idValue != null ? idValue : 0;
        this.nombre = doc.getString("nombre");
        this.numero_serie = doc.getString("numero_serie");
        this.fabricante = doc.getString("fabricante");
        this.descripcion = doc.getString("descripcion");
        Document vehiculo = (Document) doc.get("vehiculo");
        if (vehiculo != null) {
            this.marcaVehiculo = vehiculo.getString("marca");
            this.modeloVehiculo = vehiculo.getString("modelo");
        }
    }

    public int get_id() {
        return _id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNumero_serie() {
        return numero_serie;
    }

    public String getFabricante() {
        return fabricante;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getMarcaVehiculo() {
        return marcaVehiculo;
    }

    public String getModeloVehiculo() {
        return modeloVehiculo;
    }

}