import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SistemaDeCitas {
    private List<Doctor> doctores;
    private List<Paciente> pacientes;
    private List<Cita> citas;

    public SistemaDeCitas() {
        this.doctores = new ArrayList<>();
        this.pacientes = new ArrayList<>();
        this.citas = new ArrayList<>();
    }

    public void agregarDoctor(int id, String nombreCompleto, String especialidad) {
        Doctor doctor = new Doctor(id, nombreCompleto, especialidad);
        doctores.add(doctor);
        guardarDoctores();//guardar los doctores inmediatamente despues de agregar uno nuevo
    }

    public void agregarPaciente(int id, String nombreCompleto) {
        Paciente paciente = new Paciente(id, nombreCompleto);
        pacientes.add(paciente);
        guardarPacientes();//guardar los pacientes inmediatamente despues de agregar uno nuevo
    }

    public void agregarCita(int id, String fechaHora, String motivo, Doctor doctor, Paciente paciente) {
        Cita cita = new Cita(id, fechaHora, motivo, doctor, paciente);
        citas.add(cita);
        guardarCitas();//guardar las citas inmediatamente despues de agregar una nueva
    }

    public Doctor buscarDoctorPorId(int id) {
        for (Doctor doctor : doctores) {
            if (doctor.getId() == id) {
                return doctor;
            }
        }
        return null;
    }

    public Paciente buscarPacientePorId(int id) {
        for (Paciente paciente : pacientes) {
            if (paciente.getId() == id) {
                return paciente;
            }
        }
        return null;
    }

    public Cita buscarCitaPorId(int id) {
        for (Cita cita : citas) {
            if (cita.getId() == id) {
                return cita;
            }
        }
        return null;
    }

    public void guardarDoctores() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("db/doctores.csv"))) {
            for (Doctor doctor : doctores) {
                writer.write(doctor.getId() + "," + doctor.getNombreCompleto() + "," + doctor.getEspecialidad());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los doctores en el archivo CSV.");
        }
    }

    public void guardarPacientes() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("db/pacientes.csv"))) {
            for (Paciente paciente : pacientes) {
                writer.write(paciente.getId() + "," + paciente.getNombreCompleto());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los pacientes en el archivo CSV.");
        }
    }

    public void guardarCitas() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("db/citas.csv"))) {
            for (Cita cita : citas) {
                writer.write(cita.getId() + "," + cita.getFechaHora() + "," + cita.getMotivo() + "," +
                        cita.getDoctor().getId() + "," + cita.getPaciente().getId());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar las citas en el archivo CSV.");
        }
    }
}