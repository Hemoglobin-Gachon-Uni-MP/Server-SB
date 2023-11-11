package com.mp.PLine.src.admin;

import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.src.myPage.CertificationRepository;
import com.mp.PLine.src.myPage.entity.Certification;
import com.mp.PLine.src.report.ReportRepository;
import com.mp.PLine.src.report.dto.CertificationResponseDto;
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
    private final CertificationRepository certificationRepository;

    public List<ReportResponseDto> readReports(int page) {
        Pageable pageable = PageRequest.of(page, 20);
        return reportRepository.findAllByIsProcessedFalse(pageable)
                .stream()
                .map(Report::toReportResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void executeReport(Long id) throws BaseException {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_REPORT));
        report.execute();
    }

    public List<CertificationResponseDto> readCertifications() {
        return certificationRepository.findAllByIsProcessedFalse().stream()
                .map(Certification::toCertificationResponseDto)
                .collect(Collectors.toList());
    }
}
