package com.junlaninfo.controller;


import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Created by 辉 on 2020/7/4.
 */
@Component
public class ZookeeperRunner implements ApplicationRunner {
    // 创建zk连接
    ZkClient zkClient = null;
    private String path = "/election";
    @Value("${server.port}")
    private String serverPort;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        System.out.println("项目已启动就执行这个方法");
        zkClient = new ZkClient("192.168.196.175:2181");
        createEphemeral();  //尝试创建节点
        //创立监听watcher，看节点有无被删除和改变
        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                // 主节点已经挂了，重新选举
                System.out.println("主节点已经挂了，重新开始选举");
                createEphemeral();
            }
        });
    }

    private void createEphemeral() {
        try {
            zkClient.createEphemeral(path); //创建临时节点
            Sentry.isMaster = true;         //创建节点要是成功，就把ismaster置为true，创建不成功就会出异常
        } catch (Exception e) {
            Sentry.isMaster = false;
        }


    }
}
