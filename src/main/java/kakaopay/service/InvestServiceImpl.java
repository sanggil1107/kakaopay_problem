package kakaopay.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kakaopay.common.ApiErr;
import kakaopay.dto.MyInvestResponseDto;
import kakaopay.entity.Invest;
import kakaopay.entity.Product;
import kakaopay.exception.ApiException;
import kakaopay.exception.SoldoutException;
import kakaopay.repository.InvestRepository;
import kakaopay.repository.ProductRepository;

@Service
@Transactional
public class InvestServiceImpl implements InvestService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    InvestRepository investRepository; 
    
    @Override
    public void save(int product_id, int amount, int user_id) throws Exception {

        Product product = productRepository.findById(product_id).orElseThrow(() -> new ApiException(ApiErr.NOT_PRODUCT));
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startat = product.getStartedAt();
        LocalDateTime finishat = product.getFinishedAt();
          
        if (!(now.isAfter(startat) && now.isBefore(finishat))) {
            throw new ApiException(ApiErr.PRODUCT_NOT_INVEST);
        } 
        
        int total_amount = product.getTotalInvestingAmount();
        if (total_amount < amount) {
            throw new ApiException(ApiErr.OVER_AMOUNT_TOTAL);
        }

        int current_amount = 0;
        List<Object[]> findProductInfos = investRepository.findInvestInfo(product_id);
            
        for (Object[] findProductInfo : findProductInfos) {
            if (findProductInfo[0] == null) {
                findProductInfo[0] = 0;
            }
            current_amount = Integer.parseInt(findProductInfo[0].toString());
        }


        if (total_amount == current_amount) {
            throw new SoldoutException(ApiErr.SOLD_OUT);
        }
        else if (total_amount < current_amount + amount) {
            throw new ApiException(ApiErr.OVER_AMOUNT_POSSIBLE);
        }
        else {
            Invest invest = new Invest();
            invest.setProductId(product);
            invest.setAmount(amount);
            invest.setUserId(user_id);    
            investRepository.save(invest);
        }

    }

    @Override
    public List<MyInvestResponseDto> myInvestList(int user_id) throws ApiException {
        List<Invest> invests = investRepository.findByUserId(user_id);
        if (invests.isEmpty()) {
            throw new ApiException(ApiErr.NOT_USER);
        }
        List<MyInvestResponseDto> MyInvestResponses = new ArrayList<>();
        
        for (Invest invest : invests) {
             MyInvestResponseDto MyInvestResponse = MyInvestResponseDto
            		 .builder()
            		 .productId(invest.getProductId().getProductId())
            		 .title(invest.getProductId().getTitle())
            		 .totalInvestingAmount(invest.getProductId().getTotalInvestingAmount())
            		 .amount(invest.getAmount())
            		 .investat(invest.getInvestedAt())
            		 .build();
             MyInvestResponses.add(MyInvestResponse);
        }
    
        return MyInvestResponses;
    }
}
