package com.nexuswealth.common.util;

import java.util.UUID;

public final class UuidUtils {

    private UuidUtils() {
    }

    public static UUID generate() {
        return UUID.randomUUID();
    }

}