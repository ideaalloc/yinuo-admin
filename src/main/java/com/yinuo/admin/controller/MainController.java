package com.yinuo.admin.controller;

import com.yinuo.admin.model.vo.StaffVo;
import com.yinuo.admin.service.StaffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

/**
 * Title.
 * <p/>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2014-12-16
 */
@Controller
@RequestMapping("/")
public class MainController {
    static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @Autowired
    StaffService staffService;

    @RequestMapping(method = RequestMethod.GET)
    @Secured("ROLE_ADMIN")
    public String index(Principal principal, Model model) {
        LOGGER.info("user {} accesses", principal.getName());
        final StaffVo staff = staffService.getStaff(principal.getName());
        model.addAttribute("staff", staff);
        return "index";
    }
}
