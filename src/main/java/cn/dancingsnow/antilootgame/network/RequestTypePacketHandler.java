package cn.dancingsnow.antilootgame.network;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ru.timeconqueror.lootgames.api.block.SmartSubordinateBlock;
import ru.timeconqueror.lootgames.common.block.tile.MSMasterTile;
import ru.timeconqueror.lootgames.minigame.minesweeper.GameMineSweeper;
import ru.timeconqueror.lootgames.minigame.minesweeper.MSBoard;
import ru.timeconqueror.lootgames.minigame.minesweeper.Mark;
import ru.timeconqueror.lootgames.minigame.minesweeper.Type;
import ru.timeconqueror.lootgames.utils.future.BlockPos;

public class RequestTypePacketHandler implements IMessageHandler<RequestTypePacket, IMessage> {

    @Override
    public IMessage onMessage(RequestTypePacket message, MessageContext ctx) {
        EntityPlayer player = ctx.getServerHandler().playerEntity;
        BlockPos blockPos = message.getPos();
        if (blockPos != null && player instanceof EntityPlayerMP mp) {
            World world = mp.getEntityWorld();
            Block block = world.getBlock(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            if (block instanceof SmartSubordinateBlock) {
                BlockPos masterPos = SmartSubordinateBlock.getMasterPos(world, blockPos);
                TileEntity tileEntity = world.getTileEntity(masterPos.getX(), masterPos.getY(), masterPos.getZ());
                if (tileEntity instanceof MSMasterTile ms) {
                    GameMineSweeper game = ms.getGame();
                    if (game.isBoardGenerated()) {
                        MSBoard board = game.getBoard();
                        if (game.getStage() instanceof GameMineSweeper.StageWaiting stage && board != null) {
                            try {
                                board.forEach(p -> {
                                    if (board.getType(p) == Type.BOMB) {
                                        while (board.getMark(p) != Mark.FLAG) {
                                            stage.swapFieldMark(p);
                                        }
                                    } else if (mp.isSneaking()) {
                                        stage.revealField(mp, p);
                                    }
                                });
                            } catch (Exception ignore) {

                            }
                        }
                    } else {
                        player.addChatMessage(new ChatComponentText("The board is not generated yet!"));
                    }
                } else {
                    player.addChatMessage(new ChatComponentText("This Block is not a MineSweeper Master Tile!"));
                }
            } else {
                player.addChatMessage(new ChatComponentText("This Block is not a subordinate block!"));
            }
        }
        return null;
    }
}
