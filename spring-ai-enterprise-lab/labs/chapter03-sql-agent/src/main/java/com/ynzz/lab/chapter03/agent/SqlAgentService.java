package com.ynzz.lab.chapter03.agent;

import java.util.ArrayList;

import com.ynzz.lab.chapter03.common.CandidateSql;
import com.ynzz.lab.chapter03.common.SqlQueryRequest;
import com.ynzz.lab.chapter03.common.SqlQueryResult;
import com.ynzz.lab.chapter03.safety.SchemaSnapshot;
import com.ynzz.lab.chapter03.safety.SqlSafetyDecision;
import com.ynzz.lab.chapter03.safety.SqlSafetyEngine;

public class SqlAgentService {
    private final SqlGenerateService sqlGenerateService;
    private final SqlSafetyEngine sqlSafetyEngine;
    private final ReadOnlySqlExecutor readOnlySqlExecutor;
    private final SqlResultSummarizer summarizer;

    public SqlAgentService(SqlGenerateService sqlGenerateService,
                           SqlSafetyEngine sqlSafetyEngine,
                           ReadOnlySqlExecutor readOnlySqlExecutor,
                           SqlResultSummarizer summarizer) {
        this.sqlGenerateService = sqlGenerateService;
        this.sqlSafetyEngine = sqlSafetyEngine;
        this.readOnlySqlExecutor = readOnlySqlExecutor;
        this.summarizer = summarizer;
    }

    public SqlQueryResult query(SqlQueryRequest request) {
        CandidateSql candidateSql = sqlGenerateService.generate(request);
        SqlSafetyDecision decision = sqlSafetyEngine.inspect(candidateSql, SchemaSnapshot.orderReport());

        if (!decision.isAllowed()) {
            return new SqlQueryResult(
                    request.getQuestion(),
                    decision.getSafeSql(),
                    true,
                    decision.getReason(),
                    "SQL 已被安全引擎拦截，未执行。",
                    new ArrayList<String>());
        }

        java.util.List<String> rows = readOnlySqlExecutor.execute(decision.getSafeSql());
        return new SqlQueryResult(
                request.getQuestion(),
                decision.getSafeSql(),
                false,
                decision.getReason(),
                summarizer.summarize(request.getQuestion(), rows),
                rows);
    }
}
