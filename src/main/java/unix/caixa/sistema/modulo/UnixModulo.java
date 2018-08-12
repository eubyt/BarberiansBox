package unix.caixa.sistema.modulo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import unix.caixa.sistema.UnixCaixa;
import unix.caixa.sistema.data.UnixCaixaData;
import unix.caixa.sistema.traducao.TraducaoUtil;
import unix.caixa.sistema.util.Holograma;

public class UnixModulo {


    public void CriarCaixa(Location loc, Player jogador) {

        //Procurar se existe um bau em baixo
        if (!procurarBau(loc.add(0,0,0)))
        {
        	if (jogador == null)
        		return;
        	jogador.sendMessage(TraducaoUtil.ErroBauNaoEncontrado);
            return;
        }
        
        for (Entity r : getNearbyEntities(loc, 2)) {
        	 if ((r instanceof ArmorStand)) 
        	r.remove();
        }
        
        Block bloco = loc.add(0,-1,0).getBlock();
        Holograma holo = new Holograma(bloco.getLocation().add(0.5D,0.30,0.5D), TraducaoUtil.NomeCaixa, TraducaoUtil.ClickParaAbrir);
        holo.spawn();
        UnixCaixa.getUnixEvento().data.put(loc.add(0,1,0).getBlock(), new UnixCaixaData());
        if (jogador != null)
        UnixCaixa.database.AdicionarCaixa(UUID.randomUUID().toString(), bloco.getLocation());


    }
    





    public void CarregarCaixas() {
    	
    	if (UnixCaixa.CaixaLista == null)
    		return;
    	
    	for (Map.Entry<String, Location> caixas : UnixCaixa.CaixaLista.entrySet()) {
    		
    	Block bloco = caixas.getValue().add(0,0,0).getBlock();
    	
  /*  	if ((bloco.getLocation().add(0,1,0).getBlock().getType() != Material.ENDER_CHEST) || (bloco.getLocation().add(0,1,0).getBlock().getType() != Material.CHEST)) {
        bloco.getLocation().add(0,1,0).getBlock().setType(Material.ENDER_CHEST);
        bloco.getLocation().add(0,1,0).getChunk().load();
        bloco.getWorld().save();
    	} */
    	
        for (Entity r : getNearbyEntities(caixas.getValue(), 2)) {
       	 if ((r instanceof ArmorStand)) 
       	r.remove();
       }
        
        Holograma holo = new Holograma(bloco.getLocation().add(0.5D,0.30,0.5D), TraducaoUtil.NomeCaixa, TraducaoUtil.ClickParaAbrir);
        holo.spawn();
        UnixCaixa.getUnixEvento().data.put(caixas.getValue().add(0,1,0).getBlock(), new UnixCaixaData());
        
    	}
    	
    }


    private boolean procurarBau(Location loc) {
        if ((loc.getBlock().getType() == Material.CHEST) || (loc.getBlock().getType() == Material.ENDER_CHEST))
            return true;
        else
            return false;
    }
    
    
    public static Entity[]  getNearbyEntities(Location l, int radius){
        int chunkRadius = radius < 16 ? 1 : (radius - (radius % 16))/16;
        HashSet<Entity> radiusEntities = new HashSet<Entity>();
            for (int chX = 0 -chunkRadius; chX <= chunkRadius; chX ++){
                for (int chZ = 0 -chunkRadius; chZ <= chunkRadius; chZ++){
                    int x=(int) l.getX(),y=(int) l.getY(),z=(int) l.getZ();
                    for (Entity e : new Location(l.getWorld(),x+(chX*16),y,z+(chZ*16)).getChunk().getEntities()){
                        if (e.getLocation().distance(l) <= radius && e.getLocation().getBlock() != l.getBlock()) radiusEntities.add(e);
                    }
                }
            }
        return radiusEntities.toArray(new Entity[radiusEntities.size()]);
    }
}
