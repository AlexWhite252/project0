package com.Revature

import java.sql.DriverManager
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import scala.io.StdIn._

object JDBC1 {

  def main(args: Array[String]) {
    // connect to the database named "test" on the localhost

    val driver = "com.mysql.cj.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/project0"
    val username = "root"
    val password = "Luni2323cK!"

    var connection:Connection = DriverManager.getConnection(url, username, password)
    val statement = connection.createStatement()

    println("Welcome to the Note DB App")

    val noteUser = readLine("Please enter your username: ")
    val notePass = readLine("Please enter your password: ")
    //Check noteUser and notePass against the user table in db, if not valid then loop

    println(
      """
        |Please select an option from the menu
        |1. Compose new note
        |2. Read previous note
        |3. Log out
        |""".stripMargin)
    var menuSelect = readInt()
    menuSelect match {
      case 1 =>

      case 2 =>

      case 3 =>

      case _ =>
    }

    val resultSet = statement.executeQuery("SELECT * FROM users;")
    //val sql = statement.executeUpdate("INSERT INTO users VALUES (100, 'notetaker', 'passpass');")

    while ( resultSet.next() ) {
      println(resultSet.getString(1)+", " +resultSet.getString(2)+", " +resultSet.getString(3))
    }
    connection.close()
  }

}