package kakaopay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import kakaopay.dto.ResponseDto;
import kakaopay.exception.ApiException;
import kakaopay.service.InvestService;
import kakaopay.service.ProductService;

@RestController
public class ApiController {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private InvestService investService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseDto productList() throws ApiException {
        return new ResponseDto(productService.productList());
    }

    @RequestMapping(value = "/invest", method = RequestMethod.POST)
    public void productInvest(@RequestParam("productId") int productId, @RequestParam("amount") int amount, @RequestHeader("X-USER-ID") int userId) throws Exception {
    	investService.save(productId, amount, userId);
    }

    @RequestMapping(value = "/myinvest", method = RequestMethod.GET)
    public ResponseDto myInvestList(@RequestHeader("X-USER-ID") int userId) throws ApiException {
        return new ResponseDto(investService.myInvestList(userId));
    }
}
