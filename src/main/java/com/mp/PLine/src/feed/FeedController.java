package com.mp.PLine.src.feed;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(tags = "홈 - 피드")
@RequestMapping("/feeds")
public class FeedController {
    private final FeedService feedService;
    private final FeedRepository feedRepository;

    /**
     * 도장 개수 반환 API
     * 전체 도장 개수 & 현재 스탬프지의 도장 개수 반환
     * [GET] /attendances/{userIdx}
     */
//    @ApiOperation("도장 개수 반환 API")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "Authorization", paramType = "header", value = "서비스 자체 jwt 토큰")
//    })
//    @ApiResponses({
//            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
//            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
//            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다."),
//            @ApiResponse(code = 2004, message = "존재하지 않는 유저입니다.")
//    })
//    @ResponseBody
//    @GetMapping("/{userId}")
//    public BaseResponse<GetStampRes> getStamps(@PathVariable Long userId) {
//        try{
//            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            String user = principal.getUsername();
//
//            Long userIdByAuth = Long.parseLong(user);
//
//            if(!Objects.equals(userId, userIdByAuth)){
//                return new BaseResponse<>(INVALID_JWT);
//            }
//            else{
//                GetStampRes getStampRes = attendanceProvider.getStamps(userId);
//                return new BaseResponse<>(getStampRes);
//            }
//        } catch(BaseException e){
//            return new BaseResponse<>(e.getStatus());
//        }
//    }
}
