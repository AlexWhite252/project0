package com.Revature

import java.sql.DriverManager
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

object JDBC1 {

  def main(args: Array[String]) {
    // connect to the database named "test" on the localhost

    val driver = "com.mysql.cj.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/project0"
    val username = "root"
    val password = "Luni2323cK!"

    var connection:Connection = DriverManager.getConnection(url, username, password)
    val statement = connection.createStatement()
    val insertSet = statement.executeQuery( "INSERT INTO content VALUE (3,'This is my first entry from the IDE');")
    val resultSet = statement.executeQuery("SELECT * FROM content;")


    while ( resultSet.next() ) {
      println(resultSet.getString(1)+", " +resultSet.getString(2)+", " +resultSet.getString(3))
    }
    connection.close()
  }

}