package com.ynzz.lab.chapter02.agent;

import com.ynzz.lab.chapter02.common.ToolAskRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpToolIntentClient implements ToolIntentClient {
    private final String baseUrl;
    private final ToolIntentClient fallback;

    public HttpToolIntentClient(String baseUrl, ToolIntentClient fallback) {
        this.baseUrl = trimRightSlash(baseUrl);
        this.fallback = fallback;
    }

    @Override
    public ToolIntent inspect(ToolAskRequest request) {
        String body = "{"
                + "\"tenantId\":\"" + JsonSupport.escape(request.getTenantId()) + "\","
                + "\"operatorId\":\"" + JsonSupport.escape(request.getOperatorId()) + "\","
                + "\"question\":\"" + JsonSupport.escape(request.getQuestion()) + "\""
                + "}";

        try {
            String response = post("/api/mvp/tool-intent", body);
            return new ToolIntent(
                    JsonSupport.stringValue(response, "intentType", "READ"),
                    JsonSupport.stringValue(response, "orderId", "UNKNOWN"),
                    JsonSupport.stringValue(response, "reason", "AI Center intent"));
        } catch (RuntimeException ex) {
            return fallback.inspect(request);
        }
    }

    private String post(String path, String body) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(baseUrl + path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(1500);
            connection.setReadTimeout(5000);
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
                throw new IllegalStateException("AI Center returned HTTP " + status);
            }
            return builder.toString();
        } catch (Exception ex) {
            throw new IllegalStateException("AI Center tool-intent call failed: " + ex.getMessage(), ex);
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
    }
}
