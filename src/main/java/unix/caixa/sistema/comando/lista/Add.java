package unix.caixa.sistema.comando.lista;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import unix.caixa.sistema.UnixCaixa;
import unix.caixa.sistema.comando.Comandos;
import unix.caixa.sistema.data.BauType;
import unix.caixa.sistema.traducao.TraducaoUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public class Add extends Comandos {

    public Add() {
        super("adicionar", "Adicionar uma caixa ao jogador", "caixar.admin.adicionar");
    }



    @Override
    public void Exe(CommandSender arg0, String arg1, String[] arg2) {

        Player jogador = (Player) arg0;
        if (arg2.length < 3)
        {
            jogador.sendMessage(TraducaoUtil.CaixaAdicionarComandoInfo);
            return;
        }

        if (!carregarAtributos().contains(arg2[2])) {
            jogador.sendMessage(TraducaoUtil.CaixaTipoDeBauNaoExiste.replace("{nome}", arg2[2]));
            return;

        }
        Player alvo = Bukkit.getPlayerExact(arg2[1]);
        BauType bau = BauType.valueOf(arg2[2]);

        if ((alvo == null) || (!alvo.isOnline()))
        {
            jogador.sendMessage(TraducaoUtil.CaixaJogadorNaoOnline.replace("{nome}", arg2[1]));
            return;
        }

        jogador.sendMessage(TraducaoUtil.CaixaAdicionarJogador.replace("{nome_bau}", bau.name()).replace("{nome_jogador}", alvo.getName()));
        UnixCaixa.jogadores.get(alvo).AdicionarBau(bau, 1);

    }

    public List<String> carregarAtributos() {
        List<BauType> lista = Arrays.asList(BauType.values());
        List<String> retorno = new ArrayList<String>();
        for (int i = 0; i < lista.size(); i++) {
            retorno.add(lista.get(i).name());
        }
        return retorno;
    }

}
