package com.myfb.postservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostDTO {

    private Long id;
    private String title;
    private String body;
    private Long userId;
}
