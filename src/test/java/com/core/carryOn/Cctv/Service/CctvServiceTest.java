package com.core.carryOn.Cctv.Service;

import com.core.carryOn.Cctv.Dto.CctvDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CctvServiceTest {

    private final CctvService cctvService;
    Logger logger = LoggerFactory.getLogger(CctvServiceTest.class);

    @Autowired
    public CctvServiceTest(CctvService cctvService) {
        this.cctvService = cctvService;
    }

    @Test
    @DisplayName("cctv 목록 가져오기")
    public void findAll() {
        List<CctvDto> cctvList = cctvService.getCctvList(129.296619, 35.531347, 129.33974, 35.537136);
        logger.info(cctvList.toString());
        assertNotNull(cctvList);
    }
}