package sample.cafekiosk.spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // Controller Layer 에서 Mock 테스트 환경 구성 시, Error creating bean with name 'jpaAuditingHandler' 에러 방지를 위해 따로 설정 파일 분리
public class JpaAuditingConfig {
}
