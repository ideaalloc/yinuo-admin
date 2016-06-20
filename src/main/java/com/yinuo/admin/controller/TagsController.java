package com.yinuo.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Title.
 * <p/>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2014-12-17
 */
@Controller
@RequestMapping("/tags")
public class TagsController {
    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "tags/index";
    }
}
