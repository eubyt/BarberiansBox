package unix.caixa.sistema.comando.lista;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import unix.caixa.sistema.UnixCaixa;
import unix.caixa.sistema.comando.Comandos;
import unix.caixa.sistema.comando.ExecutarComando;
import unix.caixa.sistema.traducao.TraducaoUtil;

import java.util.HashMap;
import java.util.Map;

public class Lista extends Comandos {

    public Lista() {
        super("lista", "Ver todas as caixas.", "caixar.admin.lista");
    }



    @Override
    public void Exe(CommandSender arg0, String arg1, String[] arg2) {

        Player jogador = (Player) arg0;

        HashMap<String, Location> CaixaLista = UnixCaixa.database.CarregarCaixas();
        jogador.sendMessage(TraducaoUtil.CaixaListaComandosTitulo);
        int valor = 1;
        for (Map.Entry<String, Location> caixas : CaixaLista.entrySet()) {

            String msg = TraducaoUtil.CaixaListaComandosLista.replace("{numero}", String.valueOf(valor));
            msg = msg.replace("{caixaid}", caixas.getKey());
            TextComponent caixa = new TextComponent(msg);

            caixa.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/caixa remover " + caixas.getKey()));

            String cord = "X: " + caixas.getValue().getX() + ", Y: " + caixas.getValue().getY() + ", Z: " + caixas.getValue().getZ();
            String mundo = caixas.getValue().getWorld().getName();

            String msg2 = TraducaoUtil.CaixaListaComandosListaShow.replace("{cord", cord);
            msg2 = msg2.replace("{mundo}", mundo);
            msg2 = msg2.replace("{caixaid}", caixas.getKey());

            caixa.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(msg2).create()));

            jogador.spigot().sendMessage(caixa);
            valor++;
        }
        jogador.sendMessage(TraducaoUtil.CaixaListaComandosListaTotalCaixas.replace("{totalcaixa}", String.valueOf(CaixaLista.size())));

    }

}
