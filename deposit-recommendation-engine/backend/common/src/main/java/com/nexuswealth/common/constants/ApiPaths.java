package com.nexuswealth.common.constants;

public class ApiPaths {

    private ApiPaths() {

    }

    public static final String API = "/api";

    public static final String VERSION = "/v1";

    public static final String BASE = API + VERSION;

    public static final String CUSTOMERS = BASE + "/customers";

    public static final String TRANSACTIONS = BASE + "/transactions";

    public static final String RECOMMENDATIONS = BASE + "/recommendations";

    public static final String DEPOSITS = BASE + "/deposits";
}
