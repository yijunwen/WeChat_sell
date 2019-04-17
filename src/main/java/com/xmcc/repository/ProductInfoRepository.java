package com.xmcc.repository;

import com.xmcc.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductInfoRepository extends JpaRepository<ProductInfo,Integer> {
    List<ProductInfo> findByProductStatusAndCategoryTypeIn(Integer status,List<Integer> typeList);
}
