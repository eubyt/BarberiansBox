package unix.caixa.sistema.animacao.lista.Padrao;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import unix.caixa.sistema.animacao.UnixAnimacao;
import unix.caixa.sistema.animacao.lista.Padrao.execute.PadraoExecute;
import unix.caixa.sistema.data.BauType;

public class Padrao implements UnixAnimacao {


	
	public void animacao_open(Block bloco, Player jogador, BauType BauType) {
	

	    new PadraoExecute(bloco, jogador, BauType);
	}
	
	
	
	
	 
	
}
