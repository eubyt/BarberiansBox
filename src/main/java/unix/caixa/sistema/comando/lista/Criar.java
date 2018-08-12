package unix.caixa.sistema.comando.lista;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import unix.caixa.sistema.UnixCaixa;
import unix.caixa.sistema.comando.Comandos;
import unix.caixa.sistema.comando.ExecutarComando;

public class Criar extends Comandos {

    public Criar() {
        super("criar", "Criar uma caixa.", "caixar.admin.criar");
    }



    @Override
    public void Exe(CommandSender arg0, String arg1, String[] arg2) {

        Player jogador = (Player) arg0;
        UnixCaixa.um.CriarCaixa(jogador.getLocation(), jogador);
    }

}
