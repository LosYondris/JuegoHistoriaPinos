package com.cornelio.losyondris.juegohistoriapinos;

class PooUser {
    private String dni;
    private String foto;
    private String nombre;
    private String apellido;
    private String edad;
    private String nivel;
    private String puntos;
    private String fecha;

    public PooUser() { }

    public PooUser(String dni, String foto, String nombre, String apellido, String edad, String nivel, String puntos, String fecha) {
        this.dni = dni;
        this.foto = foto;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.nivel = nivel;
        this.puntos = puntos;
        this.fecha = fecha;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getPuntos() {
        return puntos;
    }

    public void setPuntos(String puntos) {
        this.puntos = puntos;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
