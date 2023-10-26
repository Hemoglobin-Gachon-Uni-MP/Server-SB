package com.mp.PLine.src.login;

import java.util.Optional;

public interface UserService {
    Optional<UserDto> login(UserDto userVo);
}