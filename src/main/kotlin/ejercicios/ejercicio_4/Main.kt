package org.example.ejercicios.ejercicio_4

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
            println("Conexión exitosa")
            do {
                val opcion = interaccionUsuario.mostrarMenu()
                when (opcion) {
                    "1" -> eliminarUsuario(connection)
                    "2" -> eliminarProducto(connection)
                    "3" -> eliminarPedido(connection)
                }
            } while (opcion != "4")
            connection.close()
        } catch (e: SQLException) {
            throw SQLException ("Error en la conexión: ${e.message}")
        } catch (e: ClassNotFoundException) {
            throw ClassNotFoundException("No se encontró el driver JDBC: ${e.message}")
        }
    }

    fun eliminarUsuario(connection: Connection?) {
        if (connection != null) {
            val query = "delete from Usuario where nombre = ?"
            val sentencia: PreparedStatement = connection.prepareStatement(query)
            sentencia.setString(1, "Cornelio Ramírez")
            sentencia.executeUpdate()
            sentencia.close()
        }
    }

    fun eliminarProducto(connection: Connection?) {
        if (connection != null) {
            val query = "delete from Producto where precio = ?"
            val sentencia: PreparedStatement = connection.prepareStatement(query)
            sentencia.setFloat(1, 24.99F)
            sentencia.executeUpdate()
            sentencia.close()
        }
    }

    fun eliminarPedido(connection: Connection?) {
        if (connection != null) {
            val query = "delete from Pedido where id = select idPedido from LineaPedido where id = ?"
            val sentencia: PreparedStatement = connection.prepareStatement(query)
            sentencia.setInt(1, 4)
            sentencia.executeUpdate()
            sentencia.close()
        }
    }
}

class InteraccionUsuario() {
    fun mostrarMenu(): String {
        println("1) eliminar al usuario Cornelio Ramírez")
        println("2) eliminar el producto con un precio de 24,99 €")
        println("3) eliminar el pedido con id igual a 4, teniendo en cuenta las líneas de pedido relacionadas")
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