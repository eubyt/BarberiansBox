package unix.caixa.sistema.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import unix.caixa.sistema.animacao.UnixAnimacao;
import unix.caixa.sistema.animacao.lista.Padrao.Padrao;
import unix.caixa.sistema.animacao.lista.craft.Craft;
import unix.caixa.sistema.util.ItemStackUtil;



public enum BauType {

	CLASSICO(ItemStackUtil.criar(Material.ENDER_CHEST, "§6Caixa de Testes", new String[] { "§fCaixa para testes do Plugin" }), new Padrao(), new String[] { "TEST_1", "TEST_2", "TEST_3" }),
    CRAFT_1(ItemStackUtil.criar(Material.ENDER_CHEST, "§6Caixa de Testes", new String[] { "§fCaixa para testes do Plugin" }), new Craft(), new String[] { "TEST_1", "TEST_2", "TEST_3" });
	
   private ItemStack item;
   private UnixAnimacao animacao;	
   private String[] premios;
   
   BauType(ItemStack material, UnixAnimacao animacao, String[] premios) {
	   this.item = material;
	   this.animacao = animacao;
       this.premios = premios;
   }
   
   public ItemStack getItem() {
	   return item;
   }
   
   public UnixAnimacao getAnimacao() {
	   return this.animacao;
   }
   
   public String SortearPremio() {
	   ArrayList<String> lista = new ArrayList<>();
	   lista.addAll(Arrays.asList(this.premios));
	   
	   Random r = new Random();
	   return lista.get(r.nextInt(lista.size()));
   }
}
