package com.ynzz.lab.chapter04.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CallChain {
    private final List<String> steps = new ArrayList<String>();

    public void add(String step) {
        steps.add(step);
    }

    public List<String> getSteps() {
        return Collections.unmodifiableList(steps);
    }
}

