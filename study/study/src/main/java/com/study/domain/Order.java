package com.study.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Data
public class Order {

    private final Long id;
    private final List<Product> products;
}
