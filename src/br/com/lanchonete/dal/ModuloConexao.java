


package br.com.lanchonete.dal;

import java.sql.*;

public class ModuloConexao {
    
    // método responsavel por estabelecer a conexao com banco de dados
    
    public static Connection conector() {
        java.sql.Connection conexao = null;
        // a linha abaixo chama o driver
        String driver = "com.mysql.jdbc.Driver";
        // Armazenando informações referente ao banco de dados
        String url = "jdbc:mysql://localhost:3306/dblanchonete";
        String user = "root";
        String password = "";
        // Estabelecendo a conexao com o banco de dados
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            // a linha abaixo serve de apoio para esclarecer o erro
            System.out.println(e);
            return null;
        }
    }
 
}
