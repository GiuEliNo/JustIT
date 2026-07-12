package it.dosti.justit.events.publisher.subjects;

import it.dosti.justit.events.publisher.observers.Observer;

public interface Subject {

    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers();
}
