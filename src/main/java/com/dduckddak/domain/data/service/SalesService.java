package com.dduckddak.domain.data.service;

import com.dduckddak.domain.data.dto.TimelyDto;
import com.dduckddak.domain.data.model.Sales;
import com.dduckddak.domain.data.repository.sales.SalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SalesService {

    private final SalesRepository salesRepository;

    public TimelyDto getSalesByCodeTop1(String code) {
        Sales sales = salesRepository.findTop1ByTownCodeOrderByQuarterDesc(code)
                .orElseThrow(() -> new IllegalArgumentException("해당 동의 매출 데이터가 존재하지 않습니다."));

        return TimelyDto.from(sales);
    }
}
