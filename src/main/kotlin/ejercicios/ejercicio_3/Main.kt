package org.example.ejercicios.ejercicio_3

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.SQLException

class Inicio() {
    val url = "jdbc:h2:./DataBase/mydb"
    val user = "user"
    val password = "password"
    val interaccionUsuario = InteraccionUsuario()

    fun iniciar() {
        try {
            Class.forName("org.h2.Driver")
            val connection = DriverManager.getConnection(url, user, password)
            do {
                val opcion = interaccionUsuario.mostrarMenu()
                when (opcion) {
                    "1" -> filtro1(connection)
                    "2" -> filtro2(connection)
                    "3" -> filtro3(connection)
                }
            } while (opcion != "4")
            connection.close()
        } catch (e: SQLException) {
            throw SQLException ("Error en la conexión: ${e.message}")
        } catch (e: ClassNotFoundException) {
            throw ClassNotFoundException("No se encontró el driver JDBC: ${e.message}")
        }
    }

    fun filtro1(connection: Connection?) {
        if (connection != null) {
            val query = "select * from Pedido where id = ?"
            val sentencia: PreparedStatement = connection.prepareStatement(query)
            sentencia.setInt(1, 1)
            val rs = sentencia.executeQuery()
            while(rs.next()) {
                val id = rs.getInt("id")
                val precioTotal = rs.getFloat("precioTotal")
                val idUsuario = rs.getInt("idUsuario")
                println("| $id | $precioTotal | $idUsuario |")
            }
            rs.close()
            sentencia.close()
        }
    }

    fun filtro2(connection: Connection?) {
        if (connection != null) {
            val query = "select sum(preciototal) as total, idUsuario from Pedido group by idUsuario having idUsuario = (select id from usuario where nombre = ?)"
            val sentencia: PreparedStatement = connection.prepareStatement(query)
            sentencia.setString(1, "Ataulfo Rodríguez")
            val rs = sentencia.executeQuery()
            while (rs.next()) {
                val precioTotal = rs.getFloat("total")
                val idUsuario = rs.getInt("idUsuario")
                println("| $idUsuario | $precioTotal |")
            }
            rs.close()
            sentencia.close()
        }
    }

    fun filtro3(connection: Connection?) {
        if (connection != null) {
            val query = "select * from usuario where id in(select idUsuario from pedido where id in(select idPedido from lineapedido where idProducto = (select id from producto where nombre = ?)))"
            val sentencia: PreparedStatement = connection.prepareStatement(query)
            sentencia.setString(1, "Abanico")
            val rs = sentencia.executeQuery()
            while (rs.next()) {
                val id = rs.getInt("id")
                val nombre = rs.getString("nombre")
                val correo = rs.getString("email")
                println("| $id | $nombre | $correo |")
            }
            rs.close()
            sentencia.close()
        }
    }
}

class InteraccionUsuario() {
    fun mostrarMenu(): String {
        println("1) consultar las líneas de pedido del pedido con id igual a 1")
        println("2) consultar la suma del importe total de todos los pedidos realizados por el usuario «Ataulfo Rodríguez»")
        println("3) consultar el nombre de los usuarios que hayan comprado un «Abanico»")
        println("4) terminar programa")
        val opcion =  pedirDato("elige una opcion: ")
        return opcion
    }
    fun pedirDato(mensaje: String): String {
        print(mensaje)
        var dato = ""
        do {
            dato = readln()
        } while (dato.isEmpty())
        return dato
    }
}

fun main() {
    val inicio = Inicio()
    inicio.iniciar()
}