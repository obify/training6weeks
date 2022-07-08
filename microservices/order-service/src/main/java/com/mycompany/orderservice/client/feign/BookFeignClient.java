package com.mycompany.orderservice.client.feign;

import com.mycompany.orderservice.client.config.BookFeignDecoder;
import com.mycompany.orderservice.dto.BookDTO;
import feign.Headers;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@Headers("Content-Type: application/json")
@FeignClient(name = "bookFeignClient", url = "${book.service.url:}", configuration = BookFeignDecoder.class)
public interface BookFeignClient {

    @GetMapping("/books/{bookId}")
    //@RequestLine("GET /books/{bookId}")
    public ResponseEntity<BookDTO> getBook(@PathVariable Long bookId);

    @PostMapping("/books")
    public ResponseEntity<BookDTO> add(@RequestBody BookDTO bookDTO);
}
