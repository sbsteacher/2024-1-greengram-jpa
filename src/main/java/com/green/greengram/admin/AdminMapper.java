package com.green.greengram.admin;

import com.green.greengram.admin.model.GroupByProviderCountRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    List<GroupByProviderCountRes> selGroupByProviderCount();
}
