package unix.caixa.sistema.modulo.crafter;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import unix.caixa.sistema.UnixCaixa;
import unix.caixa.sistema.data.BauType;
import unix.caixa.sistema.modulo.confirmar.UnixMenuConfirmar;
import unix.caixa.sistema.util.ItemStackUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class UnixCraftCaixa {

    public HashMap<Integer, Adicionar> lista = new HashMap<>();

    public UnixCraftCaixa(Player jogador) {

        Inventory fabricar = Bukkit.createInventory(null, 36, "Fábrica de Caixa Misteriosa" );

        lista.put(11, new Adicionar(new ItemStack(Material.ENDER_CHEST ),"§aFábricar Caixa Misteriosa #1",450, new String[] {"§6teste 1", "§6teste 2", "§6teste3", "§6teste 4"}, new Craftar() {
            @Override
            public void Executar() {

                UnixCaixa.jogadores.get(jogador).AdicionarBau(BauType.CRAFT_1, 1);

            }
        }));

        //lista.put(13, new Adicionar(new ItemStack(Material.ENDER_CHEST), "§aFábricar Caixa Misteriosa #2",1250, new String[] {"§6teste 1", "§6teste 2", "§6teste3", "§6teste 4"}));
        //lista.put(15, new Adicionar(new ItemStack(Material.ENDER_CHEST), "§aFábricar Caixa Misteriosa #3",1250, new String[] {"§6teste 1", "§6teste 2", "§6teste3", "§6teste 4"}));

        for (int add : lista.keySet()) {
            Adicionar a = lista.get(add);
            List<String> desc_lore = new ArrayList<>();
            desc_lore.addAll(Arrays.asList("§7Contém itens exclusivos.", ""));
            desc_lore.addAll(Arrays.asList(a.itens_exclusivos));
            desc_lore.addAll(Arrays.asList("", "§7Valor: §a" + a.valor + " §bFragmentos Mágicos"));

            if (UnixCaixa.jogadores.get(jogador).fragmentos_magicos < a.valor)
                desc_lore.add("§cVocê não tem fragmentos mágicos suficiente.");
            else
                desc_lore.add("§eClick para comprar este item");

            ItemStack item = ItemStackUtil.criar(a.item.getType(), a.titulo);
            ItemMeta meta = item.getItemMeta();
            meta.setLore(desc_lore);
            item.setItemMeta(meta);

            fabricar.setItem(add, item);
        }

        jogador.openInventory(fabricar);
    }


    public class Adicionar {

        public ItemStack item;
        public  String titulo;
        public int valor;
        public String[] itens_exclusivos;
        public Craftar craft;

        public Adicionar(ItemStack item, String titulo ,int valor, String[] itens, Craftar craft) {
            this.item = item;
            this.titulo = titulo;
            this.valor = valor;
            this.itens_exclusivos = itens;
            this.craft = craft;
        }
    }


    public abstract static class Craftar {
        public void Executar() {}
    }

}
