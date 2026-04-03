package uoc.ds.pr.model;

public class System {

    private final String id;
    private String description;
    private String location;

    private Component[] components;
    private int numComponents;

    public System(String id, String description, String location) {
        this.id = id;
        this.description = description;
        this.location = location;
        this.components = new Component[4];
        this.numComponents = 0;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void addComponent(Component component) {
        ensureComponentsCapacity();
        components[numComponents++] = component;
    }

    public int numComponents() {
        return numComponents;
    }

    public Component getComponent(int index) {
        return components[index];
    }

    public Component[] getComponents() {
        return components;
    }

    public boolean hasComponent(String componentId) {
        for (int i = 0; i < numComponents; i++) {
            if (components[i].getId().equals(componentId)) {
                return true;
            }
        }
        return false;
    }

    private void ensureComponentsCapacity() {
        if (numComponents == components.length) {
            Component[] newArray = new Component[components.length * 2];
            for (int i = 0; i < components.length; i++) {
                newArray[i] = components[i];
            }
            components = newArray;
        }
    }
}