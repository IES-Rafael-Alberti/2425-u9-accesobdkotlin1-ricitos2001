package org.example.ejercicios.ejercicio_7

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
