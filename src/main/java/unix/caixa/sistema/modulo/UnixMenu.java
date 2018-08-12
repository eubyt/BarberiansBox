package unix.caixa.sistema.modulo;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import unix.caixa.sistema.UnixCaixa;
import unix.caixa.sistema.data.BauType;
import unix.caixa.sistema.data.array.DataBau;
import unix.caixa.sistema.traducao.TraducaoUtil;
import unix.caixa.sistema.util.ItemStackUtil;
import unix.caixa.sistema.util.Tempo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class UnixMenu {
	public UnixMenu(Player jogador, Block bloco) {
		Inventory inventario = Bukkit.createInventory(null, 54, TraducaoUtil.NomeCaixaBau);
		UnixCaixa.jogadores.get(jogador).menu_baus = inventario;

		try {

			if (UnixCaixa.jogadores.get(jogador).baus.size() <= 0) {
				SemBaus(jogador);
			//	UnixCaixa.jogadores.get(jogador).RemoverHoloTotalCaixas();
			}
			else {
				CarregarBaus(jogador);

				new BukkitRunnable() {
					public float i = 0.1F;
					public void run(){

						if ((jogador == null) || (!jogador.isOnline()))
						     cancel();

						if (UnixCaixa.jogadores.get(jogador).baus.size() <= 0) {
							SemBaus(jogador);
							cancel();
						}
						else
							CarregarBaus(jogador);

						if (!UnixCaixa.jogadores.get(jogador).atualizar_baus)
							cancel();

					}
				}.runTaskTimer(UnixCaixa.getPlugin(), 0, 20);

			}
		} catch (Exception ex) {
			ERRO(jogador);
			UnixCaixa.Carregar(jogador);
		}


		ItemStack craft = ItemStackUtil.criar(Material.ANVIL, "§aFabricar Caixa Misteriosa", new String[] {"§7Sempre que você encontrar itens que", "§7você já tem, você receberá §bFragmentos", "§bMágicos. §7Com eles você consegue criar ", "§7caixas com itens exclusivos.", "", "§7Você tem: §b" + UnixCaixa.jogadores.get(jogador).fragmentos_magicos +" Fragmentos Mágicos", "§eClick para produzir."});


		UnixCaixa.jogadores.get(jogador).menu_baus.setItem(50, craft);
		jogador.openInventory(UnixCaixa.jogadores.get(jogador).menu_baus);
		UnixCaixa.jogadores.get(jogador).bloco = bloco;



	}

	public void SemBaus(Player jogador) {
		UnixCaixa.jogadores.get(jogador).menu_baus.setItem(22, ItemStackUtil.criar(Material.STAINED_GLASS_PANE, 14, TraducaoUtil.NomeItemQuandoNaoTemNadaParaAbrir, TraducaoUtil.DescItemQuandoNaoTemNadaParaAbrir));
	}

	public void ERRO(Player jogador) {
		UnixCaixa.jogadores.get(jogador).menu_baus.setItem(22, ItemStackUtil.criar(Material.STAINED_GLASS_PANE, 14, TraducaoUtil.NomeItemErro, TraducaoUtil.DescItemErro));
	}

	public void CarregarBaus(Player jogador) {
		int slot = 0;
		UnixCaixa.jogadores.get(jogador).itemstack.clear();
		UnixCaixa.jogadores.get(jogador).itemstackDataBau.clear();

		UnixCaixa.jogadores.get(jogador).AtualizarTempoBaus();

		for (DataBau baus : UnixCaixa.jogadores.get(jogador).baus) {

			if (Tempo.calcular_vencimento(baus.data))
			{
				UnixCaixa.jogadores.get(jogador).itemstack.put(baus.tipobau.getItem().getItemMeta().getDisplayName(), baus.tipobau);
				UnixCaixa.jogadores.get(jogador).menu_baus.setItem(slot, new ItemStack(Material.AIR));

			} else {
				ItemMeta backup = baus.tipobau.getItem().getItemMeta();
				ItemMeta modificar = baus.tipobau.getItem().getItemMeta();

				List desc = modificar.getLore();
				desc.addAll(Arrays.asList("", "§cExpira em " + Tempo.calcular(baus.data)));

				modificar.setLore(desc);
				int numero = slot+1;
				modificar.setDisplayName("§e" + (slot+1) + "# " + modificar.getDisplayName());
				baus.tipobau.getItem().setItemMeta(modificar);

				UnixCaixa.jogadores.get(jogador).menu_baus.setItem(slot, baus.tipobau.getItem());
				slot++;

				UnixCaixa.jogadores.get(jogador).itemstackDataBau.put(baus.tipobau.getItem().getItemMeta().getDisplayName(), baus);
				UnixCaixa.jogadores.get(jogador).itemstack.put(baus.tipobau.getItem().getItemMeta().getDisplayName(), baus.tipobau);
				baus.tipobau.getItem().setItemMeta(backup);

				if (slot == 45)
					slot = 0;
			}
		}



	}

}
