package com.muzili.image.test;

import com.muzili.common.utils.UploadUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

public class Test01 {

    @Test
    public void showPath(){
        String path = UploadUtils.getUploadPath("iloveu.jpg", "/Users/DrZon/IdeaProjects/Supermarket/supermarketimg/upload");
        System.out.println(path);
    }
}
