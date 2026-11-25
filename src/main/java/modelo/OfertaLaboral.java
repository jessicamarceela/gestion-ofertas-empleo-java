package modelo;

import jakarta.persistence.*;

import java.util.Date;

//Esta clase se guarda en la base de datos
@Entity

public class OfertaLaboral {
    //Es la clave primaria
    @Id
    //Hace que el ID sea autoincrementable
    @GeneratedValue
    private int id;
    private String titulo;
    private String empresa;
    private String ubicacion;
    private double salario;
    private int vacantes;
    private String experiencia;
    private String tipoContrato;
    //Para que solo guarde el día, sin la hora
    @Temporal(TemporalType.DATE)
    private Date fechaPublicacion;

    public OfertaLaboral() {}

    public OfertaLaboral(int id, String titulo, String empresa, String ubicacion,
                         double salario, int vacantes, String experiencia,
                         String tipoContrato, Date fechaPublicacion) {

        this.id = id;
        this.titulo = titulo;
        this.empresa = empresa;
        this.ubicacion = ubicacion;
        this.salario = salario;
        this.vacantes = vacantes;
        this.experiencia = experiencia;
        this.tipoContrato = tipoContrato;
        this.fechaPublicacion = fechaPublicacion;
    }

    // GETTERS Y SETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getEmpresa() { return empresa; }
    public void setEmpresa(String empresa) { this.empresa = empresa; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }

    public int getVacantes() { return vacantes; }
    public void setVacantes(int vacantes) { this.vacantes = vacantes; }

    public String getExperiencia() { return experiencia; }
    public void setExperiencia(String experiencia) { this.experiencia = experiencia; }

    public String getTipoContrato() { return tipoContrato; }
    public void setTipoContrato(String tipoContrato) { this.tipoContrato = tipoContrato; }

    public Date getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(Date fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }
}