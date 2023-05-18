package com.mp.PLine.src.myPage;

import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.src.feed.FeedRepository;
import com.mp.PLine.src.feed.dto.FeedRes;
import com.mp.PLine.src.member.entity.Member;
import com.mp.PLine.src.myPage.dto.GetUserRes;
import com.mp.PLine.src.myPage.dto.PatchUserReq;
import com.mp.PLine.utils.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {
    private final MyPageRepository myPageRepository;
    private final FeedRepository feedRepository;

    /* 내 정보 반환 API */
    public GetUserRes getUser(Long userId) throws BaseException {
        // userID를 이용해서 존재하는 유저인지 확인
        Optional<Member> member = myPageRepository.findByIdAndStatus(userId, Status.A);
        if(member.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_USER);

        Member info = member.get();
        List<FeedRes> feedList = feedRepository.findAllByUserIdAndStatus(userId, Status.A);

        return new GetUserRes(info.getId(), info.getName(), info.getNickname(), info.getBirth(), info.getPhone(),
                info.getGender().equals("F") ? "여" : "남", blood(info.getRh(), info.getAbo()), info.getLocation(), info.getProfileImg(), feedList);
    }

    public String blood(int rh, int abo) {
        String blood = "";
        if(rh == 0) blood += "Rh+";
        else if(rh == 1) blood += "Rh-";

        if(abo == 0) blood += "A";
        else if(abo == 1) blood += "B";
        else if(abo == 2) blood += "O";
        else if(abo == 3) blood += "AB";

        return blood;
    }

    /* 내 정보 수정 API */
    @Transactional
    public String updateUser(Long userId, PatchUserReq patchUserReq) throws BaseException {
        // userID를 이용해서 존재하는 유저인지 확인
        Optional<Member> member = myPageRepository.findByIdAndStatus(userId, Status.A);
        if(member.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_USER);

        member.get().setNickname(patchUserReq.getNickname());
        member.get().setLocation(patchUserReq.getLocation());
        myPageRepository.save(member.get());

        return "정보가 수정되었습니다.";
    }
}
