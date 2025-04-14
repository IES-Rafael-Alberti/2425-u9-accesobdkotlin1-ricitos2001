package org.example.ejercicios.ejercicio_7.Interfaces

import org.example.ejercicios.ejercicio_7.Repository.LineaPedido
import org.example.ejercicios.ejercicio_7.Repository.Pedido

interface IPedidoRepository {
    fun insertar(
        pedido: Pedido,
        lineas: MutableList<LineaPedido>,
    )

    fun mostrar(idUsuario: Int): MutableList<Map<Pedido, MutableList<LineaPedido>>>

    fun eliminar(pedido: Pedido)
}