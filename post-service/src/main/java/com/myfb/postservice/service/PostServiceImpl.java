package com.myfb.postservice.service;

import com.myfb.postservice.client.CommentDTO;
import com.myfb.postservice.dto.PostDTO;
import com.myfb.postservice.entity.PostEntity;
import com.myfb.postservice.repository.PostRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${postapi.base.url:}")
    private String postBaseUrl;

    @Override
    public PostDTO createPost(PostDTO postDTO) {

        PostEntity postEntity = new PostEntity();
        BeanUtils.copyProperties(postDTO, postEntity);
        postEntity = postRepository.save(postEntity);
        BeanUtils.copyProperties(postEntity, postDTO);
        return postDTO;
    }

    @Override
    public PostDTO getPostDetail(Long postId) {
        Optional<PostEntity> optEntity = postRepository.findById(postId);
        PostDTO postDTO = null;
        if(optEntity.isPresent()){
            postDTO = new PostDTO();
            BeanUtils.copyProperties(optEntity.get(), postDTO);
        }
        optEntity.orElseThrow(()->new RuntimeException("No post found with Id "+postId));
        return postDTO;
    }

    @Override
    public List<PostDTO> getAllPostByUser(Long userId) {

        List<PostEntity> posts = postRepository.findAllByUserId(userId);
        List<PostDTO> postDtos = null;
        PostDTO postDTO = null;

        if(posts != null && !posts.isEmpty()){
            postDtos = new ArrayList<>();
            for(PostEntity pe : posts){
                postDTO = new PostDTO();
                BeanUtils.copyProperties(pe, postDTO);
                postDtos.add(postDTO);
            }
        }
        return postDtos;
    }

    @Override
    public CommentDTO[] getAllCommentsForPostId(Long postId){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CommentDTO[]> httpEntity = new HttpEntity<>(headers);
       //CommentDTO[]  comments = restTemplate.getForObject(this.postBaseUrl+"/posts/{postId}/comments", CommentDTO[].class, postId);
        ResponseEntity<CommentDTO[]> comments = restTemplate.getForEntity(this.postBaseUrl+"/posts/{postId}/comments", CommentDTO[].class, postId);
        System.out.println(comments.getBody().length);
        System.out.println(comments);

       return comments.getBody();
    }
}
