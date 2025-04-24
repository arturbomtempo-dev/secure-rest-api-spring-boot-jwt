package dev.arturbomtempo.secure_api_jwt.dto;

import dev.arturbomtempo.secure_api_jwt.model.RoleName;

public record CreateUserDto(String email, String password, RoleName role) {
}
