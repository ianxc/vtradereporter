package com.ianxc.vtradereporter.controller;

import com.ianxc.vtradereporter.model.api.Trade;
import com.ianxc.vtradereporter.service.TradeQueryService;
import com.ianxc.vtradereporter.service.TradeSubmissionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/trades")
public class TradeReportController {
    private final TradeQueryService tqs;
    private final TradeSubmissionService tss;

    public TradeReportController(TradeQueryService tqs, TradeSubmissionService tss) {
        this.tqs = tqs;
        this.tss = tss;
    }

    @GetMapping("/prefiltered")
    List<Trade> getPrefilteredTrades() {
        return tqs.getPrefilteredTrades();
    }

    @PostMapping("/submit")
    TradeSubmission submitTrades() {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/submit/bundled")
    TradeSubmission submitBundledTrades() {
        throw new UnsupportedOperationException();
    }
}
