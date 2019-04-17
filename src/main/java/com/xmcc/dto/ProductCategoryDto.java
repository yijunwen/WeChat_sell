package com.xmcc.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.xmcc.entity.ProductCategory;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

@Data
public class ProductCategoryDto implements Serializable {

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoDto> productInfoDtoList;

    public static ProductCategoryDto build(ProductCategory productCategory){
        ProductCategoryDto dto = new ProductCategoryDto();
        BeanUtils.copyProperties(productCategory,dto);
        return dto;
    }

}
