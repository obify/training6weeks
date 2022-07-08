package com.myfb.postservice.controller;

import com.myfb.postservice.client.CommentDTO;
import com.myfb.postservice.dto.PostDTO;
import com.myfb.postservice.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
        ResponseEntity<PostDTO> responseEntity = null;
        postDTO = postService.createPost(postDTO);
        responseEntity = new ResponseEntity<>(postDTO, HttpStatus.CREATED);
        return responseEntity;
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDTO> getPostDetail(@PathVariable Long postId) {
        ResponseEntity<PostDTO> responseEntity = null;
        PostDTO postDTO = postService.getPostDetail(postId);
        responseEntity = new ResponseEntity<>(postDTO, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/posts/users/{userId}")
    public ResponseEntity<List<PostDTO>> getAllPostByUser(@PathVariable Long userId) {
        ResponseEntity<List<PostDTO>> responseEntity = null;
        List<PostDTO> allPosts = postService.getAllPostByUser(userId);
        responseEntity = new ResponseEntity<>(allPosts, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/posts/comments/{postId}")
    public void getAllCommentsForPostId(@PathVariable Long postId){
        CommentDTO[]  comments = postService.getAllCommentsForPostId(postId);
        System.out.println(comments.length);
    }
}
