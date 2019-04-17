package com.xmcc.service.impl;

import com.xmcc.common.ResultEnums;
import com.xmcc.common.ResultResponse;
import com.xmcc.dto.ProductCategoryDto;
import com.xmcc.dto.ProductInfoDto;
import com.xmcc.entity.ProductCategory;
import com.xmcc.entity.ProductInfo;
import com.xmcc.repository.ProductCategoryRepository;
import com.xmcc.repository.ProductInfoRepository;
import com.xmcc.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository productInfoRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public ResultResponse list() {
        List<ProductCategory> all = productCategoryRepository.findAll();
        List<ProductCategoryDto> productCategoryDtoList
                = all.stream().map(productCategory -> ProductCategoryDto.build(productCategory)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(all)) {
            return ResultResponse.fail();
        }
        //获取类目编号集合
        List<Integer> typeList
                = productCategoryDtoList.stream().map(productInfoDto -> productInfoDto.getCategoryType()).collect(Collectors.toList());
        //根据typeList 查询商品列表
        List<ProductInfo> infoList
                = productInfoRepository.findByProductStatusAndCategoryTypeIn(ResultEnums.PRODUCT_UP.getCode(), typeList);

        //对productCategoryDtoList标集合进行遍历 取出每个商品的类目编号 设置到对应的目录中
        //将productInfo 设置到 foods中
        //过滤：不同的type  进行不同的封装
        //将productInfo 转成Dto
         productCategoryDtoList = productCategoryDtoList.parallelStream().map(productCategoryDto -> {
            productCategoryDto.setProductInfoDtoList(infoList.stream()
                    .filter(productInfo -> productInfo.getCategoryType() == productCategoryDto.getCategoryType())
                    .map(productInfo -> ProductInfoDto.build(productInfo)).collect(Collectors.toList())
            );
            return productCategoryDto;
        }).collect(Collectors.toList());
       /* productCategoryDtoList.parallelStream().forEach(productCategoryDto -> {
             productCategoryDto.setProductInfoDtoList(infoList.stream()
             .filter(productInfo -> productInfo.getCategoryType() == productCategoryDto.getCategoryType())
                     .map(productInfo -> ProductInfoDto.build(productInfo)).collect(Collectors.toList())
             ); }
        );*/
        return ResultResponse.success(productCategoryDtoList);
    }
}
