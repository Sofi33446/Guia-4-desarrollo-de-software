package main.java.com.bolsaempleo;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AspiranteDAO {
    private Connection connection;

    public AspiranteDAO() throws SQLException, IOException {
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        connection = DriverManager.getConnection(url, user, password);
    }

    public void agregarAspirante(Aspirante aspirante) throws SQLException {
        String query = "INSERT INTO aspirantes (cedula, nombre, edad, experiencia, profesion, telefono) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, aspirante.getCedula());
            stmt.setString(2, aspirante.getNombre());
            stmt.setInt(3, aspirante.getEdad());
            stmt.setInt(4, aspirante.getExperiencia());
            stmt.setString(5, aspirante.getProfesion());
            stmt.setString(6, aspirante.getTelefono());
            stmt.executeUpdate();
        }
    }

    public List<String> obtenerCedulas() throws SQLException {
        List<String> cedulas = new ArrayList<>();
        String query = "SELECT cedula FROM aspirantes";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                cedulas.add(rs.getString("cedula"));
            }
        }
        return cedulas;
    }

    public Aspirante obtenerAspirantePorCedula(String cedula) throws SQLException {
        String query = "SELECT * FROM aspirantes WHERE cedula = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, cedula);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Aspirante(
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        rs.getInt("experiencia"),
                        rs.getString("profesion"),
                        rs.getString("telefono")
                );
            }
        }
        return null;
    }

    public List<Aspirante> buscarPorNombre(String nombre) throws SQLException {
        List<Aspirante> aspirantes = new ArrayList<>();
        String query = "SELECT * FROM aspirantes WHERE nombre LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + nombre + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                aspirantes.add(new Aspirante(
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        rs.getInt("experiencia"),
                        rs.getString("profesion"),
                        rs.getString("telefono")
                ));
            }
        }
        return aspirantes;
    }

    public List<Aspirante> ordenarAspirantes(int criterio) throws SQLException {
        List<Aspirante> aspirantes = new ArrayList<>();
        String orderBy = "";
        switch (criterio) {
            case 1:
                orderBy = "experiencia";
                break;
            case 2:
                orderBy = "edad";
                break;
            case 3:
                orderBy = "profesion";
                break;
            default:
                return aspirantes;
        }

        String query = "SELECT * FROM aspirantes ORDER BY " + orderBy;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                aspirantes.add(new Aspirante(
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        rs.getInt("experiencia"),
                        rs.getString("profesion"),
                        rs.getString("telefono")
                ));
            }
        }
        return aspirantes;
    }

    public Aspirante consultarMayorExperiencia() throws SQLException {
        String query = "SELECT * FROM aspirantes ORDER BY experiencia DESC LIMIT 1";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return new Aspirante(
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        rs.getInt("experiencia"),
                        rs.getString("profesion"),
                        rs.getString("telefono")
                );
            }
        }
        return null;
    }

    public Aspirante consultarMasJoven() throws SQLException {
        String query = "SELECT * FROM aspirantes ORDER BY edad ASC LIMIT 1";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return new Aspirante(
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        rs.getInt("experiencia"),
                        rs.getString("profesion"),
                        rs.getString("telefono")
                );
            }
        }
        return null;
    }

    public void contratarAspirante(String cedula) throws SQLException {
        String query = "UPDATE aspirantes SET contratado = TRUE WHERE cedula = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, cedula);
            stmt.executeUpdate();
        }
    }

    public void eliminarAspirantesConPocaExperiencia(int añosMinimos) throws SQLException {
        String query = "DELETE FROM aspirantes WHERE experiencia < ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, añosMinimos);
            stmt.executeUpdate();
        }
    }

    public double promedioEdad() throws SQLException {
        String query = "SELECT AVG(edad) as promedio FROM aspirantes";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getDouble("promedio");
            }
        }
        return 0;
    }
}
