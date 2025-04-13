import java.util.Scanner;

import Gestion.GestorUsuarios;

public class Consola {
    Scanner myObj = new Scanner(System.in);
    GestorUsuarios myGestor = new GestorUsuarios();

    public void menu(){
        System.out.println("1. Gestion Usuarios" + '\n'
                         + "2. recursos" + '\n');
        Integer choice = Integer.valueOf(myObj.nextLine());

        switch (choice){
            case 1:
                gestionUsuario();

        }

    }
    public void gestionUsuario() {
        System.out.println("1. Agregar Usuarios" + '\n'
                         + "2. Buscar Usuario por Nombre" + '\n'
                         + "3. Buscar Usuario por Email" + '\n');
        Integer choice = Integer.valueOf(myObj.nextLine());

        switch (choice) {
            case 1:
                System.out.println("Nombre: ");
                String name = myObj.nextLine();
                System.out.println("Mail: ");
                String mail = myObj.nextLine();
                myGestor.newUser(name, mail);
                break;

            case 2:
                System.out.println("Nombre: ");
                String name_2 = myObj.nextLine();
                myGestor.searchUserName(name_2);
                break;

            case 3:
                System.out.println("Email: ");
                String email = myObj.nextLine();
                myGestor.searchUserEmail(email);
                break;
        }

    }
}
