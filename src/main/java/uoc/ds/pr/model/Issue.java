package uoc.ds.pr.model;

import java.time.LocalDateTime;

public class Issue {

    private final String id;
    private Component component;
    private String description;
    private LocalDateTime dateTime;
    private boolean resolved;
    private Worker worker;

    public Issue(String id, Component component, String description, LocalDateTime dateTime) {
        this.id = id;
        this.component = component;
        this.description = description;
        this.dateTime = dateTime;
        this.resolved = false;
        this.worker = null;
    }

    public String getId() {
        return id;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}