package com.farelcigar.api.domain.dto;

public class PasswordsDto {

    private String oldPassword;

    private String newPassword;

    public PasswordsDto() {
    }

    public PasswordsDto(
            String oldPassword,
            String newPassword) {

        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
