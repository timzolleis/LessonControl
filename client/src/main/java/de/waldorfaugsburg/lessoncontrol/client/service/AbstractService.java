package de.waldorfaugsburg.lessoncontrol.client.service;

import de.waldorfaugsburg.lessoncontrol.common.service.AbstractServiceConfiguration;

public abstract class AbstractService<C extends AbstractServiceConfiguration> {

    private final C configuration;

    protected AbstractService(final C configuration) {
        this.configuration = configuration;
    }

    public abstract void enable();

    public abstract void disable();

    public C getConfiguration() {
        return configuration;
    }
}
