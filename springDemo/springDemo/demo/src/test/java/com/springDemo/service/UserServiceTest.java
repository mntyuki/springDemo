package com.springDemo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.springDemo.dto.UserDto;
import com.springDemo.entity.User;
import com.springDemo.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	UserRepository repository;

	@Mock
	PasswordEncoder encoder;

	@InjectMocks
	UserService userService;
	UserDto userDto;

	@Test
	void 新規ユーザーを登録する() {

		UserDto userDto = new UserDto();

		userDto.setUsername("user");
		userDto.setPassword("123");

		when(repository.findByUsername("user"))
				.thenReturn(Optional.empty());
		when(encoder.encode("123"))
				.thenReturn("encodePassword");

		userService.register(userDto);

		verify(repository).save(any(User.class));
	}

	@Test
	void ユーザーが既に存在してるとき() {

		User user = new User();
		UserDto userDto = new UserDto();

		userDto.setUsername("user");
		userDto.setPassword("123");

		when(repository.findByUsername("user"))
				.thenReturn(Optional.of(user));

		assertThrows(IllegalArgumentException.class, () -> {
			userService.register(userDto);
		});
		
		verify(repository, never()).save(any());
	}
}
