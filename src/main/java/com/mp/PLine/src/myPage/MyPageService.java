package com.mp.PLine.src.myPage;

import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.src.feed.repository.CommentRepository;
import com.mp.PLine.src.feed.repository.FeedRepository;
import com.mp.PLine.src.feed.repository.ReplyRepository;
import com.mp.PLine.src.myPage.dto.res.FeedRes;
import com.mp.PLine.src.myPage.dto.util.FeedResI;
import com.mp.PLine.src.member.entity.Member;
import com.mp.PLine.src.myPage.dto.res.GetUserRes;
import com.mp.PLine.src.myPage.dto.req.PatchUserReq;
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

    /* convert blood to string */
    public static String blood(int rh, int abo) {
        String blood = "";
        if(rh == 0) blood += "Rh+";
        else if(rh == 1) blood += "Rh-";

        if(abo == 0) blood += "A";
        else if(abo == 1) blood += "B";
        else if(abo == 2) blood += "O";
        else if(abo == 3) blood += "AB";

        return blood;
    }

    /* Return user information API */
    public GetUserRes getUser(Long userId) throws BaseException {
        // verify user existence using userId
        Member member = myPageRepository.findByIdAndStatus(userId, Status.A)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_USER));
        List<FeedResI> feedList = feedRepository.findAllByMemberIdAndStatus(userId, Status.A);
        List<FeedRes> feedRes = feedList.stream()
                .map(FeedRes::from)
                .collect(Collectors.toList());

        return GetUserRes.of(member, member.getGender().equals("F") ? "여" : "남", feedRes);
    }

    /* Edit user information API */
    @Transactional
    public String updateUser(Long userId, PatchUserReq patchUserReq) throws BaseException {
        // verify user existence using userId
        Member member = myPageRepository.findByIdAndStatus(userId, Status.A)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_USER));

        member.setNickname(patchUserReq.getNickname());
        member.setLocation(patchUserReq.getLocation());
        return "정보가 수정되었습니다.";
    }
}
