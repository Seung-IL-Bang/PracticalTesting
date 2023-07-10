package sample.cafekiosk.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.product.request.ProductCreateServiceRequest;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;

import java.util.List;
import java.util.stream.Collectors;


/**
 * readOnly = true : 읽기 전용
 * CRUD 에서 CUD 동작 X / only Read
 * JPA: CUD 스냅샷 저장, 변경감지(dirty check) X (성능 향상)
 *
 * CQRS - Command / Query 분리 : 보통의 서비스의 경우 Read 가 압도적으로 많다. -> 커맨드와 읽기를 분리하여 서로 영향이 없게끔 한다.
 */
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductNumberFactory productNumberFactory;


    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

        return products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    // 동시성 이슈 - 값이 순차적으로 증가하는 사항
    // 낙관적 락, 유니크 인덱스 + 재시도, UUID
    @Transactional
    public ProductResponse createProduct(ProductCreateServiceRequest request) {

        // productNumber
        // 001 002 003
        // DB 에서 마지막 저장된 Product 의 상품 번호를 읽어와서 +1

        String nextProductNumber = productNumberFactory.createNextProductNumber();

        Product product = request.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(product);

        return ProductResponse.of(product);
    }

}
