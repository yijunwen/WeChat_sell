package com.xmcc.wechat_sell;

import com.xmcc.entity.Person;


import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {
        ArrayList<Person> a = new ArrayList<>();
        a.add(new Person());
        a.stream().forEach(person -> person.setAge(5));
        System.out.println(a);
    }
}
