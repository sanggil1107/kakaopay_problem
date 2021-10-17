package kakaopay.common;

import lombok.Getter;

public enum ApiErr {
    PARAM_MISMATCH(100, "헤더값 또는 파라미터값이 유효하지 않습니다.")
    ,PARAM_EXCEPTION(101, "파라미터명이 유효하지 않습니다.")
    ,SOLD_OUT(200, "SOLD-OUT")
    ,FINISH_INVEST(300, "모집 중인 상품이 없습니다.")
    ,PRODUCT_NOT_INVEST(301, "모집 중인 상품이 아닙니다.")
    ,NOT_PRODUCT(302, "존재하지 않는 상품입니다.") 
    ,OVER_AMOUNT_TOTAL(400, "투자 금액이 총 모집금액보다 큽니다.")
    ,OVER_AMOUNT_POSSIBLE(401, "투자 금액이 투자 가능 금액보다 큽니다.")
    ,NOT_USER(500, "투자한 이력이 없습니다.");
    
    @Getter
    private final int code;

    @Getter
    private final String msg;

    ApiErr(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
