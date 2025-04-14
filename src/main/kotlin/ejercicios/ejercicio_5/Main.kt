package org.example.ejercicios.ejercicio_5

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
                    "1" -> modificar1(connection)
                    "2" -> modificar2(connection)
                }
            } while (opcion != "3")
            connection.close()
        } catch (e: SQLException) {
            throw SQLException ("Error en la conexión: ${e.message}")
        } catch (e: ClassNotFoundException) {
            throw ClassNotFoundException("No se encontró el driver JDBC: ${e.message}")
        }
    }

    fun modificar1(connection: Connection?) {
        if (connection != null) {
            val query = "update Producto set precio = (select precio/2 from producto where nombre = ?) where nombre = ?"
            val sentencia: PreparedStatement = connection.prepareStatement(query)
            sentencia.setString(1, "Abanico")
            sentencia.setString(2, "Abanico")
            sentencia.executeUpdate()
            sentencia.close()
        }
    }

    fun modificar2(connection: Connection?) {
        if (connection != null) {
            val query = "update lineapedido set idproducto = (select ? from lineapedido where id = ?) where id = ?; update producto set precio = (select precio*2 from producto where nombre = ?) where nombre = ?"
            val sentencia: PreparedStatement = connection.prepareStatement(query)
            sentencia.setInt(1, 2)
            sentencia.setInt(2, 3)
            sentencia.setInt(3, 3)
            sentencia.setString(4, "Abanico")
            sentencia.setString(5, "Abanico")
            sentencia.executeUpdate()
            sentencia.close()
        }
    }
}

class InteraccionUsuario() {
    fun mostrarMenu(): String {
        println("1) modificar el precio del producto «Abanico» para que esté en oferta")
        println("2) modificar la línea de pedido con id igual a 3, cambiando el id_producto a 2 y el precio a dos veces la cantidad del precio que tenga ahora «Abanico»")
        println("3) terminar programa")
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