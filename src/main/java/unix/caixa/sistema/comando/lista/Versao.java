package unix.caixa.sistema.comando.lista;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import unix.caixa.sistema.UnixCaixa;
import unix.caixa.sistema.comando.Comandos;
import unix.caixa.sistema.comando.ExecutarComando;
import unix.caixa.sistema.traducao.TraducaoUtil;

public class Versao extends Comandos {

    public Versao() {
        super("versao", "Ver a versão do plugin.", "caixar.admin.versao");
    }



    @Override
    public void Exe(CommandSender arg0, String arg1, String[] arg2) {

        Player jogador = (Player) arg0;
        jogador.sendMessage( TraducaoUtil.VersaoComando.replace("{versao}", UnixCaixa.versao_plugin));
    }

}
