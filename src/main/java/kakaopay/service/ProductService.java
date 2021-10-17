package kakaopay.service;

import java.util.List;


import kakaopay.dto.ProductAllResponseDto;
import kakaopay.entity.Product;
import kakaopay.exception.ApiException;

public interface ProductService {
	void save(Product prodcut);
	List<ProductAllResponseDto> productList() throws ApiException;
}
