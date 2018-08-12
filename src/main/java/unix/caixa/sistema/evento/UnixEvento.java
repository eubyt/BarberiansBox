package unix.caixa.sistema.evento;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import unix.caixa.UnixListener;
import unix.caixa.sistema.UnixCaixa;
import unix.caixa.sistema.animacao.UnixAnimacao;
import unix.caixa.sistema.data.BausMagicoJogador;
import unix.caixa.sistema.data.UnixCaixaData;
import unix.caixa.sistema.modulo.UnixMenu;
import unix.caixa.sistema.modulo.confirmar.UnixMenuConfirmar;
import unix.caixa.sistema.modulo.crafter.UnixCraftCaixa;
import unix.caixa.sistema.traducao.TraducaoUtil;

public class UnixEvento extends UnixListener {

	public static HashMap<Block, UnixCaixaData> data = new HashMap<>();
	public static HashMap<Player, UnixMenuConfirmar> menu_confirmar = new HashMap<>();

	public static HashMap<Player, UnixCraftCaixa> menu_craft = new HashMap<>();
	public static HashMap<Player, UnixCraftCaixaMenu> menu_craft_confirmar = new HashMap<Player, UnixCraftCaixaMenu>();

    public static ArrayList<Player> jogadores_inventario_aberto = new ArrayList<>();
	public static ArrayList<Block> caixas_sendo_abertas = new ArrayList<Block>();
    
	public UnixEvento(JavaPlugin plugin) {
		super(plugin);
	}

	@EventHandler
	public void Caixa(PlayerInteractEvent evento) {
		
		if (data.containsKey(evento.getClickedBlock())) {
			evento.setCancelled(true);
			new UnixMenu(evento.getPlayer(), evento.getClickedBlock());
		}
	}

	@EventHandler
	public void inventariobaus(InventoryClickEvent evento) {
		Player jogador = (Player) evento.getWhoClicked();

		if (this.jogadores_inventario_aberto.contains(jogador))
			evento.setCancelled(true);

		
		if (evento.getInventory().getType() != InventoryType.CHEST)
		return;
			
		if(!UnixCaixa.jogadores.containsKey(jogador))
		return;

		if ((evento.getCurrentItem() == null) ||(evento.getCurrentItem().getItemMeta() == null))
			return;


		if (evento.getCurrentItem().getItemMeta().getDisplayName() == "§aFabricar Caixa Misteriosa") {
			evento.setCancelled(true);
			menu_craft.put(jogador, new UnixCraftCaixa(jogador));
			jogadores_inventario_aberto.add(jogador);
			return;
		}

		if ((evento.getClickedInventory().getName() != null) && (evento.getClickedInventory().getName().equals("Fábrica de Caixa Misteriosa"))) {
			evento.setCancelled(true);
			UnixCraftCaixa ucc =  menu_craft.get(jogador);

			if (ucc.lista.containsKey(evento.getSlot())) {

				UnixCraftCaixa.Adicionar craft = ucc.lista.get(evento.getSlot());
				UnixMenuConfirmar menuconfirmar = new UnixMenuConfirmar(jogador, evento.getCurrentItem());
				menu_craft_confirmar.put(jogador, new UnixCraftCaixaMenu(craft, menuconfirmar));

			}

			return;
		}

		if (menu_craft_confirmar.containsKey(jogador)) {
			evento.setCancelled(true);

			if (evento.getCurrentItem().equals(menu_craft_confirmar.get(jogador).menu.confirmar_botao)) {

				UnixCraftCaixa.Adicionar craft = menu_craft_confirmar.get(jogador).ucc;
				if (craft.valor > UnixCaixa.jogadores.get(jogador).fragmentos_magicos)  {
					jogador.sendMessage("§cVocê não pode comprar este item");
					System.out.println(UnixCaixa.jogadores.get(jogador).fragmentos_magicos);
					System.out.println(craft.valor);
					return;
				}

				craft.craft.Executar();
				jogador.sendMessage("§cVocê comprou o " + craft.titulo);
				jogador.closeInventory();

				return;
			}

			if (evento.getCurrentItem().equals(menu_craft_confirmar.get(jogador).menu.cancelar_botao))
				jogador.openInventory(UnixCaixa.jogadores.get(jogador).menu_baus);


			return;
		}


		if (menu_confirmar.containsKey(jogador)) {
			evento.setCancelled(true);

			if (evento.getCurrentItem().equals(menu_confirmar.get(jogador).confirmar_botao)) {
						ItemStack click = menu_confirmar.get(jogador).item_executando;
						UnixEvento.jogadores_inventario_aberto.add(jogador);
						UnixCaixa.jogadores.get(jogador).atualizar_baus = true;

						BausMagicoJogador uc = UnixCaixa.jogadores.get(jogador);
						UnixAnimacao an = uc.itemstack.get(click.getItemMeta().getDisplayName()).getAnimacao();

						if (uc.baus.contains(uc.itemstackDataBau.get(click.getItemMeta().getDisplayName()))) {
							an.animacao_open(uc.bloco, jogador, uc.itemstack.get(click.getItemMeta().getDisplayName()));
							uc.baus.remove(uc.itemstackDataBau.get(click.getItemMeta().getDisplayName()));
							uc.itemstack.remove(jogador);
							UnixEvento.caixas_sendo_abertas.add(uc.bloco);
						} else {
							jogador.sendMessage("Este baú expirou");
						}
						if (UnixEvento.jogadores_inventario_aberto.contains(jogador)) {
							UnixEvento.jogadores_inventario_aberto.remove(jogador);
							UnixCaixa.jogadores.get(jogador).atualizar_baus = false;
						}

						UnixEvento.menu_confirmar.remove(jogador);

						jogador.closeInventory();
			             return;

			}

			if (evento.getCurrentItem().equals(menu_confirmar.get(jogador).cancelar_botao))
						jogador.openInventory(UnixCaixa.jogadores.get(jogador).menu_baus);

			return;
		}

		BausMagicoJogador uc = UnixCaixa.jogadores.get(jogador);

		if ((evento.getClickedInventory() == null) || (uc.menu_baus == null))
			return;

		if (uc.menu_baus.equals(evento.getClickedInventory())) {
		   if (uc.itemstack.containsKey(evento.getCurrentItem().getItemMeta().getDisplayName())) {
			   jogador.closeInventory();
			   
				if (caixas_sendo_abertas.contains(uc.bloco)) {
					jogador.sendMessage(TraducaoUtil.CaixaSendoAberta);
					return;
				}

				UnixMenuConfirmar menuconfirmar = new UnixMenuConfirmar(jogador, evento.getCurrentItem());
				menu_confirmar.put(jogador, menuconfirmar);
			   

			   //Remover o BAU do jogador
			   //uc.baus.remove(uc.itemstack.get(evento.getCurrentItem().getItemMeta().getDisplayName()));

		   }
		   else {


		   }
		}
		
		
		
	}
	
	@EventHandler
	public void InventarioAbrir(InventoryOpenEvent e)
	{
		if (e.getInventory().getType() != InventoryType.CHEST)
		return;
		
		if(!UnixCaixa.jogadores.containsKey(e.getPlayer()))
			return;
		
		try {

		if ((UnixCaixa.jogadores.get(e.getPlayer()).menu_baus != null) && (UnixCaixa.jogadores.get(e.getPlayer()).menu_baus.equals(e.getInventory())))
			jogadores_inventario_aberto.add((Player) e.getPlayer());
			UnixCaixa.jogadores.get((Player) e.getPlayer()).atualizar_baus = true;
		} catch (Exception ex) {
			e.getPlayer().sendMessage(TraducaoUtil.MsgErro);
			ex.printStackTrace();
		}

	}

	@EventHandler
	public void InventarioFechar(InventoryCloseEvent e)
	{
		if (e.getInventory().getType() != InventoryType.CHEST)
			return;
			
			if(!UnixCaixa.jogadores.containsKey(e.getPlayer()))
				return;

			try {

				if ((UnixCaixa.jogadores.get(e.getPlayer()).menu_baus != null) && (UnixCaixa.jogadores.get(e.getPlayer()).menu_baus.equals(e.getInventory()))) {
				jogadores_inventario_aberto.remove((Player) e.getPlayer());
				UnixCaixa.jogadores.get((Player) e.getPlayer()).atualizar_baus = false;
			}

			} catch (Exception ex) {
				e.getPlayer().sendMessage(TraducaoUtil.MsgErro);
				ex.printStackTrace();
			}
			if (menu_confirmar.containsKey((Player) e.getPlayer()))
				menu_confirmar.remove((Player) e.getPlayer());

			if (jogadores_inventario_aberto.contains((Player) e.getPlayer()))
				jogadores_inventario_aberto.remove((Player) e.getPlayer());

			if (menu_craft.containsKey((Player) e.getPlayer()))
				menu_craft.remove((Player) e.getPlayer());

			if (menu_craft_confirmar.containsKey((Player) e.getPlayer()))
				menu_craft_confirmar.remove((Player) e.getPlayer());

	}
	
	@EventHandler
	public void JogadorEntrar(PlayerJoinEvent evento) {
		UnixCaixa.Carregar(evento.getPlayer());

	}

	@EventHandler
	public void JogadorSair(PlayerQuitEvent evento) {
		System.out.println(UnixCaixa.jogadores.get(evento.getPlayer()).gerarJson()); //Gerar Json pra salvar as caixas

		if (UnixEvento.jogadores_inventario_aberto.contains(evento.getPlayer())) {
			UnixEvento.jogadores_inventario_aberto.remove(evento.getPlayer());
			UnixCaixa.jogadores.get(evento.getPlayer()).atualizar_baus = false;
		}

		UnixCaixa.jogadores.remove(evento.getPlayer());
	}

	public static class UnixCraftCaixaMenu {

		public UnixCraftCaixa.Adicionar ucc = null;
		public UnixMenuConfirmar menu = null;

		public UnixCraftCaixaMenu(UnixCraftCaixa.Adicionar ucc, UnixMenuConfirmar menu) {
			this.ucc = ucc;
			this.menu = menu;
		}

	}
}
