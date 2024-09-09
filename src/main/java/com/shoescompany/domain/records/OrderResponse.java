package com.shoescompany.domain.records;

import java.util.Set;

public record OrderResponse(
        Long id,
        String customerName,
        Integer quantity,
        Set<Long> productIds
) {}
