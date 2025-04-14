package org.example.ejercicios.ejercicio_7

data class LineaPedido(
    val id: Int,
    val cantidad: Int,
    val precio: Double,
    val idPedido: Int,
    val idProducto: Int
)
