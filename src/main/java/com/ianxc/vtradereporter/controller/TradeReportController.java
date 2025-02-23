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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/trades")
public class TradeReportController {
    private final TradeQueryService tqs;
    private final TradeSubmissionService tss;

    public TradeReportController(TradeQueryService tqs, TradeSubmissionService tss) {
        this.tqs = tqs;
        this.tss = tss;
    }

    /**
     * Queries trades which satisfy arbitrarily-complex filters implemented within this app.
     * @param kind The redefined filter to use for the query.
     * @return A list of trades which satisfy the predefined filter.
     */
    @GetMapping(value = "/prefiltered", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Trade> getPrefilteredTrades(@RequestParam(value = "kind", defaultValue = "CHALLENGE")
                                     TradePredefinedFilterKind kind) {
        return tqs.getPrefilteredTrades(kind);
    }

    /**
     * Submits a list of user-uploaded trade data files for later querying.
     * @param tradeFiles A list of valid XML files holding trade data.
     * @return A summary of the trade submission request.
     */
    @PostMapping(value = "/batch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    TradeSubmission submitTrades(@RequestPart("tradeFiles") List<? extends MultipartFile> tradeFiles) {
        final var tradeDataStreams = tradeFiles.stream().map(SpringIoExt::inputStreamOf);
        return tss.submitTrades(tradeDataStreams);
    }

    /**
     * Submits a list of trade data files that are bundled with the app for later querying.
     * @return A summary of the trade submission request.
     */
    @PostMapping(value = "/batch/bundled", produces = MediaType.APPLICATION_JSON_VALUE)
    TradeSubmission submitBundledTrades() {
        return tss.submitBundledTrades();
    }
}
