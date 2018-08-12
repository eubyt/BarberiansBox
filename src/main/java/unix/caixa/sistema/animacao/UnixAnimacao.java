package unix.caixa.sistema.animacao;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import unix.caixa.sistema.data.BauType;

public interface UnixAnimacao {

	public abstract void animacao_open(Block bloco, Player jogador, BauType BauType);
    
}
