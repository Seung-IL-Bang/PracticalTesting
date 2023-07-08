package sample.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductTypeTest {

    // 모든 주제 한 꺼번에 테스트
    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockTypeEx() {
        //given
        ProductType[] productTypes = ProductType.values();

        for (ProductType productType : productTypes) {
            if (productType == ProductType.HANDMADE) {
                // when
                boolean result = ProductType.containsStockType(productType);

                // then
                assertThat(result).isFalse();
            }

            if (productType == ProductType.BAKERY || productType == ProductType.BOTTLE) {
                // when
                boolean result = ProductType.containsStockType(productType);

                // then
                assertThat(result).isTrue();
            }
        }

    }

    // 한 주제당 한 개씩 테스트
    @DisplayName("상품 타입이 재고 관련 타입이 아닌지를 체크한다.")
    @Test
    void containsStockType() {
        //given
        ProductType givenType = ProductType.HANDMADE;

        // when
        boolean result = ProductType.containsStockType(givenType);

        // then
        assertThat(result).isFalse();

    }

    // 한 주제당 한 개씩 테스트
    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockType2() {
        //given
        ProductType givenType = ProductType.BAKERY;

        // when
        boolean result = ProductType.containsStockType(givenType);

        // then
        assertThat(result).isTrue();

    }
}