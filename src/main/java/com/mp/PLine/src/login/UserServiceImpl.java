package com.mp.PLine.src.login;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

//    private final SqlSession sqlSession;
//
//    public UserServiceImpl(SqlSession ss) {
//        this.sqlSession = ss;
//    }

    /**
     * 로그인 구현체
     *
     * @param userDto UserDto
     * @return Optional<UserDto>
     */
    @Override
    public Optional<UserDto> login(UserDto userDto) {
//        UserMapper um = sqlSession.getMapper(UserMapper.class);
//        return um.login(userDto);
        return Optional.of(null);
    }
}
