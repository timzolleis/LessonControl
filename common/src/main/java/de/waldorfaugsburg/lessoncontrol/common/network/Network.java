package de.waldorfaugsburg.lessoncontrol.common.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.minlog.Log;
import de.waldorfaugsburg.lessoncontrol.common.network.client.RegisterPacket;
import de.waldorfaugsburg.lessoncontrol.common.network.client.SystemResourcesPacket;
import de.waldorfaugsburg.lessoncontrol.common.network.server.AcceptPacket;
import de.waldorfaugsburg.lessoncontrol.common.network.server.DenyPacket;
import de.waldorfaugsburg.lessoncontrol.common.network.server.TransferFileChunkPacket;
import de.waldorfaugsburg.lessoncontrol.common.network.server.TransferProfilePacket;
import de.waldorfaugsburg.lessoncontrol.common.service.AbstractServiceConfiguration;
import de.waldorfaugsburg.lessoncontrol.common.service.ButtonServiceConfiguration;
import de.waldorfaugsburg.lessoncontrol.common.service.GeneralServiceConfiguration;
import de.waldorfaugsburg.lessoncontrol.common.service.VoicemeeterServiceConfiguration;

import java.util.LinkedHashSet;
import java.util.Set;

public final class Network {

    public static final int PROTOCOL_VERSION = 1;
    public static final int FILE_CHUNK_SIZE = 500;

    static {
        Log.set(Log.LEVEL_ERROR);
    }

    private Network() {
    }

    public static void registerPacketClasses(final EndPoint endPoint) {
        final Kryo kryo = endPoint.getKryo();

        // General
        kryo.register(byte[].class);
        kryo.register(int[].class);
        kryo.register(Set.class);
        kryo.register(LinkedHashSet.class);

        kryo.register(AbstractServiceConfiguration.class);
        kryo.register(GeneralServiceConfiguration.class);
        kryo.register(VoicemeeterServiceConfiguration.class);
        kryo.register(VoicemeeterServiceConfiguration.AntiHowl.class);
        kryo.register(ButtonServiceConfiguration.class);
        kryo.register(ButtonServiceConfiguration.Button.class);

        // Bound to client
        kryo.register(RegisterPacket.class);
        kryo.register(SystemResourcesPacket.class);

        // Bound to server
        kryo.register(AcceptPacket.class);
        kryo.register(DenyPacket.class);
        kryo.register(DenyPacket.Reason.class);
        kryo.register(TransferProfilePacket.class);
        kryo.register(TransferFileChunkPacket.class);
    }
}
