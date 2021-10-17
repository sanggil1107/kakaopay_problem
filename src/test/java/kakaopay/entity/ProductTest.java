package kakaopay.entity;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Test;

public class ProductTest {

	@Test
	public void test() {
		Product product = Product
				.builder()
				.productId(1)
				.title("개인신용 포트폴리오")
				.totalInvestingAmount(1000000)
				.startedAt(LocalDateTime.parse("2021-10-01T00:00:00.000"))
				.finishedAt(LocalDateTime.parse("2021-11-01T00:00:00.000"))
				.build();
		
		String title = product.getTitle();
		System.out.println(product.getFinishedAt());
		assertEquals("개인신용 포트폴리오", title);
	}			

}
