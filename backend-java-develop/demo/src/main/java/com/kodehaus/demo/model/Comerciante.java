package com.kodehaus.demo.model;

import lombok.Data;

@Data
public class Comerciante {
    private String id;
    private String email;
    private String password;
    private String nombreCompleto;
    private String telefono;
    private String numeroPuesto;
    private String ubicacionMercado;
}
