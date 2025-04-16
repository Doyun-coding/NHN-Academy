package nhn.academy.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum ClassType {
    A, B, C;

    @JsonCreator
    public static ClassType fromString(String str){
        for (ClassType value : ClassType.values()) {
            if (value.name().equalsIgnoreCase(str)) {
                return value;
            }
        }
        //default
        return A;
    }

    @JsonValue
    public String toJson(){
        return this.name().toLowerCase();
    }
}
