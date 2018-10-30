package com.weizu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fh.controller.base.BaseController;
import com.weizu.service.AddressLookService;

@Controller
@RequestMapping(value="/weizu/user")
public class UserController extends BaseController{
	
	String menuUrl = "weizu/user/list.do"; //菜单地址(权限用)
	@Autowired
	private AddressLookService addressLookService;

}
