package cn.dancingsnow.antilootgame.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import ru.timeconqueror.lootgames.utils.future.BlockPos;

public class RequestTypePacket implements IMessage {

    private BlockPos pos;

    public RequestTypePacket() {}

    public RequestTypePacket(BlockPos blockPos) {
        pos = blockPos;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();
        pos = new BlockPos(x, y, z);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
    }

    public BlockPos getPos() {
        return pos;
    }
}
