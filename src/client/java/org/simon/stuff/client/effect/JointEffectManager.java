package org.simon.stuff.client.effect;

public class JointEffectManager {
    private static final int FADE_DURATION = 100; // 5 seconds at 20 ticks per second
    private static int remainingDuration = 0;
    private static int totalDuration = 0;
    private static boolean isFadingOut = false;
    private static float currentIntensity = 0.0f;

    public static void startEffect(int duration) {
        if (remainingDuration > 0) {
            // If effect is already active, add duration and maintain current intensity
            remainingDuration += duration;
            totalDuration += duration;
            if (isFadingOut) {
                isFadingOut = false;
                currentIntensity = getEffectIntensity();
            }
        } else {
            // If no effect is active, start a new one
            remainingDuration = duration + FADE_DURATION;
            totalDuration = remainingDuration;
            currentIntensity = 0.0f;
            isFadingOut = false;
        }
    }

    public static void tick() {
        if (remainingDuration > 0) {
            remainingDuration--;
            if (remainingDuration == FADE_DURATION && !isFadingOut) {
                isFadingOut = true;
                currentIntensity = 1.0f;
            }
        }
    }

    public static boolean isEffectActive() {
        return remainingDuration > 0;
    }

    public static float getEffectIntensity() {
        if (remainingDuration <= 0) return 0.0f;

        if (isFadingOut) {
            return (float) remainingDuration / FADE_DURATION;
        } else {
            float progress = 1.0f - (float)(remainingDuration - FADE_DURATION) / (totalDuration - FADE_DURATION);
            return Math.max(currentIntensity, Math.min(1.0f, progress * totalDuration / FADE_DURATION));
        }
    }

    public static void clearEffect() {
        remainingDuration = FADE_DURATION;
        totalDuration = remainingDuration;
        isFadingOut = true;
        currentIntensity = 1.0f;
    }
}