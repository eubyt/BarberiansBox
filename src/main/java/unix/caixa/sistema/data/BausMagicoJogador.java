package unix.caixa.sistema.data;

import java.util.*;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import unix.caixa.sistema.UnixCaixa;
import unix.caixa.sistema.data.array.DataBau;
import unix.caixa.sistema.data.json.InfoBaus;
import unix.caixa.sistema.data.json.JsonBau;
import unix.caixa.sistema.util.Holograma;
import unix.caixa.sistema.util.Tempo;

public class BausMagicoJogador {
	
	public ArrayList<DataBau> baus = new ArrayList<DataBau>();
	
	public HashMap<String, BauType> itemstack = new HashMap<String, BauType>();

	public HashMap<String, DataBau> itemstackDataBau = new HashMap<String, DataBau>();

	public Inventory menu_baus;
	
	public Block bloco;

	public boolean atualizar_baus = false;

	public Player jogador;

	public UnixHologramCaixa holo;

	public int fragmentos_magicos = 4000;

	public BausMagicoJogador(Player jogador, String Json) {

		this.jogador = jogador;
		if (Json.equals("Nenhum")) {

			//Ignorar código daqui para baixo se for nenhum...
			return;
		}

		ObjectMapper mapper = new ObjectMapper();

		try {

		JsonBau teste = new JsonBau();

		String jsonInString = mapper.writeValueAsString(teste);

		JsonBau obj = mapper.readValue(jsonInString, JsonBau.class);

		for (InfoBaus inf : obj.baus) {
			DataBau data = new DataBau();

			data.tipobau = BauType.valueOf(inf.nome);
			data.data = inf.data;

			if (!Tempo.calcular_vencimento(inf.data))
				baus.add(data);

		}

   	    //baus.add(BauType.CLASSICO);
		//baus.add(BauType.CLASSICO);

		holo = new UnixHologramCaixa(jogador);
		holo.AtualizarHoloTotalCaixas(baus.size());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	public String gerarJson() {
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = null;

		try {
		JsonBau json = new JsonBau();

			for (DataBau inf : baus) {
				InfoBaus bau = new InfoBaus();
				bau.nome = inf.tipobau.toString();
				bau.data = inf.data;
				json.baus.add(bau);
			}

		jsonInString = mapper.writeValueAsString(json);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return jsonInString;
	}



	public void AdicionarBau(BauType tipo, int dias) {

		DataBau data = new DataBau();
		Calendar cal = Tempo.getCalendario();
		//cal.add(cal.DAY_OF_MONTH, +dias);
		cal.add(cal.MINUTE, +dias);
		Date agora = cal.getTime();
		data.data = Tempo.data.format(agora);
		data.tipobau = tipo;

		baus.add(data);
	}


	public void AtualizarTempoBaus() {

		ArrayList<DataBau> desativar = new ArrayList<>();

		for (DataBau baus : UnixCaixa.jogadores.get(jogador).baus) {
			if (Tempo.calcular_vencimento(baus.data))
                  desativar.add(baus);
		}


		for (DataBau baus : desativar)
			this.baus.remove(baus);

	}

}
