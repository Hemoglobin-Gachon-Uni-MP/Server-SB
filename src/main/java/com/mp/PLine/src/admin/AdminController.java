package com.mp.PLine.src.admin;

import com.mp.PLine.utils.JwtService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(tags = "관리자 앱")
@RequestMapping("/admins")
public class AdminController {
    private final AdminService adminService;
    private final JwtService jwtService;


}
