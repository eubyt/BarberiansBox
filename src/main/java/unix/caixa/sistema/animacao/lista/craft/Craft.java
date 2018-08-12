package unix.caixa.sistema.animacao.lista.craft;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import unix.caixa.sistema.animacao.UnixAnimacao;
import unix.caixa.sistema.animacao.lista.craft.execute.CraftExecute;
import unix.caixa.sistema.data.BauType;

public class Craft implements UnixAnimacao {



    public void animacao_open(Block bloco, Player jogador, BauType BauType) {

//
        new CraftExecute(bloco, jogador, BauType);
    }






}
