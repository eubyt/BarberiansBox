package unix.caixa.sistema.modulo.confirmar;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import unix.caixa.sistema.UnixCaixa;
import unix.caixa.sistema.animacao.UnixAnimacao;
import unix.caixa.sistema.data.BausMagicoJogador;
import unix.caixa.sistema.evento.UnixEvento;
import unix.caixa.sistema.util.ItemStackUtil;

public class UnixMenuConfirmar {

    private Player jogador;
    public ItemStack confirmar_botao = ItemStackUtil.criar(Material.STAINED_CLAY,  13, "§a§lCONFIRMAR");
    public ItemStack cancelar_botao = ItemStackUtil.criar(Material.STAINED_CLAY , 14, "§c§lCANCELAR");
    public ItemStack item_executando;

    public UnixMenuConfirmar(Player jogador, ItemStack item_executando) {
        this.jogador = jogador;
        this.item_executando = item_executando;

        UnixEvento.jogadores_inventario_aberto.remove(jogador);
        UnixCaixa.jogadores.get(jogador).atualizar_baus = false;

        Inventory confirmar = Bukkit.createInventory(null, 45, "Confirmar");

        confirmar.setItem(13, this.item_executando);
        confirmar.setItem(29, this.confirmar_botao);
        confirmar.setItem(33, this.cancelar_botao);

        jogador.openInventory(confirmar);

    }

}
