package sample.cafekiosk.spring.api.controller.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import sample.cafekiosk.spring.ControllerTestSupport;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.product.ProductService;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ProductControllerTest extends ControllerTestSupport {

    @DisplayName("신규 상품을 등록한다.")
    @Test
    public void createProduct() throws Exception {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("아메리카노")
                .price(4000)
                .build();

        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/products/new")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(MockMvcResultHandlers.print()) // 결과에 대한 자세한 로그 출력
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("신규 상품을 등록할 때 상품 타입은 필수값이다.")
    @Test
    public void createProductWithoutType() throws Exception {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(null)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("아메리카노")
                .price(4000)
                .build();

        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/products/new")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(MockMvcResultHandlers.print()) // 결과에 대한 자세한 로그 출력
                .andExpect(MockMvcResultMatchers.status().isBadRequest()) // 응답 상태 검증
                // 응답 Body 에 담겨져 있는 값들 검증
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400")) // JSON 에서 Value 를 취하기 위해서 응답 DTO 에 getter 필수
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("상품 타입은 필수입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 상품을 등록할 때 상품 판매상태는 필수값이다.")
    @Test
    public void createProductWithoutSellingStatus() throws Exception {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(ProductType.HANDMADE)
                .sellingStatus(null)
                .name("아메리카노")
                .price(4000)
                .build();

        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/products/new")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(MockMvcResultHandlers.print()) // 결과에 대한 자세한 로그 출력
                .andExpect(MockMvcResultMatchers.status().isBadRequest()) // 응답 상태 검증
                // 응답 Body 에 담겨져 있는 값들 검증
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400")) // JSON 에서 Value 를 취하기 위해서 응답 DTO 에 getter 필수
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("상품 판매상태는 필수입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 상품을 등록할 때 상품 이름은 필수값이다.")
    @Test
    public void createProductWithoutName() throws Exception {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name(null)
                .price(4000)
                .build();

        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/products/new")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(MockMvcResultHandlers.print()) // 결과에 대한 자세한 로그 출력
                .andExpect(MockMvcResultMatchers.status().isBadRequest()) // 응답 상태 검증
                // 응답 Body 에 담겨져 있는 값들 검증
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400")) // JSON 에서 Value 를 취하기 위해서 응답 DTO 에 getter 필수
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("상품 이름은 필수입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 상품을 등록할 때 상품 가격은 양수이다.")
    @Test
    public void createProductWithZeroPrice() throws Exception {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("아메리카노")
                .price(0)
                .build();

        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/products/new")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(MockMvcResultHandlers.print()) // 결과에 대한 자세한 로그 출력
                .andExpect(MockMvcResultMatchers.status().isBadRequest()) // 응답 상태 검증
                // 응답 Body 에 담겨져 있는 값들 검증
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400")) // JSON 에서 Value 를 취하기 위해서 응답 DTO 에 getter 필수
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("가격은 양수이어야 합니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @DisplayName("판매 상품을 조회한다.")
    @Test
    public void getSellingProducts() throws Exception {
        // given
        List<ProductResponse> result = List.of();
        when(productService.getSellingProducts()).thenReturn(result);

        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/products/selling")
                                // .queryParam("name", "이름") -> 쿼리스트링이 필요한 경우 이처럼 작성.
                )
                .andDo(MockMvcResultHandlers.print()) // 결과에 대한 자세한 로그 출력
                .andExpect(MockMvcResultMatchers.status().isOk()) // 응답 상태 검증
                // 응답 Body 에 담겨져 있는 값들 검증
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200")) // JSON 에서 Value 를 취하기 위해서 응답 DTO 에 getter 필수
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray());
    }

}