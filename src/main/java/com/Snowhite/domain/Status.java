package com.Snowhite.domain;

import com.Snowhite.exception.NoDataFoundException;

public enum  Status {

    AVAILABLE(1),
    SOLD(2),
    DESTROYED(3);

    private int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

//    public static Status fromValue(int value) {
//        Status status = null;
//        if (value == 1) {
//            status = AVAILABLE;
//        } else if (value == 2) {
//            status = SOLD;
//        } else if (value == 3) {
//            status = DESTROYED;
//        }
//        throw new RuntimeException("Invalid status value = " + value);
//    }

    public static Status fromValue(String value) {
        Status status = null;
        if (value == "AVAILABLE") {
            status = AVAILABLE;
        } else if (value == "SOLD") {
            status = SOLD;
        } else if (value == "DESTROYED") {
            status = DESTROYED;
        }
        throw new RuntimeException("Invalid status value = " + value);
//        throw new NoDataFoundException();
    }

}
