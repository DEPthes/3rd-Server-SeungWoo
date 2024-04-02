package com.study.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Product {

    private final String id;
    private final String name;
    private final int price;
}
