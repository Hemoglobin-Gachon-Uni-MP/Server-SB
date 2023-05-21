package com.mp.PLine.src.myPage;

import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.src.feed.repository.FeedRepository;
import com.mp.PLine.src.myPage.dto.FeedRes;
import com.mp.PLine.src.myPage.dto.FeedResI;
import com.mp.PLine.src.member.entity.Member;
import com.mp.PLine.src.myPage.dto.GetUserRes;
import com.mp.PLine.src.myPage.dto.PatchUserReq;
import com.mp.PLine.utils.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        List<FeedRes> feedRes = feedList.stream()
                .map(d -> FeedRes.builder()
                        .feedId(d.getFeedId())
                        .userId(d.getUserId())
                        .nickname(d.getNickname())
                        .profileImg(d.getProfileImg())
                        .context(d.getContext())
                        .commentCnt(d.getCommentCnt() + d.getReplyCnt())
                        .date(d.getDate())
                        .isReceiver(d.getIsReceiver()).build())
                .collect(Collectors.toList());

        return new GetUserRes(info.getId(), info.getName(), info.getNickname(), info.getBirth(), info.getPhone(),
                info.getGender().equals("F") ? "여" : "남", blood(info.getRh(), info.getAbo()), info.getLocation(), info.getProfileImg(), feedRes);
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
