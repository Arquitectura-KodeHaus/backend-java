package com.kodehaus.demo.dto;

import java.util.List;

import com.kodehaus.demo.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private List<User> users;
}
