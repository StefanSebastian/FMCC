package com.mfcc.twopl.model;

/**
 * @author stefansebii@gmail.com
 */
public class Operation {

    private Type type;
    private ResourceIdentifier resourceIdentifier;
    private TransactionAction action;

    private boolean executed = false; //default not executed
    private Runnable rollback;

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

    public TransactionAction getAction() {
        return action;
    }

    public void setAction(TransactionAction action) {
        this.action = action;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public Runnable getRollback() {
        return rollback;
    }

    public void setRollback(Runnable rollback) {
        this.rollback = rollback;
    }
}
