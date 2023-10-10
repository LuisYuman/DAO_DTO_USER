package test;

import datos.Conexion;
import datos.PersonaDao;
import datos.PersonaDaoJDBC;
import domain.PersonaDTO;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class ManejoPersonas {

    public static void desplegar(){
        Connection conexion = null;
        try{
            conexion = Conexion.getConnection();
            //el autocommit por default es true, lo pasamos a false
            if(conexion.getAutoCommit()){
                conexion.setAutoCommit(false);
            }

            PersonaDao personaDao = new PersonaDaoJDBC(conexion);
            //vamos a listar las personas
            //utilizamos el metodo list de personaJDBC
            //que devuelve un arraylist de objetos persona
            //y lo recorremos con un for each
            for(PersonaDTO persona: personaDao.select()){
                System.out.println("Persona DTO:" + persona);
            }
            //pausa con sc
            conexion.commit(); //queda permanente en la base de datos
            System.out.println("Se ha hecho commit de la transaccion");
        }catch (SQLException ex){
            ex.printStackTrace(System.out);
            System.out.println("Entramos al rollback");
            try{
                conexion.rollback();
            }catch(SQLException ex1){
                ex1.printStackTrace(System.out);
            }
        }
    }

    public static void main(String[] args) {
        desplegar(); //despliega la información de la base de datos de personas.

        Scanner sc = new Scanner(System.in);
        //definimos la variable conexion
        Connection conexion = null;
        try {
            conexion = Conexion.getConnection();
            //el autocommit por default es true, lo pasamos a false
            if (conexion.getAutoCommit()) {
                conexion.setAutoCommit(false);
            }

            PersonaDao personaDao = new PersonaDaoJDBC(conexion);

            //se inserta sin telefono ni correo
            PersonaDTO nuevapersona = new PersonaDTO();
            nuevapersona.setNombre("Juan");
            nuevapersona.setApellido("Perez");
            personaDao.insert(nuevapersona);

            //pausa para verificar antes del commit
            /*System.out.println("Presiona enter para continuar con la transaccion");
            sc.nextLine();*/
            conexion.commit(); //queda permanente en la base de datos
            System.out.println("Se ha hecho commit de la transaccion");

            //vamos a actualizar los datos
            PersonaDTO cambioPersona = new PersonaDTO();
            cambioPersona.setId_persona(1);
            cambioPersona.setNombre("Karla");
            cambioPersona.setApellido("Lara");
            cambioPersona.setEmail("nuevocorreo@gmail.com");
            cambioPersona.setTelefono("123456789");
            personaDao.update(cambioPersona);
            //pausa con sc
            conexion.commit(); //queda permanente en la base de datos
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
