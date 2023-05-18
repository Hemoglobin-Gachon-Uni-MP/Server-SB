package com.mp.PLine.src.feed;

import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.src.feed.dto.PostFeedReq;
import com.mp.PLine.src.feed.entity.Feed;
import com.mp.PLine.src.member.entity.Member;
import com.mp.PLine.src.myPage.MyPageRepository;
import com.mp.PLine.utils.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedService {
    private final FeedRepository feedRepository;
    private final MyPageRepository myPageRepository;

    @Transactional
    public Long postFeed(PostFeedReq postFeedReq) throws BaseException {
        // userID를 이용해서 존재하는 유저인지 확인
        Optional<Member> member = myPageRepository.findByIdAndStatus(postFeedReq.getUserId(), Status.A);
        if(member.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_USER);

        PostFeedReq info = postFeedReq;
        Feed feed = new Feed(member.get(), info.getContext(), info.getAbo(), info.getRh(),
                info.getLocation(), info.getIsReceiver(), Status.A);
        Feed newFeed = feedRepository.save(feed);

        return newFeed.getId();
    }
}
