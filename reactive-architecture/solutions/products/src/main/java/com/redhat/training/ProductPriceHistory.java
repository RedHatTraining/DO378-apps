package com.redhat.training;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductPriceHistory {

    @JsonProperty("product_id")
    public Long productId;

    public List<Price> prices;

    public ProductPriceHistory() {
    }

    public ProductPriceHistory( Long productId, List<Price> prices ) {
        this.productId = productId;
        this.prices = prices;
    }
}
