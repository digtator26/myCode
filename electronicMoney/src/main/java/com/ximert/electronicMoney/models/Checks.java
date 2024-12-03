package com.ximert.electronicMoney.models;

import java.util.Map;

public class Checks {

    private Map<String, Long> checks;

    public Checks(Map<String, Long> checks) {
        this.checks = checks;
    }

    public Map<String, Long> getChecks() {
        return checks;
    }

}
