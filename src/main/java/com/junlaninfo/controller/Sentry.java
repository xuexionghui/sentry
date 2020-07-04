package com.junlaninfo.controller;

import org.springframework.stereotype.Component;

/**
 * Created by 辉 on 2020/7/4.
 * 这个类主要做一个标识，看它是否为主节点
 */
@Component
public class Sentry {
    public  static  boolean isMaster;
}
