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

    /* Create feed API */
    @Transactional
    public Long postFeed(PostFeedReq postFeedReq) throws BaseException {
        // verify user existence using userID
        Optional<Member> member = myPageRepository.findByIdAndStatus(postFeedReq.getUserId(), Status.A);
        if(member.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_USER);

        // save data
        Feed feed = new Feed(member.get(), postFeedReq.getContext(), postFeedReq.getAbo(), postFeedReq.getRh(),
                postFeedReq.getLocation(), postFeedReq.getIsReceiver(), Status.A);
        Feed newFeed = feedRepository.save(feed);

        return newFeed.getId();
    }

    /* Return feed list API */
    public List<GetFeedsRes> getFeeds() throws BaseException {
        // mapping received values to list
        List<GetFeedsResI> getFeedsResI = feedRepository.findAllByStatus();

        return getFeedsResI.stream()
                .map(d -> GetFeedsRes.builder()
                        .feedId(d.getFeedId())
                        .userId(d.getUserId())
                        .profileImg(d.getProfileImg())
                        .nickname(d.getNickname())
                        .context(d.getContext())
                        .commentCnt(d.getCommentCnt() + d.getReplyCnt())
                        .date(d.getDate())
                        .abo(d.getAbo())
                        .rh(d.getRh())
                        .location(d.getLocation())
                        .isReceiver(d.getIsReceiver()).build())
                    .collect(Collectors.toList());
    }

    /* Return feed detail API */
    public GetFeedRes getFeed(Long feedId) throws BaseException {
        // verify feed existence using feedId
        Optional<Feed> feed = feedRepository.findByIdAndStatus(feedId, Status.A);
        if(feed.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_FEED);

        Feed feedRes = feed.get();
        Member userRes = feedRes.getUser();

        CommentInfo comment = getComments(feedId);

        return new GetFeedRes(feedRes.getId(), userRes.getId(), userRes.getProfileImg(), userRes.getNickname(),
                feedRes.getContext(), comment.getCommentCnt(), comment.getCommentRes(),
                shortDate(feedRes.getCreatedAt()), feedRes.getAbo(), feedRes.getRh(),
                feedRes.getLocation(), feedRes.getIsReceiver());
    }

    /* date format */
    public String shortDate(Timestamp createdAt) {
        DateFormat format = new SimpleDateFormat("MM/dd");
        return format.format(new Date(createdAt.getTime()));
    }

    public String longDate(Timestamp createdAt) {
        DateFormat format = new SimpleDateFormat("MM/dd a KK:mm");
        return format.format(new Date(createdAt.getTime()));
    }

    /* get feed's comment list */
    public CommentInfo getComments(Long feedId) {
        List<CommentResI> commentResI = commentRepository.findByFeedId(feedId);
        List<CommentRes> commentRes = new ArrayList<>();

        int replyCnt = 0;

        for (CommentResI cur : commentResI) {
            List<ReplyRes> replyRes = getReplies(cur.getCommentId());
            commentRes.add(new CommentRes(cur.getCommentId(), cur.getUserId(), cur.getProfileImg(), cur.getNickname(),
                    cur.getContext(), replyRes, longDate(cur.getDate())));

            replyCnt += replyRes.size();
        }

        return new CommentInfo(replyCnt + commentRes.size(), commentRes);
    }

    /* get comment's reply list */
    public List<ReplyRes> getReplies(Long commentId) {
        // mapping received values to list
        List<ReplyResI> replyResI = replyRepository.findByCommentId(commentId);

        return replyResI.stream().
                map(d -> ReplyRes.builder()
                        .replyId(d.getReplyId())
                        .userId(d.getUserId())
                        .profileImg(d.getProfileImg())
                        .nickname(d.getNickname())
                        .context(d.getContext())
                        .date(longDate(d.getDate())).build())
                .collect(Collectors.toList());
    }

    /* Edit feed API */
    @Transactional
    public String updateFeed(Long feedId, PatchFeedReq patchFeedReq) throws BaseException {
        // verify user existence using userId
        Optional<Member> member = myPageRepository.findByIdAndStatus(patchFeedReq.getUserId(), Status.A);
        if(member.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_USER);

        // verify feed existence using feedId
        Optional<Feed> feed = feedRepository.findByIdAndStatus(feedId, Status.A);
        if(feed.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_FEED);

        Feed updateFeed = feed.get();
        if(!patchFeedReq.getUserId().equals(updateFeed.getUser().getId())) throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);

        // save data
        updateFeed.setContext(patchFeedReq.getContext());
        feedRepository.save(updateFeed);

        return "게시물 수정이 완료되었습니다.";
    }

    /* Delete feed API */
    @Transactional
    public String deleteFeed(Long feedId, Long userId) throws BaseException {
        // verify user existence using userId
        Optional<Member> member = myPageRepository.findByIdAndStatus(userId, Status.A);
        if(member.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_USER);

        // verify feed existence using feedId
        Optional<Feed> feed = feedRepository.findByIdAndStatus(feedId, Status.A);
        if(feed.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_FEED);

        Feed updateFeed = feed.get();
        if(!userId.equals(updateFeed.getUser().getId())) throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);

        // delete feed and feed's comment & reply
        updateFeed.setStatus(Status.D);
        feedRepository.save(updateFeed);

        commentRepository.setCommentByFeedStatus(feedId);
        replyRepository.setReplyByFeedStatus(feedId);

        return "게시물이 삭제되었습니다.";
    }

    /* Create comment API */
    @Transactional
    public Long postComment(Long feedId, PostCommentReq postCommentReq) throws BaseException {
        // verify user existence using userId
        Optional<Member> member = myPageRepository.findByIdAndStatus(postCommentReq.getUserId(), Status.A);
        if(member.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_USER);

        // verify feed existence using feedId
        Optional<Feed> feed = feedRepository.findByIdAndStatus(feedId, Status.A);
        if(feed.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_FEED);

        Comment comment = new Comment(member.get(), feed.get(), postCommentReq.getContext(), Status.A);
        Comment newComment = commentRepository.save(comment);

        return newComment.getId();
    }

    /* Delete comment API */
    @Transactional
    public String deleteComment(Long commentId, Long userId) throws BaseException {
        // verify user existence using userId
        Optional<Member> member = myPageRepository.findByIdAndStatus(userId, Status.A);
        if(member.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_USER);

        // verify feed existence using feedId
        Optional<Comment> comment = commentRepository.findByIdAndStatus(commentId, Status.A);
        if(comment.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_COMMENT);

        Comment updateComment = comment.get();
        if(!updateComment.getUser().getId().equals(userId)) throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);

        // delete comment and comment's reply
        updateComment.setStatus(Status.D);
        commentRepository.save(updateComment);

        replyRepository.setReplyByCommentStatus(commentId);

        return "댓글이 삭제되었습니다.";
    }

    /* Create reply API */
    @Transactional
    public Long postReply(Long commentId, PostReplyReq postReplyReq) throws BaseException {
        // verify user existence using userId
        Optional<Member> member = myPageRepository.findByIdAndStatus(postReplyReq.getUserId(), Status.A);
        if(member.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_USER);

        // verify feed existence using feedId
        Optional<Feed> feed = feedRepository.findByIdAndStatus(postReplyReq.getFeedId(), Status.A);
        if(feed.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_FEED);

        // verify comment existence using commentId
        Optional<Comment> comment = commentRepository.findByIdAndStatus(commentId, Status.A);
        if(comment.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_COMMENT);

        if(!feed.get().getId().equals(comment.get().getFeed().getId())) throw new BaseException(BaseResponseStatus.POST_FEEDS_INVALID_FEED);

        Reply reply = new Reply(member.get(), feed.get(), comment.get(), postReplyReq.getContext(), Status.A);
        Reply newReply = replyRepository.save(reply);

        return newReply.getId();
    }

    /* Delete reply API */
    @Transactional
    public String deleteReply(Long replyId, Long userId) throws BaseException {
        // verify user existence using userId
        Optional<Member> member = myPageRepository.findByIdAndStatus(userId, Status.A);
        if(member.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_USER);

        // verify reply existence using replyId
        Optional<Reply> reply = replyRepository.findByIdAndStatus(replyId, Status.A);
        if(reply.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_REPLY);

        Reply updateReply = reply.get();
        if(!updateReply.getUser().getId().equals(userId)) throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);

        // delete reply
        updateReply.setStatus(Status.D);
        replyRepository.save(updateReply);

        return "답글이 삭제되었습니다.";
    }
}
