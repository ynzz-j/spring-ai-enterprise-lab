package com.ynzz.lab.chapter02.mcp;

public class ToolPermissionPolicy {
    public boolean isWriteIntent(String question) {
        return question.contains("改成")
                || question.contains("修改")
                || question.contains("删除")
                || question.contains("更新")
                || question.contains("发货");
    }
}

