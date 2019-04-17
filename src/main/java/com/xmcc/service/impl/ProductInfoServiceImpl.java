package com.xmcc.service.impl;

import com.xmcc.common.ProductEnums;
import com.xmcc.common.ResultEnums;
import com.xmcc.common.ResultResponse;
import com.xmcc.dto.ProductCategoryDto;
import com.xmcc.dto.ProductInfoDto;
import com.xmcc.entity.ProductCategory;
import com.xmcc.entity.ProductInfo;
import com.xmcc.repository.ProductCategoryRepository;
import com.xmcc.repository.ProductInfoRepository;
import com.xmcc.service.ProductInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.List;
import java.util.Optional;
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

    /**
     * 1.根据购物车(订单项) 传来的商品id 查询对应的商品 取得价格等相关信息 如果没查到 订单生成失败
     * 2.比较库存 ，库存不足 订单生成失败
     * 3.生成订单项OrderDetail信息
     * 4.减少商品库存
     * 5.算出总价格 ，组装订单信息 插入数据库得到订单号
     * 6.批量插入订单项
     * <p>
     * 注意:1.生成订单就会减少库存 加入购物车不会  所有的网站基本都是这么设计的
     * 2.商品价格以生成订单时候为准，后面商品价格改变不影响已经生成的订单
     */

    @Override
    public ResultResponse<ProductInfo> queryById(String productId) {
        //判断参数数据是否存在
        if (StringUtils.isBlank(productId)) {
            return ResultResponse.fail(ResultEnums.PARAM_ERROR.getCode() ,ResultEnums.PARAM_ERROR.getMsg() + ":" + productId);
        }
        Optional<ProductInfo> byId = productInfoRepository.findById(productId);
        //判断数据是否存在
        if (!byId.isPresent()) {
            return ResultResponse.fail(ResultEnums.NOT_EXITS.getCode(),productId + ":" + ResultEnums.NOT_EXITS.getMsg());
        }
        ProductInfo productInfo = byId.get();
        //判断商品是否下架
        if (productInfo.getProductStatus() == ProductEnums.PRODUCT_DOWN.getCode()) {
            return ResultResponse.fail(ResultEnums.PRODUCT_DOWN.getCode(),ResultEnums.PRODUCT_DOWN.getMsg());
        }
        return ResultResponse.success(productInfo);
    }

    @Override
    public void updateProduct(ProductInfo productInfo) {
        productInfoRepository.save(productInfo);
    }
}
