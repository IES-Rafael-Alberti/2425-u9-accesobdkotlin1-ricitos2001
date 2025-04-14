package org.example.ejercicios.ejercicio_7

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

class PedidoDAO(
    private val c: Connection
) {
    fun insertar(pedido: Pedido) {
        try {
            val query = "INSERT INTO PEDIDOS VALUES(?, ?, ?)"
            val sentencia: PreparedStatement = c.prepareStatement(query)
            sentencia.setInt(1, pedido.id)
            sentencia.setDouble(2, pedido.precioTotal)
            sentencia.setInt(3, pedido.idUsuario)
            sentencia.executeUpdate()
            sentencia.close()
        } catch (e: SQLException) {
            throw SQLException("Error de BBDD: $e")
        }
    }

    fun mostrar(idUsuario: Int): MutableList<Pedido> {
        val pedidos = mutableListOf<Pedido>()
        try {
            val sentencia = c.prepareStatement("SELECT * FROM PEDIDO WHERE IDUSUARIO = ?")
            sentencia.setInt(1, idUsuario)
            val resultado = sentencia.executeQuery()
            while (resultado.next()) {
                val id = resultado.getInt("id")
                val precioTotal = resultado.getDouble("precioTotal")
                val idUsuario = resultado.getInt("idUsuario")
                pedidos.add(Pedido(id, precioTotal, idUsuario))
            }
        } catch (e: SQLException) {
            throw SQLException("Error de BBDD: $e")
        }
        return pedidos
    }

    fun eliminar(pedido: Pedido) {
        try {
            val query = "DELETE FROM PEDIDOS WHERE ID = ?"
            val sentencia: PreparedStatement = c.prepareStatement(query)
            sentencia.setInt(1, pedido.id)
            sentencia.executeUpdate()
            sentencia.close()
        } catch (e: SQLException) {
            throw SQLException("Error de BBDD: $e")
        }
    }
}
