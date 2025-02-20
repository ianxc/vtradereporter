package com.ianxc.vtradereporter.service.submit;

import com.ianxc.vtradereporter.repo.TradeRepository;
import org.springframework.stereotype.Service;

@Service
public class TradeSubmissionServiceImpl implements TradeSubmissionService {
    private final TradeRepository tradeRepository;

    public TradeSubmissionServiceImpl(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }
}
