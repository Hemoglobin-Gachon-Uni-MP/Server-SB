package com.mp.PLine;

import com.mp.PLine.source.feed.entity.Comment;
import com.mp.PLine.source.feed.entity.Feed;
import com.mp.PLine.source.feed.repository.CommentRepository;
import com.mp.PLine.source.feed.repository.FeedRepository;
import com.mp.PLine.source.member.MemberRepository;
import com.mp.PLine.source.member.entity.Member;
import com.mp.PLine.utils.entity.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class PLineApplicationTest {
	private final MemberRepository memberRepository;
	private final FeedRepository feedRepository;
	private final CommentRepository commentRepository;

	@Autowired
	PLineApplicationTest(MemberRepository memberRepository, FeedRepository feedRepository,
						 CommentRepository commentRepository) {
		this.memberRepository = memberRepository;
		this.feedRepository = feedRepository;
		this.commentRepository = commentRepository;
	}

	/* 해당 유저가 존재하는지 검사 */
	@Test
	public void existMember() {
		Long myId = 20L;
		Optional<Member> member = memberRepository.findByIdAndStatus(myId, Status.A);

		if(member.isPresent()) {
			System.out.println("해당 유저가 존재합니다.");
		} else {
			System.out.println("해당 유저는 존재하지 않습니다.");
		}
	}

	@Test
	public void createComment() {
		Optional<Member> member = memberRepository.findByIdAndStatus(20L, Status.A);

		if(member.isPresent()) {
			Optional<Feed> feed = feedRepository.findByIdAndStatus(73L, Status.A);
			if(feed.isPresent()) {
				Comment comment = new Comment(member.get(), feed.get(), "제가 헌혈하겠습니다!", Status.A);
				Comment newComment = commentRepository.save(comment);

				System.out.println(newComment.getId());
			} else System.out.println("존재하지 않는 피드입니다.");

		} else System.out.println("존재하지 않는 유저입니다.");
	}

	@Test
	public void checkBlank() {
		Optional<Member> member = memberRepository.findByIdAndStatus(20L, Status.A);

		if(member.isPresent()) {
			Feed feed = new Feed(member.get(), null, 0, 1, "서울시 도봉구", false, Status.A);
			try {
				feedRepository.save(feed);
				System.out.println("게시물 생성에 성공했습니다.");
			} catch (Exception e) {
				System.out.println("게시물 생성에 실패했습니다.");
			}
		} else System.out.println("존재하지 않는 유저입니다.");
	}
}
