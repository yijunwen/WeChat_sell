package com.xmcc.controller;

import com.xmcc.common.ResultResponse;
import com.xmcc.service.ProductInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/buyer/product")
@Api(description = "商品接口信息")
public class ProductInfoController {

    @Autowired
    private ProductInfoService productInfoService;

    @RequestMapping("/list")
    @ApiOperation("查询商品列表")//使用swagger2的注解对方法接口描述
    public ResultResponse list(){
        return productInfoService.list();
    }
}
