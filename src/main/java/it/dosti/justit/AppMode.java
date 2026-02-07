package it.dosti.justit;

import it.dosti.justit.exceptions.NavigationException;

public interface AppMode {
    void start(String[] args) throws NavigationException;
}
