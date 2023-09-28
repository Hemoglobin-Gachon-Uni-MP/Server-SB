package com.mp.PLine.src.feed;

import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.src.feed.dto.req.PatchFeedReq;
import com.mp.PLine.src.feed.dto.req.PostCommentReq;
import com.mp.PLine.src.feed.dto.req.PostReplyReq;
import com.mp.PLine.src.feed.dto.res.CommentRes;
import com.mp.PLine.src.feed.dto.res.GetFeedRes;
import com.mp.PLine.src.feed.dto.res.GetFeedsRes;
import com.mp.PLine.src.feed.dto.res.ReplyRes;
import com.mp.PLine.src.feed.dto.util.*;
import com.mp.PLine.src.feed.dto.req.PostFeedReq;
import com.mp.PLine.src.feed.entity.Comment;
import com.mp.PLine.src.feed.entity.Feed;
import com.mp.PLine.src.feed.entity.Reply;
import com.mp.PLine.src.feed.repository.CommentRepository;
import com.mp.PLine.src.feed.repository.FeedRepository;
import com.mp.PLine.src.feed.repository.ReplyRepository;
import com.mp.PLine.src.member.entity.Member;
import com.mp.PLine.src.myPage.MyPageRepository;
import com.mp.PLine.utils.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedService {
    private final FeedRepository feedRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;
    private final MyPageRepository myPageRepository;

    /* date format */
    public static String shortDate(Timestamp createdAt) {
        DateFormat format = new SimpleDateFormat("MM/dd");
        return format.format(new Date(createdAt.getTime()));
    }

    public static String longDate(Timestamp createdAt) {
        DateFormat format = new SimpleDateFormat("MM/dd a KK:mm");
        return format.format(new Date(createdAt.getTime()));
    }

    /* Create feed API */
    @Transactional
    public Long postFeed(PostFeedReq postFeedReq) throws BaseException {
        // verify user existence using userID
        Member member = myPageRepository.findByIdAndStatus(postFeedReq.getUserId(), Status.A)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_USER));
        // save data
        Feed newFeed = feedRepository.save(Feed.of(member, postFeedReq, Status.A));
        return newFeed.getId();
    }

    /* Return feed list API */
    public List<GetFeedsRes> getFeeds() throws BaseException {
        // mapping received values to list
        List<GetFeedsResI> getFeedsResI = feedRepository.findAllByStatus();

        return getFeedsResI.stream()
                .map(GetFeedsRes::from)
                .collect(Collectors.toList());
    }

    /* Return feed detail API */
    public GetFeedRes getFeed(Long feedId) throws BaseException {
        // verify feed existence using feedId
        Optional<Feed> feed = feedRepository.findByIdAndStatus(feedId, Status.A);
        if(feed.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_FEED);
        Feed feedRes = feed.get();

        return GetFeedRes.of(feedRes, feedRes.getUser(), getComments(feedId));
    }

    /* get feed's comment list */
    public CommentInfo getComments(Long feedId) {
        List<CommentResI> commentResI = commentRepository.findByFeedId(feedId);
        List<CommentRes> commentRes = new ArrayList<>();

        int replyCnt = 0;
        for (CommentResI cur : commentResI) {
            List<ReplyRes> replyRes = getReplies(cur.getCommentId());
            commentRes.add(CommentRes.of(cur,replyRes));
            replyCnt += replyRes.size();
        }

        return new CommentInfo(replyCnt + commentRes.size(), commentRes);
    }

    /* get comment's reply list */
    public List<ReplyRes> getReplies(Long commentId) {
        // mapping received values to list
        List<ReplyResI> replyResI = replyRepository.findByCommentId(commentId);

        return replyResI.stream().
                map(ReplyRes::from)
                .collect(Collectors.toList());
    }

    /* Edit feed API */
    @Transactional
    public String updateFeed(Long feedId, PatchFeedReq patchFeedReq) throws BaseException {
        // verify user existence using userId
        Member member = myPageRepository.findByIdAndStatus(patchFeedReq.getUserId(), Status.A)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_USER));
        // verify feed existence using feedId
        Feed updateFeed = feedRepository.findByIdAndStatus(feedId, Status.A)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_FEED));
        if (!patchFeedReq.getUserId().equals(updateFeed.getUser().getId())) throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);

        // save data
        updateFeed.setContext(patchFeedReq.getContext());
        feedRepository.save(updateFeed);

        return "게시물 수정이 완료되었습니다.";
    }

    /* Delete feed API */
    @Transactional
    public String deleteFeed(Long feedId, Long userId) throws BaseException {
        // verify user existence using userId
        myPageRepository.findByIdAndStatus(userId, Status.A)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_USER));
        // verify feed existence using feedId
        Feed feed = feedRepository.findByIdAndStatus(feedId, Status.A)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_FEED));
        Member feedOwner = feed.getUser();
        if (!userId.equals(feedOwner.getId())) throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);

        // delete feed and feed's comment & reply
        feed.setStatus(Status.D);
        commentRepository.setCommentByFeedStatus(feedId);
        replyRepository.setReplyByFeedStatus(feedId);

        return "게시물이 삭제되었습니다.";
    }

    /* Create comment API */
    @Transactional
    public Long postComment(Long feedId, PostCommentReq postCommentReq) throws BaseException {
        // verify user existence using userId
        Member member = myPageRepository.findByIdAndStatus(postCommentReq.getUserId(), Status.A)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_USER));
        // verify feed existence using feedId
        Feed feed = feedRepository.findByIdAndStatus(feedId, Status.A)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_FEED));

        Comment newComment = commentRepository.save(Comment.of(member, feed, postCommentReq.getContext(), Status.A));
        return newComment.getId();
    }

    /* Delete comment API */
    @Transactional
    public String deleteComment(Long commentId, Long userId) throws BaseException {
        // verify user existence using userId
        Member member = myPageRepository.findByIdAndStatus(userId, Status.A)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_USER));
        // verify feed existence using feedId
        Comment comment = commentRepository.findByIdAndStatus(commentId, Status.A)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_COMMENT));
        Member commentOwner = comment.getUser();
        if(!commentOwner.getId().equals(userId)) throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);

        // delete comment and comment's reply
        comment.setStatus(Status.D);
        replyRepository.setReplyByCommentStatus(commentId);
        return "댓글이 삭제되었습니다.";
    }

    /* Create reply API */
    @Transactional
    public Long postReply(Long commentId, PostReplyReq postReplyReq) throws BaseException {
        // verify user existence using userId
        Member member = myPageRepository.findByIdAndStatus(postReplyReq.getUserId(), Status.A)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_USER));
        // verify feed existence using feedId
        Feed feed = feedRepository.findByIdAndStatus(postReplyReq.getFeedId(), Status.A)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_FEED));
        // verify comment existence using commentId
        Comment comment = commentRepository.findByIdAndStatus(commentId, Status.A)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_COMMENT));

        if(!feed.getId().equals(comment.getFeed().getId())) throw new BaseException(BaseResponseStatus.POST_FEEDS_INVALID_FEED);

        Reply reply = Reply.of(member, feed, comment, postReplyReq.getContext(), Status.A);
        Reply newReply = replyRepository.save(reply);
        return newReply.getId();
    }

    /* Delete reply API */
    @Transactional
    public String deleteReply(Long replyId, Long userId) throws BaseException {
        // verify user existence using userId
        myPageRepository.findByIdAndStatus(userId, Status.A)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_USER));
        // verify reply existence using replyId
        Reply updateReply = replyRepository.findByIdAndStatus(replyId, Status.A)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_REPLY));
        Member replyOwner = updateReply.getUser();
        if(!replyOwner.getId().equals(userId)) throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);

        // delete reply
        updateReply.setStatus(Status.D);
        return "답글이 삭제되었습니다.";
    }

}
