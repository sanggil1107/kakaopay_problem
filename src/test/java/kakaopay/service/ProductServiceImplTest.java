package kakaopay.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import kakaopay.entity.Product;
import kakaopay.exception.ApiException;
import kakaopay.repository.InvestRepository;
import kakaopay.repository.ProductRepository;

@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;
    
    @Autowired
    InvestRepository investRepository; 

	@Test
	@DisplayName("상품 조회")
	public void test() {
		Product product1 = Product
				.builder()
				.productId(1)
				.title("개인신용 포트폴리오")
				.totalInvestingAmount(1000000)
				.startedAt(LocalDateTime.parse("2021-10-01T00:00:00.000"))
				.finishedAt(LocalDateTime.parse("2021-11-01T00:00:00.000"))
				.build();
		System.out.println(product1);
		productRepository.save(product1);
		
		Product product2 = Product
				.builder()
				.productId(2)
				.title("부동산 포트폴리오")
				.totalInvestingAmount(5000000)
				.startedAt(LocalDateTime.parse("2021-01-01T00:00:00.000"))
				.finishedAt(LocalDateTime.parse("2021-12-31T00:00:00.000"))
				.build();
		productRepository.save(product2);
		
		
		List<Product> products = productRepository.findAll();
		
		for (Product product : products) {
			if (product.getProductId() == 1) {
				assertEquals(product, product1);
			}
			else {
				assertEquals(product, product2);
			}
		}
	}
	
	@Test
	@DisplayName("현재 투자 가능한 상품이 한 개도 없는 경우")
	public void test01() throws ApiException {
		Product product1 = Product
				.builder()
				.productId(1)
				.title("개인신용 포트폴리오")
				.totalInvestingAmount(1000000)
				.startedAt(LocalDateTime.parse("2020-10-01T00:00:00.000"))
				.finishedAt(LocalDateTime.parse("2020-11-01T00:00:00.000"))
				.build();
		System.out.println(product1);
		productRepository.save(product1);
		
		Product product2 = Product
				.builder()
				.productId(2)
				.title("부동산 포트폴리오")
				.totalInvestingAmount(5000000)
				.startedAt(LocalDateTime.parse("2020-01-01T00:00:00.000"))
				.finishedAt(LocalDateTime.parse("2020-12-31T00:00:00.000"))
				.build();
		productRepository.save(product2);
		
		
		ApiException apiexception = assertThrows(ApiException.class, () -> {
			productService.productList();
		});
		assertSame("모집 중인 상품이 없습니다.", apiexception.getError().getMsg());
	}
}
