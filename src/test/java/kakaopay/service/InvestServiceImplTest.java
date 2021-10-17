package kakaopay.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import kakaopay.entity.Invest;
import kakaopay.entity.Product;
import kakaopay.exception.ApiException;
import kakaopay.exception.SoldoutException;
import kakaopay.repository.InvestRepository;
import kakaopay.repository.ProductRepository;


@SpringBootTest
public class InvestServiceImplTest {

    @Autowired
    ProductRepository productRepository;
    
    @Autowired
    InvestService investService;
    @Autowired
    ProductService productService;
    
    @Autowired
    InvestRepository investRepository; 
    
    
    
	@Test
	@DisplayName("현재 투자 가능한 상품이 한 개도 없는 경우")
	public void test() throws ApiException {
		
		ApiException apiexception = assertThrows(ApiException.class, () -> {
			investService.save(3, 1000, 1);
		});
		assertSame("존재하지 않는 상품입니다.", apiexception.getError().getMsg());
	}
	
	@Test
	@DisplayName("모집 중이 아닌 상품을 투자할 경우")
	public void test01() throws ApiException {
		Product product1 = Product
				.builder()
				.productId(1)
				.title("개인신용 포트폴리오")
				.totalInvestingAmount(1000000)
				.startedAt(LocalDateTime.parse("2020-10-01T00:00:00.000"))
				.finishedAt(LocalDateTime.parse("2020-11-01T00:00:00.000"))
				.build();
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
		
		Invest invest = new Invest();
		invest.setInvestId(1);
		invest.setProductId(product1);
		invest.setAmount(5000);
		invest.setUserId(1);
		invest.setInvestedAt(LocalDateTime.now());
		System.out.println(invest);
		ApiException apiexception = assertThrows(ApiException.class, () -> {      
	        investService.save(1, 5000, 1);
		});
		assertSame("모집 중인 상품이 아닙니다.", apiexception.getError().getMsg());
	}

	@Test
	@DisplayName("투자 금액이 총 모집금액보다 큰 경우")
	public void test02() throws ApiException {
		Product product1 = Product
				.builder()
				.productId(1)
				.title("개인신용 포트폴리오")
				.totalInvestingAmount(1000000)
				.startedAt(LocalDateTime.parse("2021-10-01T00:00:00.000"))
				.finishedAt(LocalDateTime.parse("2021-11-01T00:00:00.000"))
				.build();
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
		
		
	    Invest invest = new Invest();
        invest.setInvestId(1);
        invest.setProductId(product1);
        invest.setAmount(10000000);
        invest.setUserId(1);
        invest.setInvestedAt(LocalDateTime.now());
        
		ApiException apiexception = assertThrows(ApiException.class, () -> {
			investService.save(1, 1000000000, 1);
		});
		assertSame("투자 금액이 총 모집금액보다 큽니다.", apiexception.getError().getMsg());
	}
	
	@Test
	@DisplayName("투자 금액이 투자 가능한 금액보다 큰 경우")
	public void test03() throws ApiException {
		Product product1 = Product
				.builder()
				.productId(1)
				.title("개인신용 포트폴리오")
				.totalInvestingAmount(1000000)
				.startedAt(LocalDateTime.parse("2021-10-01T00:00:00.000"))
				.finishedAt(LocalDateTime.parse("2021-11-01T00:00:00.000"))
				.build();
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
		
		Invest invest = new Invest();
		invest.setInvestId(1);
		invest.setProductId(product1);
		invest.setAmount(5000);
		invest.setUserId(1);
		invest.setInvestedAt(LocalDateTime.now());
		
		Invest invest1 = new Invest();
		invest1.setInvestId(1);
		invest1.setProductId(product1);
		invest1.setAmount(6000);
		invest1.setUserId(1);
		invest1.setInvestedAt(LocalDateTime.now());
		
		ApiException apiexception = assertThrows(ApiException.class, () -> {
			
			investService.save(1, 500000, 1);
			investService.save(1, 600000, 1);
		});
		assertSame("투자 금액이 투자 가능 금액보다 큽니다.", apiexception.getError().getMsg());
	}
	
	@Test
	@DisplayName("투자한 이력이 없는 경우")
	public void test04() throws ApiException {
		Product product1 = Product
				.builder()
				.productId(1)
				.title("개인신용 포트폴리오")
				.totalInvestingAmount(1000000)
				.startedAt(LocalDateTime.parse("2021-10-01T00:00:00.000"))
				.finishedAt(LocalDateTime.parse("2021-11-01T00:00:00.000"))
				.build();
		
		productRepository.save(product1);


		Invest invest = new Invest();
		invest.setInvestId(1);
		invest.setProductId(product1);
		invest.setAmount(5000);
		invest.setUserId(1);
		invest.setInvestedAt(LocalDateTime.now());
		
		ApiException apiexception = assertThrows(ApiException.class, () -> {
			investService.save(1, 5000, 1);
			investService.myInvestList(2);
		});
		assertSame("투자한 이력이 없습니다.", apiexception.getError().getMsg());
	}
	
	@Test
	@DisplayName("투자하기")
	public void test06() throws Exception {
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
		
		Invest invest = new Invest();
		invest.setInvestId(1);
		invest.setProductId(product1);
		invest.setAmount(5000);
		invest.setUserId(1);
		invest.setInvestedAt(LocalDateTime.now());
		
		Invest invest1 = new Invest();
		invest1.setInvestId(2);
		invest1.setProductId(product2);
		invest1.setAmount(5000);
		invest1.setUserId(2);
		invest1.setInvestedAt(LocalDateTime.now());
		
		investService.save(invest.getProductId().getProductId(), invest.getAmount(), invest.getUserId());
		investService.save(invest1.getProductId().getProductId(), invest1.getAmount(), invest1.getUserId());
		
		assertEquals(1, invest.getInvestId());
		assertEquals(2, invest1.getUserId());
	}
	
	@Test
	@DisplayName("나의 이력조회")
	public void test07() throws Exception {
		Product product1 = Product
				.builder()
				.productId(1)
				.title("개인신용 포트폴리오")
				.totalInvestingAmount(1000000)
				.startedAt(LocalDateTime.parse("2021-10-01T00:00:00.000"))
				.finishedAt(LocalDateTime.parse("2021-11-01T00:00:00.000"))
				.build();
		
		productRepository.save(product1);


		Invest invest = new Invest();
		invest.setInvestId(1);
		invest.setProductId(product1);
		invest.setAmount(5000);
		invest.setUserId(1);
		invest.setInvestedAt(LocalDateTime.now());
		
		investService.save(invest.getProductId().getProductId(), invest.getAmount(), invest.getUserId());
		investService.myInvestList(1);
		
	}
}
