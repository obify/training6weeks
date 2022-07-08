package com.myfb.postservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "POST_TABLE")
@Setter
@Getter
@NoArgsConstructor
public class PostEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String body;
    private Long userId;

}
