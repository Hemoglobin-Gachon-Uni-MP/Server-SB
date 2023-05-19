package com.mp.PLine.src.feed;

import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.src.feed.dto.*;
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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedService {
    private final FeedRepository feedRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;
    private final MyPageRepository myPageRepository;

    /* 게시물 생성 API */
    @Transactional
    public Long postFeed(PostFeedReq postFeedReq) throws BaseException {
        // userID를 이용해서 존재하는 유저인지 확인
        Optional<Member> member = myPageRepository.findByIdAndStatus(postFeedReq.getUserId(), Status.A);
        if(member.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_USER);

        Feed feed = new Feed(member.get(), postFeedReq.getContext(), postFeedReq.getAbo(), postFeedReq.getRh(),
                postFeedReq.getLocation(), postFeedReq.getIsReceiver(), Status.A);
        Feed newFeed = feedRepository.save(feed);

        return newFeed.getId();
    }

    /* 게시물 정보 반환 API */
    public GetFeedRes getFeed(Long feedId) throws BaseException {
        // feedID를 이용해서 존재하는 게시물인지 확인
        Optional<Feed> feed = feedRepository.findByIdAndStatus(feedId, Status.A);
        if(feed.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_FEED);

        Feed feedRes = feed.get();
        Member userRes = feedRes.getUser();

        List<CommentRes> comment = getComments(feedId);

        return new GetFeedRes(feedRes.getId(), userRes.getId(), userRes.getProfileImg(), userRes.getNickname(),
                feedRes.getContext(), comment.size(), comment,
                shortDate(feedRes.getCreatedAt()), feedRes.getAbo(), feedRes.getRh(),
                feedRes.getLocation(), feedRes.getIsReceiver());
    }

    /* 날짜 표기 */
    public String shortDate(Timestamp createdAt) {
        DateFormat format = new SimpleDateFormat("MM/dd");
        return format.format(new Date(createdAt.getTime()));
    }

    public String longDate(Timestamp createdAt) {
        DateFormat format = new SimpleDateFormat("MM/dd a KK:mm");
        return format.format(new Date(createdAt.getTime()));
    }

    /* 게시물 댓글 리스트 */
    public List<CommentRes> getComments(Long feedId) {
        List<CommentResI> commentResI = commentRepository.findByFeedId(feedId);
        List<CommentRes> commentRes = new ArrayList<>();

        for (CommentResI cur : commentResI) {
            List<ReplyRes> reply = replyRepository.findByCommentId(cur.getCommentId());
            commentRes.add(new CommentRes(cur.getCommentId(), cur.getUserId(), cur.getProfileImg(), cur.getNickname(),
                    cur.getContext(), reply, longDate(cur.getDate())));
        }

        return commentRes;
    }

    /* 게시물 수정 API */
    @Transactional
    public String updateFeed(Long feedId, PatchFeedReq patchFeedReq) throws BaseException {
        // userID를 이용해서 존재하는 유저인지 확인
        Optional<Member> member = myPageRepository.findByIdAndStatus(patchFeedReq.getUserId(), Status.A);
        if(member.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_USER);

        // feedID를 이용해서 존재하는 게시물인지 확인
        Optional<Feed> feed = feedRepository.findByIdAndStatus(feedId, Status.A);
        if(feed.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_FEED);

        Feed updateFeed = feed.get();
        if(!patchFeedReq.getUserId().equals(updateFeed.getUser().getId())) throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);

        updateFeed.setContext(patchFeedReq.getContext());
        feedRepository.save(updateFeed);

        return "게시물 수정이 완료되었습니다.";
    }

    /* 게시물 삭제 API */
    @Transactional
    public String deleteFeed(Long feedId, Long userId) throws BaseException {
        // userID를 이용해서 존재하는 유저인지 확인
        Optional<Member> member = myPageRepository.findByIdAndStatus(userId, Status.A);
        if(member.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_USER);

        // feedID를 이용해서 존재하는 게시물인지 확인
        Optional<Feed> feed = feedRepository.findByIdAndStatus(feedId, Status.A);
        if(feed.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_FEED);

        Feed updateFeed = feed.get();
        if(!userId.equals(updateFeed.getUser().getId())) throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);

        updateFeed.setStatus(Status.D);
        feedRepository.save(updateFeed);

        return "게시물이 삭제되었습니다.";
    }

    /* 댓글 달기 API */
    @Transactional
    public Long postComment(Long feedId, PostCommentReq postCommentReq) throws BaseException {
        // userID를 이용해서 존재하는 유저인지 확인
        Optional<Member> member = myPageRepository.findByIdAndStatus(postCommentReq.getUserId(), Status.A);
        if(member.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_USER);

        // feedID를 이용해서 존재하는 게시물인지 확인
        Optional<Feed> feed = feedRepository.findByIdAndStatus(feedId, Status.A);
        if(feed.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_FEED);

        Comment comment = new Comment(member.get(), feed.get(), postCommentReq.getContext(), Status.A);
        Comment newComment = commentRepository.save(comment);

        return newComment.getId();
    }

    /* 댓글 삭제 API */
    @Transactional
    public String deleteComment(Long commentId, Long userId) throws BaseException {
        // userID를 이용해서 존재하는 유저인지 확인
        Optional<Member> member = myPageRepository.findByIdAndStatus(userId, Status.A);
        if(member.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_USER);

        // commentID를 이용해서 존재하는 댓글인지 확인
        Optional<Comment> comment = commentRepository.findByIdAndStatus(commentId, Status.A);
        if(comment.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_COMMENT);

        Comment updateComment = comment.get();
        if(!updateComment.getUser().getId().equals(userId)) throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);

        updateComment.setStatus(Status.D);
        commentRepository.save(updateComment);

        return "댓글이 삭제되었습니다.";
    }

    /* 답글 달기 API */
    @Transactional
    public Long postReply(Long commentId, PostReplyReq postReplyReq) throws BaseException {
        // userID를 이용해서 존재하는 유저인지 확인
        Optional<Member> member = myPageRepository.findByIdAndStatus(postReplyReq.getUserId(), Status.A);
        if(member.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_USER);

        // feedID를 이용해서 존재하는 게시물인지 확인
        Optional<Feed> feed = feedRepository.findByIdAndStatus(postReplyReq.getFeedId(), Status.A);
        if(feed.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_FEED);

        // commentID를 이용해서 존재하는 댓글인지 확인
        Optional<Comment> comment = commentRepository.findByIdAndStatus(commentId, Status.A);
        if(comment.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_COMMENT);

        if(!feed.get().getId().equals(comment.get().getFeed().getId())) throw new BaseException(BaseResponseStatus.POST_FEEDS_INVALID_FEED);

        Reply reply = new Reply(member.get(), feed.get(), comment.get(), postReplyReq.getContext(), Status.A);
        Reply newReply = replyRepository.save(reply);

        return newReply.getId();
    }

    /* 댓글 삭제 API */
    @Transactional
    public String deleteReply(Long replyId, Long userId) throws BaseException {
        // userID를 이용해서 존재하는 유저인지 확인
        Optional<Member> member = myPageRepository.findByIdAndStatus(userId, Status.A);
        if(member.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_USER);

        // replyID를 이용해서 존재하는 답글인지 확인
        Optional<Reply> reply = replyRepository.findByIdAndStatus(replyId, Status.A);
        if(reply.isEmpty()) throw new BaseException(BaseResponseStatus.INVALID_REPLY);

        Reply updateReply = reply.get();
        if(!updateReply.getUser().getId().equals(userId)) throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);

        updateReply.setStatus(Status.D);
        replyRepository.save(updateReply);

        return "답글이 삭제되었습니다.";
    }
}
