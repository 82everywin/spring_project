package org.study.controllers.admin.study;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Category {

    List<String> categoryList = new ArrayList<>(Arrays.asList("인문","수학","영어","과학","사회"));

    /**
     * 카테고리 추가
     * 
     * @param newCate
     */
    public List<String> add(String newCate){
        
        categoryList.add(newCate);
        return categoryList;
    }
}
