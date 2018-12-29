package com.mfcc.twopl.model;

/**
 * @author stefansebii@gmail.com
 */
public class ResourceIdentifier {
    // indicates that the lock extends to the whole table
    public static final long WHOLE_TABLE = -1;
    // indicates that the resource is not yet stored
    public static final long NONE = -2;

    private Class record;
    private long recordId;

    public ResourceIdentifier() {}

    public ResourceIdentifier(Class record, long recordId) {
        this.record = record;
        this.recordId = recordId;
    }

    public Class getRecord() {
        return record;
    }

    public void setRecord(Class record) {
        this.record = record;
    }

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    @Override
    public String toString() {
        String recordIdMsg = Long.toString(recordId);
        if (recordId == WHOLE_TABLE) {
            recordIdMsg = "Whole table";
        }
        if (recordId == NONE) {
            recordIdMsg = "None";
        }
        return "ResourceIdentifier{" +
                "record=" + record +
                ", recordId=" + recordIdMsg +
                '}';
    }
}
