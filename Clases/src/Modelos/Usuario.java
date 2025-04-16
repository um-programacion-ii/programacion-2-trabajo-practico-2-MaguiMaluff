package Modelos;

import java.util.UUID;

public class Usuario {
    private String ID;
    private String name;
    private String email;

    public String  getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Usuario(String name, String email) {
        this.ID = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
    }

}
