package org.example.ejercicios.ejercicio_7.Repository

import org.example.ejercicios.ejercicio_7.Interfaces.IPedidoRepository
import java.sql.Connection

class PedidoRepository(
    c: Connection,
) : IPedidoRepository {
    private val pedidoDAO = PedidoDAO(c)
    private val lineaPedidoDAO = LineaDAO(c)

    override fun insertar(
        pedido: Pedido,
        lineas: MutableList<LineaPedido>,
    ) {
        pedidoDAO.insertar(pedido)
        for (lineaPedido in lineas) {
            lineaPedidoDAO.insertar(lineaPedido)
        }
    }

    override fun mostrar(idUsuario: Int): MutableList<Map<Pedido, MutableList<LineaPedido>>> {
        val pedidos = pedidoDAO.mostrar(idUsuario)
        val pedidosCompletos = mutableListOf<Map<Pedido, MutableList<LineaPedido>>>()
        for (p in pedidos) {
            val asociacion: Map<Pedido, MutableList<LineaPedido>> = mapOf(Pair(p, lineaPedidoDAO.mostrar(p.id)))
            pedidosCompletos.add(asociacion)
        }
        return pedidosCompletos
    }

    override fun eliminar(pedido: Pedido) {
        pedidoDAO.eliminar(pedido)
        lineaPedidoDAO.eliminar(pedido.id)
    }
}
