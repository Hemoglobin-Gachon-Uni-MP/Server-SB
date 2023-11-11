package com.mp.PLine.src.admin;

import com.mp.PLine.src.report.ReportRepository;
import com.mp.PLine.src.report.dto.ReportResponseDto;
import com.mp.PLine.src.report.entity.Report;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {
    private final ReportRepository reportRepository;

    public List<ReportResponseDto.ReportReadResponseDto> readReports(int page) {
        Pageable pageable = PageRequest.of(page, 20);
        return reportRepository.findAllByProcessStatusIsFalse(pageable)
                .stream()
                .map(Report::toReportResponse)
                .collect(Collectors.toList());
    }
}
