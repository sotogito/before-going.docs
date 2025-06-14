package com.und.server.card;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum CheckListStatus {

    ACTIVE(1),
    INACTIVE(0)
    ;

    private final int dbValue;

    public static boolean isActive(int value) {
        return Arrays.stream(CheckListStatus.values())
                .anyMatch(checkListStatus -> checkListStatus.dbValue== value);
    }

}
