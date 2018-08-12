package unix.caixa.sistema.sqlite;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.plugin.java.JavaPlugin;

public class UnixSQLite extends Database {

	String db;
	
    public String CriarBancoDeDados = "CREATE TABLE IF NOT EXISTS BarberiansBoxs (" + 
            "`Caixa` varchar(200) NOT NULL," + 
            "`Mundo` varchar(60) NOT NULL," +
            "`x` varchar(60) NOT NULL," +
            "`y` varchar(60) NOT NULL," +
            "`z` varchar(60) NOT NULL," +
            "PRIMARY KEY (`Caixa`)" +  
            ");";
	
	public UnixSQLite(JavaPlugin plugin) {
		super(plugin);
		 db = plugin.getConfig().getString("Caixas", "BarberiansBoxs");
	
	}

	@Override
	public Connection getConexao() {
		 File procurar = new File(plugin.getDataFolder(), db+".db");
		 
	      if (!procurar.exists()){
	            try {
	                procurar.createNewFile();
	            } catch (IOException e) {
	                   e.printStackTrace();
	            }
	        }
	      
	      
	       try {
	    	   
	            if( conexao!=null&&! conexao.isClosed())
	                return  conexao;
	            
	            Class.forName("org.sqlite.JDBC");
	            conexao = DriverManager.getConnection("jdbc:sqlite:" + procurar);
	            return  conexao;
	            
	        } catch (SQLException ex) {
               ex.printStackTrace();

	        } catch (ClassNotFoundException ex) {
	       ex.printStackTrace();
	        }
		 
		return null;
	}

	@Override
	public void Carregar() {
		
		  conexao = getConexao();
	        try {
	            Statement s = conexao.createStatement();
	            s.executeUpdate(CriarBancoDeDados);
	            s.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    iniciar();
		
	}

}
