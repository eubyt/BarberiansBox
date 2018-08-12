package unix.caixa.sistema.animacao.lista.Padrao.execute;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.material.Directional;
import org.bukkit.material.MaterialData;
import org.bukkit.scheduler.BukkitRunnable;

import net.minecraft.server.v1_8_R3.BlockPosition;
import unix.caixa.sistema.UnixCaixa;
import unix.caixa.sistema.data.BauType;
import unix.caixa.sistema.data.BausMagicoJogador;
import unix.caixa.sistema.evento.UnixEvento;
import unix.caixa.sistema.modulo.Premio;
import unix.caixa.sistema.modulo.UnixModulo;
import unix.caixa.sistema.util.Blocos;
import unix.caixa.sistema.util.Holograma;
import unix.caixa.sistema.util.ItemStackUtil;
import unix.caixa.sistema.util.Particulas;

public class PadraoExecute {

	private boolean cancelar_som = false;
	private int spawn = 0;
	private UnixModulo um = new UnixModulo();
	private BauType bt;
	private Block bloco;
	private Player jogador;
	private BlockFace direcao;
	
	public PadraoExecute(Block bloco, Player jogador, BauType BauType) {
		this.bloco = bloco;
		this.jogador = jogador;
		this.direcao = Blocos.getDirecao(bloco);


		for (BausMagicoJogador jog : UnixCaixa.jogadores.values()) {
			if (jog.holo.holo.containsKey(bloco.getLocation())) {
				jog.holo.hologramas_nao_atualizar.add(bloco.getLocation());
				Holograma holo = jog.holo.holo.get(bloco.getLocation());

				jog.holo.holo.remove(bloco.getLocation());

			    holo.RemoverPara(jog.jogador);
			}
		}

		animacao_open(bloco, jogador, BauType);
		
	}
	
	public void animacao_open(Block bloco, Player jogador, BauType BauType) {
	
	
        for (Entity r : um.getNearbyEntities(bloco.getLocation(), 2)) {
       	 if ((r instanceof ArmorStand))
       	   r.remove();

       }
        this.bt = BauType;
		comecar(bloco.getLocation());
	}
	
	
	
	public void comecar(Location loc)
    {
        ((CraftWorld) loc.getWorld()).getHandle().playBlockAction(new BlockPosition(loc.getX(), loc.getY(), loc.getZ()), CraftMagicNumbers.getBlock(loc.getBlock()), 1, 1);
        
        new BukkitRunnable() {
        	int cabeca = 0;
        	   public void run(){
        		   
        if (cabeca > 2)
        	cancel();

        if ((direcao == BlockFace.EAST) || (direcao == BlockFace.WEST))
			Animacao(new Location(loc.getWorld(), loc.getX()+0.5, loc.getY()-2, loc.getZ()-0.2), 1);
        else
	        Animacao(new Location(loc.getWorld(), loc.getX()-0.2, loc.getY()-2, loc.getZ()+0.5), 1);

        spawn++;
        cabeca++;
        	   }
        }.runTaskTimer(UnixCaixa.getPlugin(), 0, 10);
        
        
        new BukkitRunnable() {
        	   public float i = 0.1F;
        	   public void run(){
        		   loc.getWorld().playSound(loc, Sound.NOTE_PLING, 1, i);
        		   i += 0.3F;
        		   if (cancelar_som) {
        			   loc.getWorld().playSound(loc, Sound.CREEPER_DEATH, 1, 0.2f);
        			   AnimacaoFinal(loc);
        			   cancel();
        		   }
        		   
        	   }
        }.runTaskTimer(UnixCaixa.getPlugin(), 0, 8);
    }
	
	
	
	public void Animacao(Location loc, int tick) {

		  ArmorStand main = loc.getWorld().spawn(loc, ArmorStand.class);
          main.setVisible(false);
	      main.setHelmet(ItemStackUtil.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWM5NmJlNzg4NmViN2RmNzU1MjVhMzYzZTVmNTQ5NjI2YzIxMzg4ZjBmZGE5ODhhNmU4YmY0ODdhNTMifX19"));
	      main.setGravity(false);


          new BukkitRunnable() {
          private double radius = 1;
          private double angle = 180;
          private int tempo = 0;
          private float nota = 0.1F;
          private Sound som = Sound.NOTE_PIANO;

          public void run(){
        	  if (tempo >= 26) {
        		  
        		  Descer(main, 1);
        	      cancel();
        	  }
        	  double y = (radius * Math.sin(angle));
              double z = (radius * Math.cos(angle));
              angle -= 0.1;

              if ((direcao == BlockFace.NORTH) || (direcao == BlockFace.SOUTH))
              main.teleport(new Location(loc.getWorld(), loc.getX() + z,loc.getY() + y,loc.getZ()));

			  if ((direcao == BlockFace.EAST) || (direcao == BlockFace.WEST))
				  main.teleport(new Location(loc.getWorld(), loc.getX(), loc.getY() + y, loc.getZ() + z));

              nota += 0.1F;
              tempo++;
          }
          }.runTaskTimer(UnixCaixa.getPlugin(), 0, tick);
	        
	}
	
	
	public void Descer(ArmorStand main, int tick) {
        new BukkitRunnable() {
        private int tempo = 0;
        public void run(){
      	  if (tempo >= 10) {
      		  spawn--;
      		  if (spawn == 0)
          		  cancelar_som = true;
      		  main.getLocation().getWorld().playSound(main.getLocation(), Sound.CLICK, 1, 0.2F);
      		  Particulas.EXPLOSION_NORMAL.display(main.getLocation().add(0,3,0), 0.6f, 0.1f, 0.6f);
      		  Particulas.CLOUD.display(main.getLocation().add(0,3,0), 0.6f, 0.1f, 0.6f);
      		  main.remove();
      	      cancel();
      	  }
      	 
      	main.teleport(main.getLocation().add(0,-0.1,0));
      	  tempo++;
        }
        }.runTaskTimer(UnixCaixa.getPlugin(), 0, tick);
	        
	}
	

	public void AnimacaoFinal(Location loc) {
		  ArmorStand main = loc.getWorld().spawn(new Location(loc.getWorld(), loc.getX()+0.5, loc.getY()-1.2, loc.getZ()+0.5), ArmorStand.class);
          main.setVisible(false);
	      main.setHelmet(ItemStackUtil.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODI2MDFlYzQ4OWMwNjUyNzUxNTc2OTg0ZDg1MzVmNTA0OWM4ZTBhYmQ3M2E4YTNkNTFlMDcyN2VmMmYyIn19fQ"));
	      main.setGravity(false);
	      String premio =  Premio.valueOf(bt.SortearPremio()).texto;
	      main.setCustomName("§eVocê ganhou " + premio);
	      main.setCustomNameVisible(true);
  		  main.getLocation().getWorld().playSound(main.getLocation(), Sound.FIREWORK_LAUNCH, 1, 0.2F);
       	  main.getLocation().getWorld().playSound(main.getLocation(), Sound.BAT_HURT, 1, 0.2F);
  		  
	      new BukkitRunnable() {
	        private int tempo = 0;
	        private double radius = 3;
	        private double angle = 180;
	        public void run(){
	        
	        	 if (tempo >= 50) {
	        		 ((CraftWorld) loc.getWorld()).getHandle().playBlockAction(new BlockPosition(loc.getX(), loc.getY(), loc.getZ()), CraftMagicNumbers.getBlock(loc.getBlock()), 1, 0);
	        		 main.remove();
	        		 um.CriarCaixa(loc, null);
	      		     UnixEvento.caixas_sendo_abertas.remove(bloco);

					 for (BausMagicoJogador jog : UnixCaixa.jogadores.values()) {
						 jog.holo.hologramas_nao_atualizar.remove(bloco.getLocation());
						 jog.holo.AtualizarHoloTotalCaixas(jog.baus.size());
					 }


	        		 cancel();
	        	 }
	       	  double y = (radius * Math.sin(angle));
              double z = (radius * Math.cos(angle));
              angle -= 0.1;
              Location an = null;

				if ((direcao == BlockFace.NORTH) || (direcao == BlockFace.SOUTH))
				an = new Location(loc.getWorld(), loc.getX()+0.3 +z,loc.getY() + y,loc.getZ()+0.5);

				if ((direcao == BlockFace.EAST) || (direcao == BlockFace.WEST))
					an = new Location(loc.getWorld(), loc.getX()+0.5,loc.getY() + y,loc.getZ()+0.3+z);


             Particulas.CRIT_MAGIC.display(an);
	      	  tempo++;
	        }
	        }.runTaskTimer(UnixCaixa.getPlugin(), 0, 1);
		        

	      
	}
	 
	
}
