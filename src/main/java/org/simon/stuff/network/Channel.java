package org.simon.stuff.network;

import com.sollace.fabwork.api.packets.S2CPacketType;
import com.sollace.fabwork.api.packets.SimpleNetworking;

import org.simon.stuff.Stuff;

/**
 * @author Sollace
 * @since 1 Jan 2023
 */
public interface Channel {
    S2CPacketType<MsgDrugProperties> UPDATE_DRUG_PROPERTIES = SimpleNetworking.serverToClient(Stuff.id("update_drug_properties"), MsgDrugProperties::new);

    static void bootstrap() { }

}
