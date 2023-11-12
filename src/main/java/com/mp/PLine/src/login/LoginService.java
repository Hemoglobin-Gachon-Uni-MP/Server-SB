package com.mp.PLine.src.login;

import com.mp.PLine.src.admin.AdminRepository;
import com.mp.PLine.src.admin.entity.Admin;
import com.mp.PLine.src.member.MemberRepository;
import com.mp.PLine.src.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        // 관리자 로그인
        if (id.startsWith("a")) {
            Admin admin = adminRepository.findAdminByAdminKey(id)
                    .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 관리자입니다."));

            return org.springframework.security.core.userdetails.User.builder()
                    .username(admin.getAdminKey())
                    .roles(admin.getRole().name())
                    .build();
        }
        Member member = memberRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new UsernameNotFoundException("해당 계정이 존재하지 않습니다."));

        return org.springframework.security.core.userdetails.User.builder()
                .username(Long.toString(member.getId()))
                .roles(member.getRole().name())
                .build();
    }
}
