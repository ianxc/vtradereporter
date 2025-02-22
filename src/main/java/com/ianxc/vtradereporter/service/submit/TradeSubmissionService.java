package com.ianxc.vtradereporter.service.submit;

import com.ianxc.vtradereporter.model.api.TradeSubmission;

import java.io.InputStream;
import java.util.stream.Stream;

public interface TradeSubmissionService {
    TradeSubmission submitTrades(Stream<? extends InputStream> tradeDataStreams);

    TradeSubmission submitBundledTrades();
}
