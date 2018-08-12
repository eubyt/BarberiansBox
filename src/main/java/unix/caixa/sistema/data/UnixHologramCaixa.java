package unix.caixa.sistema.data;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import unix.caixa.sistema.UnixCaixa;
import unix.caixa.sistema.traducao.TraducaoUtil;
import unix.caixa.sistema.util.Holograma;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UnixHologramCaixa {

    public HashMap<Location, Holograma> holo = new HashMap<>();
    public ArrayList<Location> hologramas_nao_atualizar = new ArrayList<>();
    public Player jogador;
    public String cor = "§c";

    public UnixHologramCaixa(Player jogador) {

        this.jogador = jogador;

    }



    public void AtualizarHoloTotalCaixas(int total) {
        RemoverHoloTotalCaixas();
        CriarHoloTotalCaixas(total);
    }


    public void AtualizarListaHoloTotalCaixas(int total) {
        RemoverHoloTotalCaixas();

        if (cor.contains("§c"))
            cor = "§f";
        else
            cor = "§c";

        if (total == 0)
            return;

        for (Map.Entry<String, Location> caixas : UnixCaixa.CaixaLista.entrySet()) {

            Block bloco = caixas.getValue().add(0,0,0).getBlock();

            if (!hologramas_nao_atualizar.contains(bloco.getLocation())) {
                String texto =  TraducaoUtil.HologramaNumeroCaixas.replace("{numero_caixas}",  String.valueOf(total));
                Holograma holo = new Holograma(bloco.getLocation().add(0.5D, -0.40, 0.5D), cor + texto);
                this.holo.put(bloco.getLocation(), holo);
                holo.Mostrar(this.jogador);
            }
        }
    }

    public void CriarHoloTotalCaixas(int total) {

        if (UnixCaixa.CaixaLista == null)
            return;

        if (total == 0)
            return;

        for (Map.Entry<String, Location> caixas : UnixCaixa.CaixaLista.entrySet()) {

            Block bloco = caixas.getValue().add(0,0,0).getBlock();
            String texto =  TraducaoUtil.HologramaNumeroCaixas.replace("{numero_caixas}",  String.valueOf(total));
            Holograma holo = new Holograma(bloco.getLocation().add(0.5D,-0.40,0.5D),   "§c"+ texto);
            this.holo.put(bloco.getLocation(), holo);
            holo.Mostrar(this.jogador);

        }
    }

    public void RemoverHoloTotalCaixas() {
        for (Holograma h : holo.values())
            h.RemoverPara(this.jogador);

        holo.clear();

    }

}
