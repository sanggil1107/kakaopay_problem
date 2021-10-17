package kakaopay.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Builder;
import lombok.Data;

@Data
public class ProductAllResponseDto {
    private int productId;
    private String title;
    private int totalInvestingAmount;
    private int currentAmount;
    private int investerCnt;
    private String investStatus;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startedAt;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime finishedAt;

    @Builder
    public ProductAllResponseDto(int productId, String title, int totalInvestingAmount, int currentAmount, int investerCnt, String investStatus, LocalDateTime startedAt, LocalDateTime finishedAt) {
        this.productId = productId;
        this.title = title;
        this.totalInvestingAmount = totalInvestingAmount;
        this.currentAmount = currentAmount;
        this.investerCnt = investerCnt;
        this.investStatus = investStatus;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
    }
}
