package kakaopay.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @Column(name = "title")
    private String title;

    @Column(name = "total_investing_amount")
    private int totalInvestingAmount;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    @Builder
    public Product(int productId, String title, int totalInvestingAmount, LocalDateTime startedAt, LocalDateTime finishedAt) {
    	this.productId = productId;
    	this.title = title;
    	this.totalInvestingAmount = totalInvestingAmount;
    	this.startedAt = startedAt;
    	this.finishedAt = finishedAt;
    }
}
