package com.nexuswealth.common.constants;

public final class RegexConstants {

    private RegexConstants() {
    }

    public static final String EMAIL =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public static final String MOBILE =
            "^[6-9]\\d{9}$";

    public static final String PAN =
            "^[A-Z]{5}[0-9]{4}[A-Z]{1}$";

}