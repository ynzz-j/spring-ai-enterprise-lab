package com.ynzz.lab.chapter07.agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicketMaskingService {
    private List<String> lastMaskedFields = new ArrayList<String>();

    public String mask(String content) {
        lastMaskedFields = new ArrayList<String>();
        String masked = content;
        if (masked.matches(".*1[3-9][0-9]{9}.*")) {
            masked = masked.replaceAll("1[3-9][0-9]{9}", "1**********");
            lastMaskedFields.add("mobile");
        }
        return masked;
    }

    public List<String> getLastMaskedFields() {
        return Collections.unmodifiableList(lastMaskedFields);
    }
}

