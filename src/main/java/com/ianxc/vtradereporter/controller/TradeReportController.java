package com.ianxc.vtradereporter.controller;

import com.ianxc.vtradereporter.model.api.Trade;
import com.ianxc.vtradereporter.model.api.TradePredefinedFilterKind;
import com.ianxc.vtradereporter.model.api.TradeSubmission;
import com.ianxc.vtradereporter.service.query.TradeQueryService;
import com.ianxc.vtradereporter.service.submit.TradeSubmissionService;
import com.ianxc.vtradereporter.util.SpringIoExt;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    List<Trade> getPrefilteredTrades(@RequestParam(value = "kind", defaultValue = "CHALLENGE")
                                     TradePredefinedFilterKind kind) {
        return tqs.getPrefilteredTrades(kind);
    }

    @PostMapping(value = "/submit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    TradeSubmission submitTrades(@RequestPart("tradeFiles") List<? extends MultipartFile> tradeFiles) {
        final var tradeDataStreams = tradeFiles.stream().map(SpringIoExt::inputStreamOf);
        return tss.submitTrades(tradeDataStreams);
    }

    @PostMapping("/submit/bundled")
    TradeSubmission submitBundledTrades() {
        return tss.submitBundledTrades();
    }
}
