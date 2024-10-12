package org.simon.stuff.entity.drug.sound;

import org.simon.stuff.StuffSounds;
import org.simon.stuff.entity.drug.*;
import org.simon.stuff.util.MathUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.MathHelper;


public class DrugMusicManager {
    public static final float PLAY_THRESHOLD = 0.01F;

    final DrugProperties properties;

    private int delayUntilHeartbeat;
    private int delayUntilBreath;
    private boolean lastBreathWasIn;

    private float prevHeartbeatPulseStrength;
    private float heartbeatPulseStrength;
    private float targetHeartbeatPulseStrength;

    public DrugMusicManager(DrugProperties properties) {
        this.properties = properties;
    }

    public void update() {
        PlayerEntity entity = properties.asEntity();

        if (delayUntilHeartbeat > 0) {
            delayUntilHeartbeat--;
        }

        if (delayUntilBreath > 0) {
            delayUntilBreath--;
        }

        prevHeartbeatPulseStrength = heartbeatPulseStrength;
        heartbeatPulseStrength = MathUtils.approach(heartbeatPulseStrength, targetHeartbeatPulseStrength, 0.2F);
        if (targetHeartbeatPulseStrength > 0) {
            targetHeartbeatPulseStrength -= 0.02F;
        }

        if (delayUntilHeartbeat == 0) {
            float heartbeatVolume = properties.getModifier(Drug.HEART_BEAT_VOLUME);
            if (heartbeatVolume > 0) {
                float speed = properties.getModifier(Drug.HEART_BEAT_SPEED);

                delayUntilHeartbeat = speed <= 1 ? 1 : MathHelper.floor(35F / (speed - 1F));
                targetHeartbeatPulseStrength = 1;
                entity.getWorld().playSound(entity.getX(), entity.getY(), entity.getZ(),
                        StuffSounds.ENTITY_PLAYER_HEARTBEAT,
                        SoundCategory.AMBIENT, heartbeatVolume, speed, false);
            }
        }

        if (delayUntilBreath == 0) {
            lastBreathWasIn = !lastBreathWasIn;

            float breathVolume = properties.getModifier(Drug.BREATH_VOLUME);
            if (breathVolume > 0) {
                float speed = properties.getModifier(Drug.BREATH_SPEED);
                delayUntilBreath = MathHelper.floor(30F / speed);
                entity.getWorld().playSoundFromEntity(entity, entity, StuffSounds.ENTITY_PLAYER_BREATH, SoundCategory.PLAYERS,
                        breathVolume,
                        speed * 0.05F + 0.9F + (lastBreathWasIn ? 0.15F : 0)
                );
            }
        }
    }

    public float getHeartbeatPulseStrength(float delta) {
        return MathHelper.lerp(delta, prevHeartbeatPulseStrength, heartbeatPulseStrength);
    }
}
