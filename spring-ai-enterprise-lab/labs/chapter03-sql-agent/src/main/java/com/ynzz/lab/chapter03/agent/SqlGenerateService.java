package com.ynzz.lab.chapter03.agent;

import com.ynzz.lab.chapter03.common.CandidateSql;
import com.ynzz.lab.chapter03.common.SqlQueryRequest;

public interface SqlGenerateService {
    CandidateSql generate(SqlQueryRequest request);
}
