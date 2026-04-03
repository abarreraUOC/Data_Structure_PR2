package uoc.ds.pr.pr2;

import edu.uoc.ds.traversal.Iterator;
import uoc.ds.pr.exceptions.ComponentAlreadyInstalledException;
import uoc.ds.pr.exceptions.ComponentNotFoundException;
import uoc.ds.pr.exceptions.IssueAlreadyAssignedException;
import uoc.ds.pr.exceptions.IssueAlreadyResolvedException;
import uoc.ds.pr.exceptions.IssueNotFoundException;
import uoc.ds.pr.exceptions.NoIssuesException;
import uoc.ds.pr.exceptions.NoSystemsException;
import uoc.ds.pr.exceptions.NoWorkerException;
import uoc.ds.pr.exceptions.SystemHasNoComponentsException;
import uoc.ds.pr.exceptions.WorkerNotFoundException;
import uoc.ds.pr.model.Component;
import uoc.ds.pr.model.Issue;
import uoc.ds.pr.model.System;
import uoc.ds.pr.model.Worker;

import java.time.LocalDateTime;

public class SystemIssuesPR2Impl implements SystemIssues, SystemIssuesHelper {

    private final Worker[] workers;
    private int numWorkers;

    private final System[] systems;
    private int numSystems;

    private final Component[] components;
    private int numComponents;

    private Issue[] issues;
    private int numIssues;

    public SystemIssuesPR2Impl() {
        this.workers = new Worker[MAX_WORKERS];
        this.numWorkers = 0;

        this.systems = new System[MAX_SYSTEMS];
        this.numSystems = 0;

        this.components = new Component[MAX_COMPONENTS];
        this.numComponents = 0;

        this.issues = new Issue[16];
        this.numIssues = 0;
    }

    @Override
    public void addWorker(String workerId, String name, String address) {
        int pos = findWorker(workerId);
        if (pos != -1) {
            workers[pos].setName(name);
            workers[pos].setAddress(address);
            return;
        }

        workers[numWorkers++] = new Worker(workerId, name, address);
    }

    @Override
    public void addSystem(String systemId, String description, String location) {
        int pos = findSystem(systemId);
        if (pos != -1) {
            systems[pos].setDescription(description);
            systems[pos].setLocation(location);
            return;
        }

        systems[numSystems++] = new System(systemId, description, location);
    }

    @Override
    public void addComponent(String componentId, String trademark, String model, String serial) {
        int pos = findComponent(componentId);
        if (pos != -1) {
            components[pos].setTrademark(trademark);
            components[pos].setModel(model);
            components[pos].setSerial(serial);
            return;
        }

        components[numComponents++] = new Component(componentId, trademark, model, serial);
    }

    @Override
    public void installComponentToSystem(String componentId, String systemId) throws ComponentAlreadyInstalledException {
        Component component = getComponent(componentId);
        System system = getSystem(systemId);

        if (component == null || system == null) {
            throw new ComponentAlreadyInstalledException();
        }

        if (component.getSystem() != null || system.hasComponent(componentId)) {
            throw new ComponentAlreadyInstalledException();
        }

        system.addComponent(component);
        component.setSystem(system);
    }

    @Override
    public Issue createIssue(String issueId, String componentId, String description, LocalDateTime dateTime)
            throws ComponentNotFoundException {

        Component component = getComponent(componentId);
        if (component == null) {
            throw new ComponentNotFoundException();
        }

        int pos = findIssue(issueId);
        if (pos != -1) {
            return issues[pos];
        }

        ensureIssuesCapacity();

        Issue issue = new Issue(issueId, component, description, dateTime);
        issues[numIssues++] = issue;
        component.addIssue(issue);

        return issue;
    }

    @Override
    public void assignIssue(String issueId, String workerId)
            throws IssueNotFoundException, WorkerNotFoundException,
            IssueAlreadyAssignedException, IssueAlreadyResolvedException {

        Issue issue = getIssue(issueId);
        if (issue == null) {
            throw new IssueNotFoundException();
        }

        Worker worker = getWorker(workerId);
        if (worker == null) {
            throw new WorkerNotFoundException();
        }

        if (issue.isResolved()) {
            throw new IssueAlreadyResolvedException();
        }

        if (issue.getWorker() != null) {
            throw new IssueAlreadyAssignedException();
        }

        worker.assignIssue(issue);
        issue.setWorker(worker);
    }

    @Override
    public Issue solveIssue(String workerId) throws WorkerNotFoundException, NoIssuesException {
        Worker worker = getWorker(workerId);
        if (worker == null) {
            throw new WorkerNotFoundException();
        }

        if (!worker.hasAssignedIssues()) {
            throw new NoIssuesException();
        }

        Issue issue = worker.solveLastAssignedIssue();
        issue.setResolved(true);
        worker.addDoneIssue(issue);

        return issue;
    }

    @Override
    public Iterator<System> getSystems() throws NoSystemsException {
        if (numSystems == 0) {
            throw new NoSystemsException();
        }

        return new ArrayIterator<>(systems, numSystems);
    }

    @Override
    public Iterator<Component> getComponentsBySystem(String systemId) throws SystemHasNoComponentsException {
        System system = getSystem(systemId);

        if (system == null || system.numComponents() == 0) {
            throw new SystemHasNoComponentsException();
        }

        return new ArrayIterator<>(system.getComponents(), system.numComponents());
    }

    @Override
    public Iterator<Issue> getDoneIssuesByWorker(String workerId) throws NoIssuesException {
        Worker worker = getWorker(workerId);

        if (worker == null || worker.numDoneIssues() == 0) {
            throw new NoIssuesException();
        }

        return new ArrayIterator<>(worker.getDoneIssues(), worker.numDoneIssues());
    }

    @Override
    public Worker getTopWorker() throws NoWorkerException {
        if (numWorkers == 0) {
            throw new NoWorkerException();
        }

        Worker top = workers[0];
        for (int i = 1; i < numWorkers; i++) {
            if (workers[i].numDoneIssues() > top.numDoneIssues()) {
                top = workers[i];
            }
        }

        return top;
    }

    @Override
    public System getSystemWithMostComponents() throws NoSystemsException {
        if (numSystems == 0) {
            throw new NoSystemsException();
        }

        System best = systems[0];
        for (int i = 1; i < numSystems; i++) {
            if (systems[i].numComponents() > best.numComponents()) {
                best = systems[i];
            }
        }

        return best;
    }

    @Override
    public SystemIssuesHelper getSystemIssuesHelper() {
        return this;
    }

    @Override
    public Worker getWorker(String id) {
        int pos = findWorker(id);
        return (pos == -1 ? null : workers[pos]);
    }

    @Override
    public int numWorkers() {
        return numWorkers;
    }

    @Override
    public System getSystem(String id) {
        int pos = findSystem(id);
        return (pos == -1 ? null : systems[pos]);
    }

    @Override
    public int numSystems() {
        return numSystems;
    }

    @Override
    public Component getComponent(String id) {
        int pos = findComponent(id);
        return (pos == -1 ? null : components[pos]);
    }

    @Override
    public int numComponents() {
        return numComponents;
    }

    @Override
    public int numComponentsBySystem(String systemId) {
        System system = getSystem(systemId);
        return (system == null ? 0 : system.numComponents());
    }

    @Override
    public int numIssues() {
        return numIssues;
    }

    @Override
    public int numIssuesByComponent(String componentId) {
        Component component = getComponent(componentId);
        return (component == null ? 0 : component.numIssues());
    }

    @Override
    public int numIssuesByWorker(String workerId) {
        Worker worker = getWorker(workerId);
        return (worker == null ? 0 : worker.numAssignedIssues());
    }

    private Issue getIssue(String id) {
        int pos = findIssue(id);
        return (pos == -1 ? null : issues[pos]);
    }

    private int findWorker(String id) {
        for (int i = 0; i < numWorkers; i++) {
            if (workers[i].getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    private int findSystem(String id) {
        for (int i = 0; i < numSystems; i++) {
            if (systems[i].getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    private int findComponent(String id) {
        for (int i = 0; i < numComponents; i++) {
            if (components[i].getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    private int findIssue(String id) {
        for (int i = 0; i < numIssues; i++) {
            if (issues[i].getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    private void ensureIssuesCapacity() {
        if (numIssues == issues.length) {
            Issue[] newArray = new Issue[issues.length * 2];
            for (int i = 0; i < issues.length; i++) {
                newArray[i] = issues[i];
            }
            issues = newArray;
        }
    }

    private static class ArrayIterator<E> implements Iterator<E> {
        private final E[] data;
        private final int size;
        private int index;

        private ArrayIterator(E[] data, int size) {
            this.data = data;
            this.size = size;
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public E next() {
            return data[index++];
        }
    }
}