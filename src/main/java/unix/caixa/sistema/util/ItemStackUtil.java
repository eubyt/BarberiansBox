package unix.caixa.sistema.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class ItemStackUtil {

	public static ItemStack criar(Material material) {
		return criar(material, 1, 0, null, null, false, null, false);
	}

	public static ItemStack criar(Material material, String nome) {
		return criar(material, 1, 0, nome, null, false, null, false);
	}

	public static ItemStack criar(Material material, String nome, String[] descricao) {
		return criar(material, 1, 0, nome, descricao, false, null, false);
	}

	public static ItemStack criar(Material material, String nome, String[] descricao, boolean inquebravel,
			Encantamento encantamento) {
		return criar(material, 1, 0, nome, descricao, inquebravel, encantamento, false);
	}

	public static ItemStack criar(Material material, String nome, String[] descricao, boolean inquebravel,
			boolean efeitoencatamento) {
		return criar(material, 1, 0, nome, descricao, inquebravel, null, efeitoencatamento);
	}

	public static ItemStack criar(Material material, String nome, String[] descricao, boolean inquebravel) {
		return criar(material, 1, 0, nome, descricao, inquebravel, null, false);
	}

	public static ItemStack criar(Material material, int id) {
		return criar(material, 1, id, null, null, false, null, false);
	}

	public static ItemStack criar(Material material, int id, String nome) {
		return criar(material, 1, id, nome, null, false, null, false);
	}

	public static ItemStack criar(Material material, int id, String nome, String[] descricao) {
		return criar(material, 1, id, nome, descricao, false, null, false);
	}

	public static ItemStack criar(Material material, int id, String nome, String[] descricao, boolean inquebravel) {
		return criar(material, 1, id, nome, descricao, inquebravel, null, false);
	}

	public static ItemStack criar(Material material, int id, String nome, String[] descricao, boolean inquebravel,
			Encantamento encantamento) {
		return criar(material, 1, id, nome, descricao, inquebravel, encantamento, false);
	}

	public static ItemStack criar(Material material, int id, String nome, String[] descricao, boolean inquebravel,
			boolean efeitoencatamento) {
		return criar(material, 1, id, nome, descricao, inquebravel, null, false);
	}

	public static ItemStack criar(Material material, int quantidade, int id) {
		return criar(material, quantidade, id, null, null, false, null, false);
	}

	public static ItemStack criar(Material material, int quantidade, int id, String nome) {
		return criar(material, quantidade, id, nome, null, false, null, false);
	}

	public static ItemStack criar(Material material, int quantidade, int id, String nome, String[] descricao) {
		return criar(material, quantidade, id, nome, descricao, false, null, false);
	}

	public static ItemStack criar(Material material, int quantidade, int id, String nome, String[] descricao,
			boolean inquebravel) {
		return criar(material, quantidade, id, nome, descricao, inquebravel, null, false);
	}

	public static ItemStack criar(Material material, int quantidade, int id, String nome, String[] descricao,
			boolean inquebravel, Encantamento encantamento) {
		return criar(material, quantidade, id, nome, descricao, inquebravel, encantamento, false);
	}

	public static ItemStack criar(Material material, int quantidade, int id, String nome, String[] descricao,
			boolean inquebravel, boolean efeitoencatamento) {
		return criar(material, quantidade, id, nome, descricao, inquebravel, null, efeitoencatamento);
	}
	
	
	public static ItemStack createSkull(String url) {
		ItemStack head = new ItemStack(Material.SKULL_ITEM, 1 ,(byte) 3);
		
		SkullMeta headMeta = (SkullMeta) head.getItemMeta();
		headMeta.setOwner("UnixCF");
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		profile.getProperties().put("textures", new Property("textures", url));
		Field profileField;
		try {
			profileField = headMeta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(headMeta, profile);
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
		head.setItemMeta(headMeta);
		return head;
}

	public static ItemStack criar(Material material, int quantidade, int id, String nome, String[] descricao,
			boolean inquebravel, Encantamento encantamento, boolean efeitoencatamento) {
		ItemStack is = new ItemStack(material, quantidade, (short) id);
		CriarItem item = new CriarItem(is);
		if (nome != null)
			item.nome(nome);
		if (descricao != null)
			item.descricao(descricao);
		item.inquebravel(inquebravel);
		if (efeitoencatamento)
			item.efeitoEncantamento();
		if (encantamento != null)
			item.encantamento(encantamento.tipo_encantamento, encantamento.level);
		item.Criar();
		return item.itemstack;
	}

	public static class Encantamento {

		public Enchantment tipo_encantamento;
		public int level;

		public Encantamento(Enchantment tipo, int level) {
			this.tipo_encantamento = tipo;
			this.level = level;
		}

	}

	public static class CriarItem {

		private ItemStack itemstack;
		private ItemMeta itemMeta;

		public CriarItem(ItemStack material) {
			this.itemstack = material;
			this.itemMeta = itemstack.getItemMeta();
		}

		public void nome(String nome) {
			this.itemMeta.setDisplayName(nome);
		}

		public void descricao(String[] desc) {
			this.itemMeta.setLore(Arrays.asList(desc));
		}

		public void encantamento(Enchantment encantamento, int level) {
			this.itemMeta.addEnchant(encantamento, level, true);
		}

		public void inquebravel(boolean sim) {
			this.itemMeta.spigot().setUnbreakable(sim);
		}

		public void AdicionarFlag(ItemFlag flags) {
			this.itemMeta.addItemFlags(flags);
		}

		public void Criar() {
			this.itemstack.setItemMeta(this.itemMeta);
		}

		public ItemStack getItemStack() {
			return this.itemstack;
		}

		public void efeitoEncantamento() {
			encantamento(Enchantment.DURABILITY, 1);
			AdicionarFlag(ItemFlag.HIDE_ENCHANTS);
		}

	}

}
