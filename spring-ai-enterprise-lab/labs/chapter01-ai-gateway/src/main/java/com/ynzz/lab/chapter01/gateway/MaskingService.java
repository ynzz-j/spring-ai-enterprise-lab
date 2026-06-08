package com.ynzz.lab.chapter01.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 第 1 讲最小脱敏服务。
 */
public class MaskingService {
    private static final Pattern ID_CARD_PATTERN = Pattern.compile("(?<!\\d)(\\d{17}[\\dXx]|\\d{15})(?!\\d)");
    private static final Pattern PHONE_PATTERN = Pattern.compile("(?<!\\d)(1\\d{2})\\d{4}(\\d{4})(?!\\d)");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");

    public MaskingResult mask(String text) {
        if (text == null || text.isEmpty()) {
            return new MaskingResult(text, new ArrayList<String>());
        }

        List<String> maskedFields = new ArrayList<String>();
        String result = text;

        MaskStep idCardStep = replaceAll(result, ID_CARD_PATTERN, "******************", "idCard");
        result = idCardStep.text;
        maskedFields.addAll(idCardStep.maskedFields);

        MaskStep phoneStep = replaceAll(result, PHONE_PATTERN, "$1****$2", "mobile");
        result = phoneStep.text;
        maskedFields.addAll(phoneStep.maskedFields);

        MaskStep emailStep = replaceAll(result, EMAIL_PATTERN, "***@***", "email");
        result = emailStep.text;
        maskedFields.addAll(emailStep.maskedFields);

        return new MaskingResult(result, maskedFields);
    }

    private MaskStep replaceAll(String text, Pattern pattern, String replacement, String fieldName) {
        Matcher matcher = pattern.matcher(text);
        StringBuffer buffer = new StringBuffer();
        List<String> fields = new ArrayList<String>();
        while (matcher.find()) {
            fields.add(fieldName);
            matcher.appendReplacement(buffer, replacement);
        }
        matcher.appendTail(buffer);
        return new MaskStep(buffer.toString(), fields);
    }

    public static class MaskingResult {
        private final String text;
        private final List<String> maskedFields;

        public MaskingResult(String text, List<String> maskedFields) {
            this.text = text;
            this.maskedFields = maskedFields;
        }

        public String getText() {
            return text;
        }

        public List<String> getMaskedFields() {
            return maskedFields;
        }
    }

    private static class MaskStep {
        private final String text;
        private final List<String> maskedFields;

        private MaskStep(String text, List<String> maskedFields) {
            this.text = text;
            this.maskedFields = maskedFields;
        }
    }
}
