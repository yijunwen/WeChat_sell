package com.xmcc.wechat_sell;

import com.xmcc.entity.ProductCategory;
import com.xmcc.repository.ProductCategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatSellApplicationTests {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Test
    public void contextLoads() {
        List<ProductCategory> all = productCategoryRepository.findAll();
        all.stream().forEach(System.out::println);

    }

}
