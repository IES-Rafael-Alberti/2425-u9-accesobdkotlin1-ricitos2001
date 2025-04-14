package org.example.ejercicios.ejercicio_7.Repository

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

class LineaDAO(
    private val c: Connection,
) {
    fun insertar(lineaPedido: LineaPedido) {
        try {
            val query = "INSERT INTO LINEAPEDIDO VALUES(?, ?, ?, ?, ?)"
            val sentencia: PreparedStatement = c.prepareStatement(query)
            sentencia.setInt(1, lineaPedido.id)
            sentencia.setInt(2, lineaPedido.cantidad)
            sentencia.setDouble(3, lineaPedido.precio)
            sentencia.setInt(4, lineaPedido.idPedido)
            sentencia.setInt(5, lineaPedido.idProducto)
            sentencia.executeUpdate()
            sentencia.close()
        } catch (e: SQLException) {
            throw SQLException("Error de BBDD: $e")
        }
    }

    fun mostrar(idPedido: Int): MutableList<LineaPedido> {
        val lineasDePedidos = mutableListOf<LineaPedido>()
        try {
            val sentencia = c.prepareStatement("SELECT * FROM LINEAPEDIDO WHERE IDPEDIDO = ?")
            sentencia.setInt(1, idPedido)
            val resultado = sentencia.executeQuery()
            while (resultado.next()) {
                val id = resultado.getInt("id")
                val cantidad = resultado.getInt("cantidad")
                val precio = resultado.getDouble("precio")
                val idPedido = resultado.getInt("idPedido")
                val idProducto = resultado.getInt("idProducto")
                lineasDePedidos.add(LineaPedido(id, cantidad, precio, idPedido, idProducto))
            }
        } catch (e: SQLException) {
            throw SQLException("Error de BBDD: $e")
        }
        return lineasDePedidos
    }

    fun eliminar(idPedido: Int) {
        try {
            val query = "DELETE FROM PEDIDOS WHERE IDPEDIDO = ?"
            val sentencia: PreparedStatement = c.prepareStatement(query)
            sentencia.setInt(1, idPedido)
            sentencia.executeUpdate()
            sentencia.close()
        } catch (e: SQLException) {
            throw SQLException("Error de BBDD: $e")
        }
    }
}
