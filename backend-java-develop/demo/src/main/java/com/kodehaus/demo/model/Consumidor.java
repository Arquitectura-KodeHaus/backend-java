package com.kodehaus.demo.model;

import lombok.Data;

@Data
public class Consumidor {
    private String id;
    private String email;
    private String password;
    private String nombreCompleto;
    private String telefono;
}
