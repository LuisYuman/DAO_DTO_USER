package test;

import datos.Conexion;
import datos.UsuarioDao;
import datos.UsuarioDaoJDBC;
import domain.UsuarioDTO;
import java.sql.*;
import java.util.List;

public class ManejoUsuarios {

    public static void main(String[] args) {
        Connection conexion = null;
        try {
            conexion = Conexion.getConnection();
            if (conexion.getAutoCommit()) {
                conexion.setAutoCommit(false);
            }

            UsuarioDao usuarioDao = new UsuarioDaoJDBC(conexion);

            // Agregar un nuevo usuario
//            UsuarioDTO nuevoUsuario = new UsuarioDTO();
//            nuevoUsuario.setUsername("fernanda");
//            nuevoUsuario.setPassword("n123");

//            int resultado = usuarioDao.insert(nuevoUsuario);

//            boolean exito = (resultado > 0);

            String username = "fernanda";
            String password = "n123";

            boolean credencialesValidas = usuarioDao.validarCredenciales(username, password);

            if(credencialesValidas){
                System.out.println("El usuario " + username + " ha iniciado sesión.");
            }else{
                System.out.println("El usuario " + username + " no ha iniciado sesión.");
            }

//            if (exito) {
//                System.out.println("Se ha agregado un nuevo usuario.");
//            } else {
//                System.out.println("No se pudo agregar el usuario.");
//            }

            conexion.commit();
            System.out.println("Se ha hecho commit de la transaccion");
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            System.out.println("Entramos al rollback");
            try {
                conexion.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace(System.out);
            }
        }
    }
}

