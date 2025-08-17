package cn.dancingsnow.antilootgame.waila;

import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import ru.timeconqueror.lootgames.api.block.SmartSubordinateBlock;
import ru.timeconqueror.lootgames.api.util.Pos2i;
import ru.timeconqueror.lootgames.common.block.tile.MSMasterTile;
import ru.timeconqueror.lootgames.minigame.minesweeper.GameMineSweeper;
import ru.timeconqueror.lootgames.minigame.minesweeper.Type;
import ru.timeconqueror.lootgames.utils.future.BlockPos;

public class WailaCompat implements IWailaDataProvider {

    public static final WailaCompat INSTANCE = new WailaCompat();

    private WailaCompat() {}

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        currenttip.add("<Type>");
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        MovingObjectPosition position = accessor.getPosition();
        if (position.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            World world = accessor.getWorld();
            BlockPos pos = SmartSubordinateBlock.getMasterPos(world, new BlockPos(position.blockX, position.blockY, position.blockZ));
            TileEntity tileEntity = world.getTileEntity(pos.getX(), pos.getY(), position.blockZ);
        }
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x,
        int y, int z) {
        if (te instanceof MSMasterTile ms) {
            GameMineSweeper msGame = ms.getGame();
            if (msGame.isBoardGenerated()) {
                Pos2i gamePos = msGame.convertToGamePos(ms.getBlockPos());
                tag.setByte(
                    "type",
                    msGame.getBoard()
                        .getType(gamePos)
                        .getId());
            }
        }
        return tag;
    }
}
