package kakaopay.service;

import java.util.List;
import kakaopay.dto.MyInvestResponseDto;
import kakaopay.exception.ApiException;

public interface InvestService {
    void save(int product_id, int amount, int user_id) throws Exception;
    List<MyInvestResponseDto> myInvestList(int user_id) throws ApiException;
}
