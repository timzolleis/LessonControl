package de.waldorfaugsburg.lessoncontrol.client.service.voicemeeter;

import de.waldorfaugsburg.lessoncontrol.client.service.AbstractService;
import de.waldorfaugsburg.lessoncontrol.common.service.VoicemeeterServiceConfiguration;
import de.waldorfaugsburg.lessoncontrol.common.util.Scheduler;

import java.io.File;
import java.util.concurrent.ScheduledFuture;

public final class VoicemeeterService extends AbstractService<VoicemeeterServiceConfiguration> {

    private ScheduledFuture<?> task;
    private boolean antiHowlEnabled;
    private boolean antiHowlMute;

    public VoicemeeterService(final VoicemeeterServiceConfiguration configuration) {
        super(configuration);
    }

    @Override
    public void enable() {
        Voicemeeter.init();
        Voicemeeter.runVoicemeeter();

        loadConfig();
        setAntiHowlEnabled(getConfiguration().getAntiHowl().isEnabled());

        task = Scheduler.schedule(() -> {
            if (antiHowlEnabled) {
                final float volume = Voicemeeter.getLevel(0, getConfiguration().getAntiHowl().getMonitoredChannel());
                if (volume >= 5 && !antiHowlMute) {
                    muteStrips(true);
                    antiHowlMute = true;
                } else if (antiHowlMute && volume < 4) {
                    muteStrips(false);
                    antiHowlMute = false;
                }
            }
        }, 100);
    }

    @Override
    public void disable() {
        setAntiHowlEnabled(false);
        task.cancel(true);
        Voicemeeter.setParameterFloat("Command.Shutdown", 1);
    }

    public boolean isAntiHowlEnabled() {
        return antiHowlEnabled;
    }

    public void setAntiHowlEnabled(final boolean antiHowlEnabled) {
        this.antiHowlEnabled = antiHowlEnabled;

        if (!antiHowlEnabled) {
            muteStrips(false);
        }
    }

    private void loadConfig() {
        final File file = new File(getConfiguration().getConfigPath());
        if (!file.exists()) throw new IllegalStateException("Config is missing");

        Voicemeeter.setParameterString("Command.Load", file.getAbsolutePath());
    }

    private void muteStrips(final boolean muted) {
        for (final int strip : getConfiguration().getAntiHowl().getMuteStrips()) {
            Voicemeeter.setParameterFloat("Strip(" + strip + ").Mute", muted ? 1 : 0);
        }
    }
}
