package org.simon.stuff.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.simon.stuff.Stuff;

public class JointEffectPacket {
    public static final Identifier ID = new Identifier(Stuff.MOD_ID, "joint_effect");

    public static void send(ServerPlayerEntity player, int duration) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(duration);
        ServerPlayNetworking.send(player, ID, buf);
    }
}