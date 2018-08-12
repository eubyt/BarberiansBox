package unix.caixa.sistema.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

import unix.caixa.sistema.UnixCaixa;



public abstract class Database {

    String tabela = "BarberiansBoxs";
    JavaPlugin plugin;
    Connection conexao;
   
    
    
    public Database(JavaPlugin plugin){
        this.plugin = plugin;
    }

    public abstract Connection getConexao();

    public abstract void Carregar();

    public void iniciar(){
        conexao = getConexao();
        try{
            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM " + tabela + " WHERE Caixa = ?");
            ResultSet rs = ps.executeQuery();
            fechar(ps,rs);
   
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Erro de banco de dados", ex);
        }
    }


    public void fechar(PreparedStatement ps,ResultSet rs){
        try {
            if (ps != null)
                ps.close();
            if (rs != null)
                rs.close();
        } catch (SQLException ex) {
        ex.printStackTrace();
        }
    }
    
    
    public boolean RemoverCaixa(String UUID) {
    	
    	
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        HashMap<String, Location> loc = new HashMap<>();
        try {
        	con = getConexao();
            ps = con.prepareStatement("SELECT * FROM " + tabela + " WHERE Caixa = '" + UUID + "'");
            rs = ps.executeQuery();        

            while(rs.next()){ 
        	
              	String Mundo = rs.getString(2);
            	String x = rs.getString(3);
            	String y = rs.getString(4);
            	String z = rs.getString(5);
            	
            	Location l = new Location(Bukkit.getWorld(Mundo), Float.parseFloat(x), Float.parseFloat(y),Float.parseFloat(z));
            	
                for (Entity r : UnixCaixa.um.getNearbyEntities(l, 2)) {
               	 if ((r instanceof ArmorStand)) 
                 	r.remove();
                 }
                
                UnixCaixa.getUnixEvento().data.remove(l.getBlock().getLocation());
                UnixCaixa.CaixaLista.remove(UUID);

            	
            ps = con.prepareStatement("DELETE FROM " + tabela + " WHERE Caixa = ?");
            ps.setString(1, UUID);
            ps.execute();
            
            ps.close();
            con.close();
            return true;
            }
            
            
        } catch (SQLException ex) {
                  ex.printStackTrace();
                  return false;

        } 
        
        
            try {
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
                return false;
            } catch (SQLException ex) {
               ex.printStackTrace();
            }
        

        
        return false;
    }
    
    public HashMap<String, Location> CarregarCaixas() {
    	
    	
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        HashMap<String, Location> loc = new HashMap<>();
        try {
        	con = getConexao();
            ps = con.prepareStatement("SELECT * FROM " + tabela + ";");
   
            rs = ps.executeQuery();
            while(rs.next()){
            	String nome_caixa = rs.getString(1);
            	String Mundo = rs.getString(2);
            	String x = rs.getString(3);
            	String y = rs.getString(4);
            	String z = rs.getString(5);
            	
            	System.out.println("Caixa: " + nome_caixa + " Loc: " + Mundo + "(x:"+x + ", y:" + y + ", z:" + z + ")");
            	loc.put(nome_caixa, new Location(Bukkit.getWorld(Mundo), Float.parseFloat(x), Float.parseFloat(y),Float.parseFloat(z)));
            	
            }
            rs.close();
            
        } catch (SQLException ex) {
                  ex.printStackTrace();

        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException ex) {
               ex.printStackTrace();
            }
        }
        
        return loc;
    }
    
    public void AdicionarCaixa(String ID_Caixa, Location loc) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
        	
            con = getConexao();
            ps = con.prepareStatement("REPLACE INTO " + tabela + " (Caixa,Mundo,x,y,z) VALUES(?,?,?,?,?)"); 
            ps.setString(1, ID_Caixa);     
            ps.setString(2, String.valueOf(loc.getWorld().getName()));     
            ps.setString(3, String.valueOf(loc.getX()));     
            ps.setString(4, String.valueOf(loc.getY()));   
            ps.setString(5, String.valueOf(loc.getZ()));     

            ps.executeUpdate();
            
            return;
        } catch (SQLException ex) {

        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (SQLException ex) {
               ex.printStackTrace();
            }
        
        return;      
        }
    }
}