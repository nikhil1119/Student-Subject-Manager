package com.nikhilgadiwadd.student_subject_management.jwt;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

@Getter
@Setter
@Data
public class RegisterRequest {

    private String username;
    private String password;
    private String role;
}
