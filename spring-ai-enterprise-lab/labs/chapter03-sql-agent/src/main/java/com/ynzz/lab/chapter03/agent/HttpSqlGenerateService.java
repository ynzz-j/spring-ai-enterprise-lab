package com.ynzz.lab.chapter03.agent;

import com.ynzz.lab.chapter03.common.CandidateSql;
import com.ynzz.lab.chapter03.common.SqlQueryRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpSqlGenerateService implements SqlGenerateService {
    private final String baseUrl;
    private final SqlGenerateService fallback;

    public HttpSqlGenerateService(String baseUrl, SqlGenerateService fallback) {
        this.baseUrl = trimRightSlash(baseUrl);
        this.fallback = fallback;
    }

    @Override
    public CandidateSql generate(SqlQueryRequest request) {
        String body = "{"
                + "\"tenantId\":\"" + JsonSupport.escape(request.getTenantId()) + "\","
                + "\"operatorId\":\"" + JsonSupport.escape(request.getOperatorId()) + "\","
                + "\"question\":\"" + JsonSupport.escape(request.getQuestion()) + "\""
                + "}";

        try {
            String response = post("/api/mvp/sql-candidate", body);
            return new CandidateSql(JsonSupport.stringValue(
                    response,
                    "candidateSql",
                    fallback.generate(request).getValue()));
        } catch (RuntimeException ex) {
            return fallback.generate(request);
        }
    }

    private String post(String path, String body) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(baseUrl + path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(1500);
            connection.setReadTimeout(8000);
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
            throw new IllegalStateException("AI Center sql-candidate call failed: " + ex.getMessage(), ex);
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
