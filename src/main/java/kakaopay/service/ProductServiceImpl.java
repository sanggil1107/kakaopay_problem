package kakaopay.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kakaopay.common.ApiErr;
import kakaopay.dto.ProductAllResponseDto;
import kakaopay.entity.Product;
import kakaopay.exception.ApiException;
import kakaopay.repository.InvestRepository;
import kakaopay.repository.ProductRepository;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    InvestRepository investRepository; 

    @Override
    public void save(Product product) {
    	productRepository.save(product);
    }

    @Override
    public List<ProductAllResponseDto> productList() throws ApiException {
    	int current_mount = 0;
    	int cnt = 0;
        LocalDateTime now = LocalDateTime.now();
        List<Product> products = productRepository.findAll();
        List<ProductAllResponseDto> ProductResponses = new ArrayList<>();

        for (Product product : products) {
            LocalDateTime startat = product.getStartedAt();
            LocalDateTime finishat = product.getFinishedAt();
          
            if (now.isAfter(startat) && now.isBefore(finishat)) {
                List<Object[]> findProductInfos = investRepository.findInvestInfo(product.getProductId());
                
                for (Object[] findProductInfo : findProductInfos) {
                    if (findProductInfo[0] == null) {
                        findProductInfo[0] = 0;
                    }
                    current_mount = Integer.parseInt(findProductInfo[0].toString());
                    cnt = Integer.parseInt(findProductInfo[1].toString());     	
                }
            
                String investStatus;
                if (current_mount == product.getTotalInvestingAmount()) {
                    investStatus = "모집완료";
                }
                else {
                    investStatus = "모집중";
                }
      
                ProductAllResponseDto ProductResponse = ProductAllResponseDto
                		.builder()
                		.productId(product.getProductId())
                		.title(product.getTitle())
                		.totalInvestingAmount(product.getTotalInvestingAmount())
                		.currentAmount(current_mount)
                		.investerCnt(cnt)
                		.investStatus(investStatus)
                		.startedAt(startat)
                		.finishedAt(finishat)
                		.build();
                ProductResponses.add(ProductResponse);
            }
        }
        if (ProductResponses.isEmpty()) {
            throw new ApiException(ApiErr.FINISH_INVEST);
        }
        return ProductResponses;
    }


}
