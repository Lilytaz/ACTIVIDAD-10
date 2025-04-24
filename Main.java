import java.util.*;
import java.util.regex.*;

interface Validable {
    boolean esValido();
}

class DatosUsuario implements Validable {
    private String nombre;
    private String correo;
    private String contrasena;

    public DatosUsuario(String nombre, String correo, String contrasena) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getContrasena() { return contrasena; }

    @Override
    public boolean esValido() {
        return ValidadorNombre.validar(nombre) &&
                ValidadorCorreo.validar(correo) &&
                ValidadorContrasena.validar(contrasena);
    }

    public String resumen() {
        return String.format("Nombre: %s | Correo: %s", nombre, correo);
    }
}

class ValidadorNombre {
    public static boolean validar(String nombre) {
        return nombre != null && nombre.matches("[A-Z][a-z]{2,}(\\s[A-Z][a-z]+)*");
    }
}

class ValidadorCorreo {
    public static boolean validar(String correo) {
        String regex = "^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,6}$";
        return Pattern.matches(regex, correo);
    }
}

class ValidadorContrasena {
    public static boolean validar(String contrasena) {
        String regex = "(?=(.*[A-Z]){2,})(?=(.*[a-z]){3,})(?=.*\\d)(?=.*[!@#$%^&*]).{8,}";
        return Pattern.matches(regex, contrasena);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<DatosUsuario> usuariosRegistrados = new ArrayList<>();

        System.out.println("=== REGISTRO DE USUARIOS ===");
        while (true) {
            System.out.print("\nNombre completo: ");
            String nombre = sc.nextLine();

            System.out.print("Correo electrónico: ");
            String correo = sc.nextLine();

            System.out.print("Contraseña: ");
            String contrasena = sc.nextLine();

            DatosUsuario usuario = new DatosUsuario(nombre, correo, contrasena);

            if (usuario.esValido()) {
                usuariosRegistrados.add(usuario);
                System.out.println("Registro exitoso.");
            } else {
                System.out.println("Datos inválidos. Inténtelo nuevamente.");
                continue;
            }

            System.out.print("¿Desea registrar otro usuario? (s/n): ");
            String respuesta = sc.nextLine();
            if (!respuesta.equalsIgnoreCase("s")) break;
        }

        System.out.println("\nUsuarios registrados:");
        usuariosRegistrados.forEach(u -> System.out.println("- " + u.resumen()));
    }
}