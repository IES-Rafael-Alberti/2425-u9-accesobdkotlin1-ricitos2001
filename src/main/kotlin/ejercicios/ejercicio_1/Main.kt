package org.example.ejercicios.ejercicio_1

import java.sql.DriverManager
import java.sql.SQLException

class Inicio() {
    val url = "jdbc:h2:./DataBase/mydb"
    val user = "user"
    val password = "password"

    fun iniciar() {
        try {
            Class.forName("org.h2.Driver")
            val connection = DriverManager.getConnection(url, user, password)
            println("Conexión exitosa")
            connection.close()
        } catch (e: SQLException) {
            throw SQLException ("Error en la conexión: ${e.message}")
        } catch (e: ClassNotFoundException) {
            throw ClassNotFoundException("No se encontró el driver JDBC: ${e.message}")
        }
    }
}

fun main() {
    val inicio = Inicio()
    inicio.iniciar()
}