package com.ianxc.vtradereporter.service.submit;

import com.ianxc.vtradereporter.model.api.TradeSubmission;

public interface TradeSubmissionService {
    TradeSubmission submitBundledTrades();
}
