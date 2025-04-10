package org.example.ejercicios.ejercicio_2

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.SQLException

data class Usuario(val id: Int, val nombre: String, val correo: String)
data class Producto(val id: Int, val nombre: String, val precio: Float, val stock: Int)
data class Pedido(val id: Int, val precioTotal: Float, val idUsuario: Int)
data class LineaDePedido(val id: Int, val cantidad: Int, val precio: Float,  val idPedido: Int, val idProducto: Int)

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
                    "1" -> insertarUsuario(connection)
                    "2" -> insertarProducto(connection)
                    "3" -> insertarPedido(connection)
                    "4" -> insertarLineaDePedidos(connection)
                }
            } while (opcion != "5")
            connection.close()
        } catch (e: SQLException) {
            throw SQLException ("Error en la conexión: ${e.message}")
        } catch (e: ClassNotFoundException) {
            throw ClassNotFoundException("No se encontró el driver JDBC: ${e.message}")
        }
    }

    fun insertarUsuario(connection: Connection?) {
        if (connection != null) {
            val query = "insert into Usuario values(?, ?, ?)"
            val sentencia: PreparedStatement = connection.prepareStatement(query)
            val usuario = interaccionUsuario.accionInsertarUsuario()
            sentencia.setInt(1, usuario.id)
            sentencia.setString(2, usuario.nombre)
            sentencia.setString(3, usuario.correo)
            sentencia.executeUpdate()
            sentencia.close()
        }
    }

    fun insertarProducto(connection: Connection?) {
        if (connection != null) {
            val query = "insert into Producto values(?, ?, ?, ?)"
            val sentencia: PreparedStatement = connection.prepareStatement(query)
            val producto = interaccionUsuario.accionInsertarProducto()
            sentencia.setInt(1, producto.id)
            sentencia.setString(2, producto.nombre)
            sentencia.setFloat(3, producto.precio)
            sentencia.setInt(4, producto.stock)
            sentencia.executeUpdate()
            sentencia.close()
        }
    }

    fun insertarPedido(connection: Connection?) {
        if (connection != null) {
            val query = "insert into Pedido values(?, ?, ?)"
            val sentencia: PreparedStatement = connection.prepareStatement(query)
            val pedido = interaccionUsuario.accionInsertarPedido()
            sentencia.setInt(1, pedido.id)
            sentencia.setFloat(2, pedido.precioTotal)
            sentencia.setInt(3, pedido.idUsuario)
            sentencia.executeUpdate()
            sentencia.close()
        }
    }

    fun insertarLineaDePedidos(connection: Connection?) {
        if (connection != null) {
            val query = "insert into LineaPedido values(?, ?, ?, ?, ?)"
            val sentencia: PreparedStatement = connection.prepareStatement(query)
            val lineaDePedidos = interaccionUsuario.accionInsertarLineaDePedidos()
            sentencia.setInt(1, lineaDePedidos.id)
            sentencia.setInt(2, lineaDePedidos.cantidad)
            sentencia.setFloat(3, lineaDePedidos.precio)
            sentencia.setInt(4, lineaDePedidos.idPedido)
            sentencia.setInt(5, lineaDePedidos.idProducto)
            sentencia.executeUpdate()
            sentencia.close()
        }
    }
}

class InteraccionUsuario() {
    fun mostrarMenu(): String {
        println("1) insertar usuario")
        println("2) insertar porducto")
        println("3) insertar pedido")
        println("4) insertar linea de pedidos")
        println("5) terminar programa")
        val opcion =  pedirDato("elige una opcion: ")
        return opcion
    }

    fun accionInsertarUsuario(): Usuario {
        val id = pedirDato("id: ").toInt()
        val nombre = pedirDato("nombre: ")
        val correo = pedirDato("correo: ")
        return Usuario(id, nombre, correo)
    }

    fun accionInsertarProducto(): Producto {
        val id = pedirDato("id: ").toInt()
        val nombre = pedirDato("nombre: ")
        val precio = pedirDato("precio: ").toFloat()
        val stock = pedirDato("stock: ").toInt()
        return Producto(id, nombre, precio, stock)
    }

    fun accionInsertarPedido(): Pedido {
        val id = pedirDato("id: ").toInt()
        val idUsuario = pedirDato("id del usuario: ").toInt()
        val precioTotal = pedirDato("precio total: ").toFloat()
        return Pedido(id, precioTotal, idUsuario)
    }

    fun accionInsertarLineaDePedidos(): LineaDePedido {
        val id = pedirDato("id: ").toInt()
        val idPedido = pedirDato("id del pedido: ").toInt()
        val idProducto = pedirDato("id del producto: ").toInt()
        val cantidad = pedirDato("cantidad: ").toInt()
        val precio = pedirDato("precio: ").toFloat()
        return LineaDePedido(id, cantidad, precio, idPedido, idProducto)
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