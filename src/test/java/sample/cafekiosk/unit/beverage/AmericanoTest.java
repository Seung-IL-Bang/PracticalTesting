package sample.cafekiosk.unit.beverage;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AmericanoTest {

    @Test
    void getName() {

        Americano americano = new Americano();

//        assertEquals(americano.getName(), "아메리카노"); // JUnit

        // JUnit 보다 코드가 더 직관적이며, 메소드 체인닝을 지원하여 여러가지 검증 과정을 추가할 수 있다.
        assertThat(americano.getName()).isEqualTo("아메리카노"); // AssertJ
    }

    @Test
    void getPrice() {
        Americano americano = new Americano();

        assertThat(americano.getPrice()).isEqualTo(4000);
    }
}