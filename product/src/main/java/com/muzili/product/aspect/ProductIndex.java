package com.muzili.product.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.muzili.common.domain.Product;
import com.muzili.common.vo.CommonResult;
import com.muzili.common.vo.SysResult;
import com.muzili.product.service.SearchService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class ProductIndex {

    @Autowired
    private SearchService searchService = null;

    /**
     * 增加商品后需要同步更新索引
     * @param jp 连接点
     * @param result 返回值
     * @throws JsonProcessingException 抛出
     */
    @AfterReturning(value = "execution(* com.muzili.product.service.ProductService*.addProduct(..))", returning = "result")
    public void afterAddProduct(JoinPoint jp, Object result) throws JsonProcessingException {
        // 获取方法参数
        Map<String, Object> map = new HashMap<String, Object>();
        Object[] values = jp.getArgs();
        String[] names = ((CodeSignature)jp.getSignature()).getParameterNames();
        for (int i = 0; i < names.length; i++) {
            map.put(names[i], values[i]);
        }
        Product product = (Product) map.get("product");
        // 切
        CommonResult<?> addResult = this.searchService.addProduct(product);
    }

    /**
     * 更新商品后需要同步更新索引，先删后增
     * @param jp 连接点
     * @param result 返回值
     * @throws JsonProcessingException 抛出
     */
    @AfterReturning(value = "execution(* com.muzili.product.service.ProductService*.updateProduct(..))", returning = "result")
    public void afterUpdateProduct(JoinPoint jp, Object result) throws JsonProcessingException {
        // 获取方法参数
        Map<String, Object> map = new HashMap<String, Object>();
        Object[] values = jp.getArgs();
        String[] names = ((CodeSignature)jp.getSignature()).getParameterNames();
        for (int i = 0; i < names.length; i++) {
            map.put(names[i], values[i]);
        }
        Product product = (Product) map.get("product");
        CommonResult<?> deleteResult = this.searchService.deleteProduct(product);
        this.afterAddProduct(jp, result);
    }
}
