package com.telmaneng.tistore.service;

import com.telmaneng.tistore.pojo.TiPartOrder;
import com.telmaneng.tistore.repository.TiRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@Service
@ComponentScan("com.telmaneng.tistore.repository")
public class TiStoreAppService {
    private final Logger logger = LoggerFactory.getLogger(TiStoreAppService.class);

    private final TiRequestRepository tiRequestRepository;

    public TiStoreAppService(TiRequestRepository tiRequestRepository) {
        this.tiRequestRepository = tiRequestRepository;
    }

    public TiPartOrder addTiPartRequest(TiPartOrder tiPartOrder) {
        return tiRequestRepository.saveAndFlush(tiPartOrder);
    }

}
