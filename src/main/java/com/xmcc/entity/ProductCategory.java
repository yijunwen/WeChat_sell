package com.xmcc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data//相当于set、get、toString方法
@Entity//表示该类为实体类
@DynamicUpdate//设置为true,表示update对象的时候,生成动态的update语句,如果这个字段的值是null就不会被加入到update语句中
@AllArgsConstructor
@NoArgsConstructor
@Table(name="product_category") //表名
public class ProductCategory implements Serializable {


    @Id//主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//表示自增IDENTITY：mysql SEQUENCE:oracle
    private Integer categoryId;

    /** 类目名字. */
    private String categoryName;

    /** 类目编号. */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;
}
