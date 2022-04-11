package com.hcy.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    // 插入一条新记录时填充
    @Override
    public void insertFill(MetaObject metaObject) {
        // 参数一：java类中的属性名，参数二：你要填充的是什么，参数三：给数据库插入的元数据对象
        this.setFieldValByName("createTime",new Date(),metaObject);  // 插入日期
        this.setFieldValByName("updateTime",new Date(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime",new Date(),metaObject); // 插入日期
    }
}