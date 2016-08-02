/**
 * 
 */
package br.com.decimal.sword.mapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

/**
 * @author Vitor Hugo Oliveira
 *
 */
public class MySQL {
	
	private String host;
    private String user;
    private String pass;
    private String database;
    
    public Connection c;
    
    /**
     * Construtor da classe
     * 
     * @param host Host em que se deseja conectar 
     * @param database Nome do database em que se deseja conectar
     * @param user Nome do usuário
     * @param pass Senha do usuário
     */
    public MySQL( String host, String database, String user, String pass ) {
        this.pass = pass;
        this.user = user;
        this.host = host;
        this.database = database;
    }
    
    /**
     * Método que estabelece a conexão com o banco de dados
     * 
     * @return True se conseguir conectar, falso em caso contrário.
     */
    public boolean connect() {
        boolean isConnected = false;
        String url;
        
        url = "jdbc:mysql://"+this.host+"/"
              +this.database+"?"
              +"user="+this.user
              +"&password="+this.pass;
              
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println(url);
            this.c = DriverManager.getConnection(url);
            isConnected = true;
        } catch( SQLException e ) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            isConnected = false;
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            isConnected = false;
        } catch ( InstantiationException e ) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            isConnected = false;
        } catch ( IllegalAccessException e ) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            isConnected = false;
        }
        
        return isConnected;
    }
    
    /**
     * Esse método executa a query dada, e retorna um ResultSet
     * Talvez fosse melhor idéia fazer esse método lançar uma exception
     * a faze-lo retornar null como eu fiz, porém isso é apenas um exemplo
     * para demonstrar a funcionalidade do comando execute
     * 
     * @param query String contendo a query que se deseja executar
     * @return ResultSet em caso de estar tudo Ok, null em caso de erro.
     */
    public ResultSet executar( String query ) {
        Statement st;
        ResultSet rs;
        
        try {
            st = this.c.createStatement();
            rs = st.executeQuery(query);
            return rs;
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static void main (String [] args) {
        MySQL db = new MySQL("localhost","tac","tac","tac");
        String query;
        
        if ( db.connect() ) {
            System.out.println("Conectado!");
            System.out.println("Rodando uma query contra o banco");
            
            JOptionPane.showMessageDialog(null, "Conectado com sucesso !!!");
          
            query = "select * from usuarios";
            ResultSet rs = db.executar(query);
            try {
                if ( rs != null ) { // Verifica se a query retornou algo
                    while ( rs.next() ) {
                        // Podemos referenciar a coluna pelo índice
                        System.out.println("Id: " + rs.getInt("id"));
                        
                        // Ou pelo seu nome
                        System.out.println("Nome: " + rs.getString("nome"));
                        System.out.println("Login: " + rs.getString("login"));
                        System.out.println("Senha: " + rs.getString("senha"));
                        System.out.println("Status: " + rs.getString("Status"));
                        System.out.println("—————————-");
                    }
                }
          } catch ( SQLException e ) {
        	  
        	  JOptionPane.showMessageDialog(null, e.getMessage());
              e.printStackTrace();
          }
          
        };
      }

}
