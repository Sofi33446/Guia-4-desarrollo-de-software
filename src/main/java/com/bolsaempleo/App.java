package main.java.com.bolsaempleo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws SQLException, IOException {
        Scanner scanner = new Scanner(System.in);
        AspiranteDAO aspiranteDAO = new AspiranteDAO();

        while (true) {
            System.out.println("\n--- Bolsa de Empleo ---");
            System.out.println("1. Agregar nuevo aspirante");
            System.out.println("2. Mostrar cédulas de aspirantes");
            System.out.println("3. Mostrar información detallada de un aspirante");
            System.out.println("4. Buscar aspirante por nombre");
            System.out.println("5. Ordenar aspirantes");
            System.out.println("6. Consultar aspirante con mayor experiencia");
            System.out.println("7. Consultar aspirante más joven");
            System.out.println("8. Contratar aspirante");
            System.out.println("9. Eliminar aspirantes con poca experiencia");
            System.out.println("10. Presentar promedio de edad de aspirantes");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            try {
                switch (opcion) {
                    case 1:
                        // Agregar aspirante
                        System.out.print("Cédula: ");
                        String cedula = scanner.nextLine();
                        System.out.print("Nombre: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Edad: ");
                        int edad = scanner.nextInt();
                        System.out.print("Años de experiencia: ");
                        int experiencia = scanner.nextInt();
                        scanner.nextLine(); // Limpiar el buffer
                        System.out.print("Profesión: ");
                        String profesion = scanner.nextLine();
                        System.out.print("Teléfono: ");
                        String telefono = scanner.nextLine();
                        aspiranteDAO.agregarAspirante(new Aspirante(cedula, nombre, edad, experiencia, profesion, telefono));
                        break;
                    case 2:
                        // Mostrar cédulas
                        List<String> cedulas = aspiranteDAO.obtenerCedulas();
                        System.out.println("Cédulas de aspirantes:");
                        cedulas.forEach(System.out::println);
                        break;
                    case 3:
                        // Mostrar información detallada
                        System.out.print("Cédula del aspirante: ");
                        cedula = scanner.nextLine();
                        Aspirante aspirante = aspiranteDAO.obtenerAspirantePorCedula(cedula);
                        if (aspirante != null) {
                            System.out.println(aspirante);
                        } else {
                            System.out.println("Aspirante no encontrado.");
                        }
                        break;
                    case 4:
                        // Buscar por nombre
                        System.out.print("Nombre del aspirante: ");
                        nombre = scanner.nextLine();
                        List<Aspirante> aspirantesPorNombre = aspiranteDAO.buscarPorNombre(nombre);
                        aspirantesPorNombre.forEach(System.out::println);
                        break;
                    case 5:
                        // Ordenar aspirantes
                        System.out.println("1. Por experiencia");
                        System.out.println("2. Por edad");
                        System.out.println("3. Por profesión");
                        System.out.print("Seleccione un criterio: ");
                        int criterio = scanner.nextInt();
                        List<Aspirante> aspirantesOrdenados = aspiranteDAO.ordenarAspirantes(criterio);
                        aspirantesOrdenados.forEach(System.out::println);
                        break;
                    case 6:
                        // Aspirante con mayor experiencia
                        Aspirante aspiranteMayor = aspiranteDAO.consultarMayorExperiencia();
                        System.out.println(aspiranteMayor);
                        break;
                    case 7:
                        // Aspirante más joven
                        Aspirante aspiranteJoven = aspiranteDAO.consultarMasJoven();
                        System.out.println(aspiranteJoven);
                        break;
                    case 8:
                        // Contratar aspirante
                        System.out.print("Cédula del aspirante a contratar: ");
                        cedula = scanner.nextLine();
                        aspiranteDAO.contratarAspirante(cedula);
                        break;
                    case 9:
                        // Eliminar aspirantes con poca experiencia
                        System.out.print("Años de experiencia mínima: ");
                        int añosMinimos = scanner.nextInt();
                        aspiranteDAO.eliminarAspirantesConPocaExperiencia(añosMinimos);
                        break;
                    case 10:
                        // Promedio de edad
                        double promedioEdad = aspiranteDAO.promedioEdad();
                        System.out.println("Promedio de edad: " + promedioEdad);
                        break;
                    case 0:
                        System.out.println("Saliendo...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Opción inválida.");
                }
            } catch (SQLException e) {
                System.out.println("Error en la base de datos: " + e.getMessage());
            }
        }
    }
}
