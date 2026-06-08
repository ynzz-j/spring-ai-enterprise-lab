package com.ynzz.lab.aicenter.api;

import com.ynzz.lab.aicenter.api.dto.OrderSummaryRequest;
import com.ynzz.lab.aicenter.api.dto.OrderSummaryResponse;
import com.ynzz.lab.aicenter.api.dto.SqlCandidateRequest;
import com.ynzz.lab.aicenter.api.dto.SqlCandidateResponse;
import com.ynzz.lab.aicenter.api.dto.ToolIntentRequest;
import com.ynzz.lab.aicenter.api.dto.ToolIntentResponse;
import com.ynzz.lab.aicenter.service.OrderSummaryMvpService;
import com.ynzz.lab.aicenter.service.SqlCandidateMvpService;
import com.ynzz.lab.aicenter.service.ToolIntentMvpService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mvp")
public class MvpController {
    private final OrderSummaryMvpService orderSummaryService;
    private final ToolIntentMvpService toolIntentService;
    private final SqlCandidateMvpService sqlCandidateService;

    public MvpController(OrderSummaryMvpService orderSummaryService,
                         ToolIntentMvpService toolIntentService,
                         SqlCandidateMvpService sqlCandidateService) {
        this.orderSummaryService = orderSummaryService;
        this.toolIntentService = toolIntentService;
        this.sqlCandidateService = sqlCandidateService;
    }

    @PostMapping("/order-summary")
    public OrderSummaryResponse orderSummary(@RequestBody OrderSummaryRequest request) {
        return orderSummaryService.summarize(request);
    }

    @PostMapping("/tool-intent")
    public ToolIntentResponse toolIntent(@RequestBody ToolIntentRequest request) {
        return toolIntentService.inspect(request);
    }

    @PostMapping("/sql-candidate")
    public SqlCandidateResponse sqlCandidate(@RequestBody SqlCandidateRequest request) {
        return sqlCandidateService.generate(request);
    }
}
