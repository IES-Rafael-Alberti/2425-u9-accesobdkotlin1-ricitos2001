package org.example.ejercicios.ejercicio_7

import org.example.ejercicios.ejercicio_7.Repository.Conexion
import org.example.ejercicios.ejercicio_7.Repository.PedidoRepository

fun main() {
    val c = Conexion()
    if (c.c.isValid(10)) {
        c.c.use {
            val pedidos = PedidoRepository(c.c).mostrar(1)
            for (p in pedidos) {
                println(p.keys)
                for (l in p.values.elementAt(0)) {
                    println(l)
                }
            }
        }
    } else {
        println("Error de conexión")
    }
}
