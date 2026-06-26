package com.ynzz.lab.chapter04;

import com.ynzz.lab.chapter04.analysis.CallChainAnalyzer;
import com.ynzz.lab.chapter04.analysis.JavaProjectScanner;
import com.ynzz.lab.chapter04.analysis.LayerDetector;
import com.ynzz.lab.chapter04.analysis.ReadingPathGenerator;
import com.ynzz.lab.chapter04.common.CallChain;
import com.ynzz.lab.chapter04.common.JavaProject;
import com.ynzz.lab.chapter04.common.ReadingPathResult;

import java.io.File;
import java.util.List;

public class Chapter04Demo {
    public static void main(String[] args) {
        String projectName = "legacy-order";
        String entrypoint = "OrderController#createOrder";
        File projectPath = new File("legacy-codebase-sample");

        JavaProject project = new JavaProjectScanner().scan(projectName, projectPath);
        List<String> layers = new LayerDetector().detect(project);
        CallChain callChain = new CallChainAnalyzer().analyze(project, entrypoint);
        ReadingPathResult result = new ReadingPathGenerator().generate(projectName, entrypoint, layers, callChain);

        System.out.println("=== reading path ===");
        System.out.println(result.toJson());
    }
}
