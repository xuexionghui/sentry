package com.junlaninfo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 辉 on 2020/7/4.
 */
@RestController
public class IndexController {

    @GetMapping("/getInfo")
    public  String  getInfo(){
     return  "当前是否为主节点："+Sentry.isMaster;
    }
}
