package com.xmcc.service;

import com.xmcc.common.ResultResponse;
import com.xmcc.entity.ProductInfo;
import com.xmcc.repository.ProductInfoRepository;

public interface ProductInfoService {

    ResultResponse list();

    ResultResponse<ProductInfo> queryById(String productId);

    void updateProduct(ProductInfo productInfo);
}
