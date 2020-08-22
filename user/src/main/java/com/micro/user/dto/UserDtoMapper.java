package com.micro.user.dto;

import com.micro.user.domain.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    UserDto toUserDto(User user);
}
