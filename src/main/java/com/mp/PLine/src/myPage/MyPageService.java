package com.mp.PLine.src.myPage;

import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.src.feed.FeedRepository;
import com.mp.PLine.src.feed.dto.FeedRes;
import com.mp.PLine.src.feed.dto.FeedResI;
import com.mp.PLine.src.feed.entity.Feed;
import com.mp.PLine.src.member.MemberRepository;
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
        List<FeedResI> feedList = feedRepository.findAllByUserIdAndStatus(userId, Status.A);

        return new GetUserRes(info.getId(), info.getName(), info.getNickname(), info.getBirth(), info.getPhone(),
                info.getGender().equals("F") ? "여" : "남", info.getRh() + info.getAbo(), info.getLocation(), info.getProfileImg(), feedList);
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
