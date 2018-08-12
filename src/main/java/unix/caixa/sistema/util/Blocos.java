package unix.caixa.sistema.util;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.material.Directional;
import org.bukkit.material.MaterialData;

public class Blocos {



    public static BlockFace getDirecao(Block bloco) {

        BlockState bl = bloco.getState();
        final MaterialData materialData = bl.getData();

        if (materialData instanceof Directional) {
            Directional direcao = (Directional) materialData;
            return direcao.getFacing();
        }

        return null;
    }
}
