package com.veljkoilic.instagramclone.user.dto;

import java.util.List;

import com.veljkoilic.instagramclone.post.dto.PostDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String username;
    private String email;
}
