package com.ynzz.lab.chapter08.agent;

public class ScreenshotRecorder {
    public String record(String planId, String target) {
        return "artifacts/screenshots/" + planId + "-" + target + ".png";
    }
}

