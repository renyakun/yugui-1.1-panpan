package src.yugui.common;

import java.util.HashMap;
import java.util.Map;

public class YgConstants {

    public static final int AGENCY_FACTORY_TYPE = 1;
    public static final int AGENCY_WAREHOUSE_TYPE = 2;
    public static final int AGENCY_PHARMACY_TYPE = 3;

    public static final String LOGIN_USER = "loginUser";

    public static final Map<String, Integer> REQUTEST_METHOD_MAP;

    static {
        //pharm code
        REQUTEST_METHOD_MAP = new HashMap<>();

        //request method map
        REQUTEST_METHOD_MAP.put("POST", 1);
        REQUTEST_METHOD_MAP.put("DELETE", 2);
        REQUTEST_METHOD_MAP.put("PUT", 3);
        REQUTEST_METHOD_MAP.put("GET", 4);
    }

    private YgConstants() {
    }
}
