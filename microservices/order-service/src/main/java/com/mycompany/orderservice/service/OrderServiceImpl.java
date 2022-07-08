package com.mycompany.orderservice.service;

import com.mycompany.orderservice.client.feign.BookFeignClient;
import com.mycompany.orderservice.dto.BookDTO;
import com.mycompany.orderservice.dto.ErrorDTO;
import com.mycompany.orderservice.dto.OrderDTO;
import com.mycompany.orderservice.entity.OrderEntity;
import com.mycompany.orderservice.exception.BusinessException;
import com.mycompany.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private BookFeignClient bookFeignClient;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${book.service.url:}")
    private String bsBaseUrl;

    @Override
    public OrderDTO placeOrder(OrderDTO orderDTO) {
        log.info("Entering method placeOrder in OrderServiceImpl");
        OrderEntity orderEntity = new OrderEntity();
        StringBuilder sb = new StringBuilder();
        List<ErrorDTO> errorDTOS = null;
        for(BookDTO bdto: orderDTO.getBooks()){
            //Call BOOK SERVICE and get the Available Quantity of the book
            //If Book Quantity is in stock place order else throw exception
            ResponseEntity<BookDTO> respEntity = this.restTemplate.getForEntity(this.bsBaseUrl+"/books/{bookId}", BookDTO.class, bdto.getBookId());
            if(respEntity.getStatusCodeValue() == 200){
                BookDTO bookDTO = respEntity.getBody();
                if(bookDTO.getAvailableQty() < 1){
                    ErrorDTO errorDTO = new ErrorDTO();
                    errorDTOS = new ArrayList<>();
                    errorDTO.setCode("QTY_NOT_SUFFICIENT");
                    errorDTO.setMsg("Book with Id "+bdto.getBookId()+" does not have sufficient quantity");
                    errorDTOS.add(errorDTO);
                }else{
                    sb.append(bdto.getBookId().toString());
                    sb.append(",");
                }
            }
        }

        //if all books inside the order is out of qty only then throw exception else place the order
        if(errorDTOS !=null && errorDTOS.size() != orderDTO.getBooks().size()){
            throw new BusinessException(errorDTOS);
        }else{
            sb.deleteCharAt(sb.toString().length() - 1);
            orderEntity.setUserId(orderDTO.getUserId());
            orderEntity.setBookIds(sb.toString());
            orderEntity = orderRepository.save(orderEntity);
            //MAke API call to Book Service Update Available Qty functionality
            BeanUtils.copyProperties(orderEntity, orderDTO);
        }
        log.info("Exiting method placeOrder in OrderServiceImpl");
        return orderDTO;
    }

    @Override
    public List<OrderDTO> getAllOrders(Long userId) {
        log.info("Entering method placeOrder in OrderServiceImpl");
        List<OrderEntity> orderEntities = orderRepository.findAllByUserId(userId);

        ResponseEntity<BookDTO> re = null;
        List<BookDTO> dtoList = null;
        List<OrderDTO> orderDtoList = new ArrayList<>();
        BookDTO bookDTO = null;
        OrderDTO orderDTO = null;

        for (OrderEntity oe: orderEntities){
            String[] bookIds = oe.getBookIds().split(",");
            orderDTO = new OrderDTO();
            dtoList = new ArrayList<>();
            for(String bookId: bookIds){
                re = bookFeignClient.getBook(Long.parseLong(bookId));
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity httpEntity = new HttpEntity(null, headers);
                ParameterizedTypeReference<BookDTO> typeRef = new ParameterizedTypeReference<BookDTO>() {};
                re = this.restTemplate.exchange(this.bsBaseUrl+"/books/{bookId}", HttpMethod.GET, httpEntity, typeRef, bookId);
                bookDTO = re.getBody();
                dtoList.add(bookDTO);
            }
            orderDTO.setId(oe.getId());
            orderDTO.setBooks(dtoList);
            orderDTO.setUserId(userId);
            orderDtoList.add(orderDTO);
        }
        log.info("Exiting method placeOrder in OrderServiceImpl");
        return orderDtoList;
    }
}
