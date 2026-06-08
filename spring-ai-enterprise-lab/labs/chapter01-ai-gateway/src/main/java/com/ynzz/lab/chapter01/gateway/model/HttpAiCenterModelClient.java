package com.ynzz.lab.chapter01.gateway.model;

import com.ynzz.lab.chapter01.common.OrderSummaryResult;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class HttpAiCenterModelClient implements ModelClient {
    private final String baseUrl;
    private final int connectTimeoutMs;
    private final int readTimeoutMs;

    public HttpAiCenterModelClient(String baseUrl) {
        this(baseUrl, 3000, 10000);
    }

    public HttpAiCenterModelClient(String baseUrl, int connectTimeoutMs, int readTimeoutMs) {
        this.baseUrl = trimRightSlash(baseUrl);
        this.connectTimeoutMs = connectTimeoutMs;
        this.readTimeoutMs = readTimeoutMs;
    }

    @Override
    public OrderSummaryResult summarize(String orderId, String maskedText) {
        String body = "{"
                + "\"tenantId\":\"demo\","
                + "\"operatorId\":\"u1001\","
                + "\"orderId\":\"" + JsonSupport.escape(orderId) + "\","
                + "\"orderText\":\"" + JsonSupport.escape(maskedText) + "\""
                + "}";

        String response = post("/api/mvp/order-summary", body);
        return new OrderSummaryResult(
                JsonSupport.stringValue(response, "orderId", orderId),
                JsonSupport.stringValue(response, "summary", "AI Center 未返回摘要。"),
                JsonSupport.stringValue(response, "riskLevel", "UNKNOWN"),
                JsonSupport.stringArray(response, "suggestedActions",
                        Arrays.asList("联系 AI 平台管理员")),
                JsonSupport.booleanValue(response, "fallback", false),
                Arrays.asList());
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    private String post(String path, String body) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(baseUrl + path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(connectTimeoutMs);
            connection.setReadTimeout(readTimeoutMs);
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);

            byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
            connection.setFixedLengthStreamingMode(bytes.length);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(bytes);
            outputStream.close();

            int status = connection.getResponseCode();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    status >= 200 && status < 300
                            ? connection.getInputStream()
                            : connection.getErrorStream(),
                    StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();

            if (status < 200 || status >= 300) {
                throw new IllegalStateException("AI Center returned HTTP " + status + ": " + builder);
            }
            return builder.toString();
        } catch (Exception ex) {
            throw new IllegalStateException("AI Center order-summary call failed: " + ex.getMessage(), ex);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private String trimRightSlash(String value) {
        if (value == null) {
            return "";
        }
        while (value.endsWith("/")) {
            value = value.substring(0, value.length() - 1);
        }
        return value;
    }

    private static class JsonSupport {
        private static String escape(String value) {
            if (value == null) {
                return "";
            }
            return value.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\r", "\\r")
                    .replace("\n", "\\n");
        }

        private static String stringValue(String json, String field, String defaultValue) {
            String marker = "\"" + field + "\":";
            int start = json.indexOf(marker);
            if (start < 0) {
                return defaultValue;
            }
            start = json.indexOf('"', start + marker.length());
            if (start < 0) {
                return defaultValue;
            }
            int end = start + 1;
            boolean escaping = false;
            while (end < json.length()) {
                char ch = json.charAt(end);
                if (ch == '"' && !escaping) {
                    return json.substring(start + 1, end)
                            .replace("\\n", "\n")
                            .replace("\\r", "\r")
                            .replace("\\\"", "\"")
                            .replace("\\\\", "\\");
                }
                escaping = ch == '\\' && !escaping;
                if (ch != '\\') {
                    escaping = false;
                }
                end++;
            }
            return defaultValue;
        }

        private static boolean booleanValue(String json, String field, boolean defaultValue) {
            String marker = "\"" + field + "\":";
            int start = json.indexOf(marker);
            if (start < 0) {
                return defaultValue;
            }
            String rest = json.substring(start + marker.length()).trim();
            if (rest.startsWith("true")) {
                return true;
            }
            if (rest.startsWith("false")) {
                return false;
            }
            return defaultValue;
        }

        private static List<String> stringArray(String json, String field, List<String> defaultValue) {
            String marker = "\"" + field + "\":";
            int start = json.indexOf(marker);
            if (start < 0) {
                return defaultValue;
            }
            int left = json.indexOf('[', start + marker.length());
            int right = json.indexOf(']', left + 1);
            if (left < 0 || right < 0) {
                return defaultValue;
            }
            String value = json.substring(left + 1, right).trim();
            if (value.length() == 0) {
                return Arrays.asList();
            }
            String[] parts = value.split(",");
            java.util.ArrayList<String> result = new java.util.ArrayList<String>();
            for (int i = 0; i < parts.length; i++) {
                String item = parts[i].trim();
                if (item.startsWith("\"") && item.endsWith("\"")) {
                    item = item.substring(1, item.length() - 1);
                }
                result.add(item.replace("\\\"", "\"").replace("\\\\", "\\"));
            }
            return result;
        }
    }
}
