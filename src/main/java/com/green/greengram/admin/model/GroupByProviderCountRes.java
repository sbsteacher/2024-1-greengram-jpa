package com.green.greengram.admin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GroupByProviderCountRes {
    private String provider;
    private int count;
}
