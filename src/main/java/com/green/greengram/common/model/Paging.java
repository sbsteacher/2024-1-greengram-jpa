package com.green.greengram.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

import java.beans.ConstructorProperties;

@Getter
@ToString

public class Paging {

    @Schema(example = "1", description = "선택한 page")
    private int page; //페이지 값

    @Schema(example = "5", description = "페이지 당 게시글 수")
    private int size; //페이지 당 레코드 수

    @ConstructorProperties({"page", "size"})
    public Paging(Integer page, Integer size) {
        this.page = page == null ? 1 : page;
        this.size = size == null ? 10 : size;
        this.startIdx = (this.page - 1) * this.size;
    }

    @JsonIgnore
    private int startIdx;

}
