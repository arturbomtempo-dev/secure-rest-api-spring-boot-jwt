package dev.arturbomtempo.secure_api_jwt.dto;

import java.util.List;

import dev.arturbomtempo.secure_api_jwt.model.Role;

public record RecoveryUserDto(Long id, String email, List<Role> roles) {
}
