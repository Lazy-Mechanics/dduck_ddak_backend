package com.dduckddak.domain.data.service;

import com.dduckddak.domain.data.dto.TimelyDto;
import com.dduckddak.domain.data.repository.sales.SalesRepository;
import com.dduckddak.domain.town.dto.SalesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SalesService {

    private final SalesRepository salesRepository;

    public TimelyDto getSalesByCode(String code) {
        return TimelyDto.from(salesRepository.findAllByTownCodeOrderByQuarterDesc(code));
    }

    public SalesResponse getSalesCompare(String code, String name) {
        return SalesResponse.of(salesRepository.getSalesCompare(code, name, 20241L, 20234L), false);
    }
}
