package com.hcy.service;

import freemarker.template.TemplateException;

import java.io.IOException;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/23 20:07
 */
public interface PageService {
    String genPage(String goodsId) throws IOException, TemplateException;
}
