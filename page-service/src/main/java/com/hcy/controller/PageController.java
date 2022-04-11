package com.hcy.controller;

import com.hcy.service.PageService;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/23 20:07
 */
@RestController
@RequestMapping("/page")
public class PageController {
    @Autowired
    private PageService pageService;
    @RequestMapping("/genPage")
    public String generatePage(String goodsId) throws TemplateException, IOException {
        return pageService.genPage(goodsId);
    }
}
