package de.waldorfaugsburg.lessoncontrol.common.network.client;

import de.waldorfaugsburg.lessoncontrol.common.network.Packet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public final class ClientPerformancePacket extends Packet {
    private double cpuUsage;
    private long usedMemory;
}