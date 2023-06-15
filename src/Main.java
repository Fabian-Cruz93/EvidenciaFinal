import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static SistemaDeCitas sistemaDeCitas = new SistemaDeCitas();

    public static void main(String[] args) {
        // Crear la carpeta "db" si no existe
        File directorioDB = new File("db");
        if (!directorioDB.exists()) {
            if (directorioDB.mkdir()) {
                System.out.println("Carpeta 'db' creada exitosamente.");
            } else {
                System.out.println("Error al crear la carpeta 'db'.");
            }
        }

        cargarDatos();
        solicitarAcceso();



        int opcion;
        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir nueva línea pendiente

            switch (opcion) {
                case 1:
                    darAltaDoctor();
                    break;
                case 2:
                    darAltaPaciente();
                    break;
                case 3:
                    crearCita();
                    break;
                case 4:
                    mostrarCitas();
                    break;
                case 5:
                    guardarDatos();
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, intente nuevamente.");
                    break;
            }
        } while (opcion != 5);
    }

    private static void cargarDatos() {
        cargarDoctores();
        cargarPacientes();
        cargarCitas();
    }

    private static void cargarDoctores() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("db/doctores.csv"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] datos = line.split(",");
                int id = Integer.parseInt(datos[0]);
                String nombreCompleto = datos[1];
                String especialidad = datos[2];
                sistemaDeCitas.agregarDoctor(id, nombreCompleto, especialidad);
            }
            br.close();
            System.out.println("Doctores cargados exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al cargar los doctores: " + e.getMessage());
        }
    }

    private static void cargarPacientes() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("db/pacientes.csv"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] datos = line.split(",");
                int id = Integer.parseInt(datos[0]);
                String nombreCompleto = datos[1];
                sistemaDeCitas.agregarPaciente(id, nombreCompleto);
            }
            br.close();
            System.out.println("Pacientes cargados exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al cargar los pacientes: " + e.getMessage());
        }
    }

    private static void cargarCitas() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("db/citas.csv"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] datos = line.split(",");
                int id = Integer.parseInt(datos[0]);
                String fechaHora = datos[1];
                String motivo = datos[2];
                int idDoctor = Integer.parseInt(datos[3]);
                int idPaciente = Integer.parseInt(datos[4]);
                Doctor doctor = sistemaDeCitas.buscarDoctorPorId(idDoctor);
                Paciente paciente = sistemaDeCitas.buscarPacientePorId(idPaciente);
                sistemaDeCitas.agregarCita(id, fechaHora, motivo, doctor, paciente);
            }
            br.close();
            System.out.println("Citas cargadas exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al cargar las citas: " + e.getMessage());
        }
    }

    private static void solicitarAcceso() {
        System.out.println("Bienvenido al sistema de administración de citas");
        System.out.print("Ingrese su identificador: ");
        String identificador = scanner.nextLine();

        System.out.print("Ingrese su contraseña: ");
        String contraseña = scanner.nextLine();

        // Realizar verificación de identificador y contraseña aquí
        // ...
    }

    private static void mostrarMenu() {
        System.out.println("\nMenú Principal");
        System.out.println("1. Dar de alta doctor");
        System.out.println("2. Dar de alta paciente");
        System.out.println("3. Crear cita");
        System.out.println("4. Mostrar citas");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void darAltaDoctor() {
        System.out.println("\nDar de Alta Doctor");
        System.out.print("Ingrese el ID del doctor: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir nueva línea pendiente

        System.out.print("Ingrese el nombre completo del doctor: ");
        String nombreCompleto = scanner.nextLine();

        System.out.print("Ingrese la especialidad del doctor: ");
        String especialidad = scanner.nextLine();

        Doctor doctor = new Doctor(id, nombreCompleto, especialidad);
        sistemaDeCitas.agregarDoctor(id, nombreCompleto, especialidad);
        System.out.println("Doctor dado de alta exitosamente.");
    }

    private static void darAltaPaciente() {
        System.out.println("\nDar de Alta Paciente");
        System.out.print("Ingrese el ID del paciente: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir nueva línea pendiente

        System.out.print("Ingrese el nombre completo del paciente: ");
        String nombreCompleto = scanner.nextLine();

        Paciente paciente = new Paciente(id, nombreCompleto);
        sistemaDeCitas.agregarPaciente(id, nombreCompleto);
        System.out.println("Paciente dado de alta exitosamente.");
    }

    private static void crearCita() {
        System.out.println("\nCrear Cita");
        System.out.print("Ingrese el ID de la cita: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir nueva línea pendiente

        System.out.print("Ingrese la fecha y hora de la cita: ");
        String fechaHora = scanner.nextLine();

        System.out.print("Ingrese el motivo de la cita: ");
        String motivo = scanner.nextLine();

        System.out.print("Ingrese el ID del doctor: ");
        int idDoctor = scanner.nextInt();
        scanner.nextLine(); // Consumir nueva línea pendiente

        Doctor doctor = sistemaDeCitas.buscarDoctorPorId(idDoctor);

        System.out.print("Ingrese el ID del paciente: ");
        int idPaciente = scanner.nextInt();
        scanner.nextLine(); // Consumir nueva línea pendiente

        Paciente paciente = sistemaDeCitas.buscarPacientePorId(idPaciente);

        Cita cita = new Cita(id, fechaHora, motivo, doctor, paciente);
        sistemaDeCitas.agregarCita(id, fechaHora, motivo, doctor, paciente);
        System.out.println("Cita creada exitosamente.");
    }

    private static void mostrarCitas() {
        System.out.println("\nMostrar Citas");
        System.out.print("Ingrese el ID de la cita: ");
        int idCita = scanner.nextInt();
        scanner.nextLine(); // Consumir nueva línea pendiente

        Cita citaMostrar = sistemaDeCitas.buscarCitaPorId(idCita);
        if (citaMostrar != null) {
            Paciente paciente = citaMostrar.getPaciente();
            Doctor doctor = citaMostrar.getDoctor();
            System.out.println("Paciente: " + paciente.getNombreCompleto());
            System.out.println("Doctor: " + doctor.getNombreCompleto());
        } else {
            System.out.println("La cita con ID " + idCita + " no existe.");
        }
    }

    private static void guardarDatos() {
        sistemaDeCitas.guardarDoctores();
        sistemaDeCitas.guardarPacientes();
        sistemaDeCitas.guardarCitas();
        System.out.println("Datos guardados exitosamente.");
    }
}