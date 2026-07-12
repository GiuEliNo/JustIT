package it.dosti.justit.events.publisher.observers;


import it.dosti.justit.events.publisher.subjects.Subject;

public interface Observer {
    void update(Subject subject);
}
