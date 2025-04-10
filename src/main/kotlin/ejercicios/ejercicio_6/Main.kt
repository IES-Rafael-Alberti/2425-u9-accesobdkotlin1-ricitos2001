package org.example.ejercicios.ejercicio_6

import com.zaxxer.hikari.HikariDataSource

fun main(){
    val dataSource = HikariDataSource()
    dataSource.jdbcUrl = "jdbc:h2:./DataBase/mydb"
    dataSource.username = "user"
    dataSource.password = "password"
    println(dataSource.maximumPoolSize)
    val connection = dataSource.connection
    val query = connection.prepareStatement("insert into Usuario values(?, ?, ?)")
    query.setInt(1, 4)
    query.setString(2, "Reinaldo Girúndez")
    query.setString(3, "reingir@mail.com")
    query.executeUpdate()
}