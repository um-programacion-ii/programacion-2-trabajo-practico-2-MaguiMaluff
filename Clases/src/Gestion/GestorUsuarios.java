package Gestion;
import Excepciones.DatosErroneosException;
import Excepciones.UsuarioNoEncontradoException;
import Modelos.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorUsuarios {
    private Map<String, Usuario> users = new HashMap<>();

    public void newUser(String name, String email){
        if (name == null || email == null || !email.contains("@")){
            throw new DatosErroneosException("Ingrese los datos correctamente");
        }else{

            Usuario new_user = new Usuario(name, email);
            this.users.put(new_user.getID(),new_user);

            if (users.get(new_user.getID()) != null){
                System.out.println("Usuario creado exitosamente");
                }
        }
    }

    public Usuario searchUserName(String name){
        for (Map.Entry<String, Usuario> entrada : users.entrySet()) {
            Usuario user = entrada.getValue();
            if (user.getName().trim().equalsIgnoreCase(name.trim())){
                showInfo(user);
                return user;
            }
        }
        throw new UsuarioNoEncontradoException("El usuario no fue encontrado");
    }

    public Usuario searchUserEmail(String email){
        for (Map.Entry<String, Usuario> entrada : users.entrySet()) {
            Usuario user = entrada.getValue();
            if (user.getEmail().equals(email)) {
                showInfo(user);
                return user;
            }
        }
        throw new UsuarioNoEncontradoException("El usuario no fue encontrado");
    }

    public void showInfo(Usuario user){
        System.out.println("Name: " + user.getName() + '\n'+ "Email: " + user.getEmail() + '\n');
    }
}



