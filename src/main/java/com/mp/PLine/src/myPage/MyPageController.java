package com.mp.PLine.src.myPage;

import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponse;
import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.src.myPage.dto.res.GetUserRes;
import com.mp.PLine.src.myPage.dto.req.PatchUserReq;
import com.mp.PLine.utils.JwtService;
import com.mp.PLine.utils.Validation;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(tags = "마이페이지")
@RequestMapping("/mypages")
public class MyPageController {
    private final MyPageService myPageService;
    private final JwtService jwtService;

    /**
     * Return user information API
     * [GET] /mypages/{userId}
     */
    @ApiOperation("내 정보 반환 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-ACCESS-TOKEN", required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다."),
            @ApiResponse(code = 2028, message = "존재하지 않는 유저입니다.")
    })
    @GetMapping("/{userId}")
    public BaseResponse<GetUserRes> getUser(@PathVariable Long userId) {
        try {
            // get jwt from header
            Long userIdByJwt = jwtService.getUserId();
            if (!userId.equals(userIdByJwt)) {
                return new BaseResponse<>(BaseResponseStatus.INVALID_JWT);
            }

            return new BaseResponse<>(myPageService.getUser(userId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }

    /**
     * Edit user API
     * [PATCH] /mypages/{userId}
     */
    @ApiOperation("내 정보 수정 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-ACCESS-TOKEN", required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다."),
            @ApiResponse(code = 2011, message = "닉네임을 입력해주세요."),
            @ApiResponse(code = 2015, message = "위치를 입력해주세요."),
            @ApiResponse(code = 2020, message = "닉네임 길이를 확인해주세요."),
            @ApiResponse(code = 2028, message = "존재하지 않는 유저입니다.")
    })
    @PatchMapping("/{userId}")
    public BaseResponse<String> updateUser(@PathVariable Long userId, @RequestBody PatchUserReq patchUserReq) {
        try {
            // get jwt from header
            Long userIdByJwt = jwtService.getUserId();
            if(!userId.equals(userIdByJwt)) {
                return new BaseResponse<>(BaseResponseStatus.INVALID_JWT);
            }

            // get jwt from header
            BaseResponseStatus status = Validation.checkUpdateUser(patchUserReq);
            if(status != BaseResponseStatus.SUCCESS) return new BaseResponse<>(status);

            return new BaseResponse<>(myPageService.updateUser(userId, patchUserReq));

        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
