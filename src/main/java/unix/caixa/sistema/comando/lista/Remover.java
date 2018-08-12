package unix.caixa.sistema.comando.lista;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import unix.caixa.sistema.UnixCaixa;
import unix.caixa.sistema.comando.Comandos;
import unix.caixa.sistema.traducao.TraducaoUtil;

public class Remover extends Comandos {

    public Remover() {
        super("remover", "Remover uma caixa.", "caixar.admin.remover");
    }



    @Override
    public void Exe(CommandSender arg0, String arg1, String[] arg2) {

        Player jogador = (Player) arg0;

        if (arg2.length < 2)
        {
            jogador.sendMessage(TraducaoUtil.CaixaListaComandosRemoverInformar);
            return;
        }

        boolean check = UnixCaixa.database.RemoverCaixa(arg2[1]);
        if (check)
            jogador.sendMessage(TraducaoUtil.CaixaListaComandosRemover.replace("{caixaid}", arg2[1]));
        else
            jogador.sendMessage(TraducaoUtil.CaixaListaComandosRemoverInformarNaoEncontrado.replace("{caixaid}", arg2[1]));

    }

}
