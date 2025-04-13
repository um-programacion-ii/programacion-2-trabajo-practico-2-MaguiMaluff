package Gestion;
import src.Usuario;

import java.util.ArrayList;
import java.util.List;

public class GestorUsuarios {
    private List<Usuario> users = new ArrayList<>();

    public void newUser(String name, String email){
        if (name == null || email == null || !email.contains("@")){
            System.out.println("Ingrese Los datos correctamente");
        }

        Usuario new_user = new Usuario(name, email);
        this.users.add(new_user);

        if (users.contains(new_user)){
            System.out.println("Usuario creado exitosamente");
            }
    }

    public void searchUserName(String name){
        boolean notFound = true;
        for (Usuario user : users) {
            if (user.getName().equals(name)){
                showInfo(user);
                notFound = false;
            }
        if (notFound){
                System.out.println("Usuario no encontrado");
            }
        }
    }

    public void searchUserEmail(String email){
        boolean notFound = true;
        for (Usuario user : users) {
            if (user.getEmail().equals(email)){
                showInfo(user);
                notFound = false;
            }
            if (notFound){
                System.out.println("Usuario no encontrado");
            }
        }
    }

    public void showInfo(Usuario user){
        System.out.println("Name: " + user.getName() + '\n'+ "Email: " + user.getEmail() + '\n');
    }
}



