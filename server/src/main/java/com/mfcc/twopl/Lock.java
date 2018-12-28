package com.mfcc.twopl;

/**
 * @author stefansebii@gmail.com
 */
public class Lock {

    private long id;
    private Type type;
    private long recordId;
    private String table;
    private Transaction transaction;

    public enum Type { READ, WRITE }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lock lock = (Lock) o;

        if (id != lock.id) return false;
        if (recordId != lock.recordId) return false;
        if (type != lock.type) return false;
        return table.equals(lock.table);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + type.hashCode();
        result = 31 * result + (int) (recordId ^ (recordId >>> 32));
        result = 31 * result + table.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Lock{" +
                "id=" + id +
                ", type=" + type +
                ", recordId=" + recordId +
                ", table='" + table + '\'' +
                ", transaction=" + transaction +
                '}';
    }
}
