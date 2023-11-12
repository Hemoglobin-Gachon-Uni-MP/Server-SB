package com.mp.PLine.src.admin;

import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.src.admin.entity.Admin;
import com.mp.PLine.src.myPage.CertificationRepository;
import com.mp.PLine.src.myPage.entity.Certification;
import com.mp.PLine.src.report.ReportRepository;
import com.mp.PLine.src.report.dto.CertificationResponseDto;
import com.mp.PLine.src.report.dto.ReportResponseDto;
import com.mp.PLine.src.report.entity.Report;
import com.mp.PLine.utils.JwtService;
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
    private final AdminRepository adminRepository;
    private final JwtService jwtService;
    private final ReportRepository reportRepository;
    private final CertificationRepository certificationRepository;

    @Transactional
    public void signUp(AdminDto.RequestDto adminRequest) throws BaseException {
        if (!adminRequest.getKey().startsWith("a")) {
            throw new BaseException(BaseResponseStatus.INVALID_ADMIN_KEY);
        }
        adminRepository.save(Admin.from(adminRequest.getKey()));
    }

    public AdminDto.ResponseDto login(AdminDto.RequestDto adminRequest) throws BaseException {
        Admin admin = adminRepository.findAdminByAdminKey(adminRequest.getKey())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXIST_ADMIN_ACCOUNT));
        return new AdminDto.ResponseDto(jwtService.createAccessToken(admin.getAdminKey()));
    }

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

    @Transactional
    public void executeCertification(Long id) throws BaseException {
        Certification certification = certificationRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_REPORT));
        certification.excecute();
    }
}
