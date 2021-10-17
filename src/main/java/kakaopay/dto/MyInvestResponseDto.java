package kakaopay.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Builder;
import lombok.Data;

@Data
public class MyInvestResponseDto {
    
    private int productId;
    private String title;
    private int totalInvestingAmount;
    private int amount;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime investat;

    @Builder
    public MyInvestResponseDto(int productId, String title, int totalInvestingAmount, int amount, LocalDateTime investat) {
        this.productId = productId;
        this.title = title;
        this.totalInvestingAmount = totalInvestingAmount;
        this.amount = amount;
        this.investat = investat;
    }
}
