package com.mfcc.twopl.model;

/**
 * @author stefansebii@gmail.com
 */
public class Operation {

    private Type type;
    private ResourceIdentifier resourceIdentifier;
    private Runnable action;

    public enum Type { INSERT, DELETE, UPDATE, SELECT }

    public Operation(){}

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public ResourceIdentifier getResourceIdentifier() {
        return resourceIdentifier;
    }

    public void setResourceIdentifier(ResourceIdentifier resourceIdentifier) {
        this.resourceIdentifier = resourceIdentifier;
    }

    public Runnable getAction() {
        return action;
    }

    public void setAction(Runnable action) {
        this.action = action;
    }

}
