package com.yellow.apidoc.controller;

import com.yellow.apidoc.GeneratorApiDoc;
import com.yellow.apidoc.bean.ApiDocAction;
import com.yellow.apidoc.bean.ApiDocInfo;
import com.yellow.apidoc.utis.utils.JsonUtil;
import com.yellow.apidoc.bean.ApiDoc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 文档生成controller
 * @Author: admin
 * @CreateDate: 2018/1/8 16:12
 */
@RequestMapping("/apidoc")
@RestController
public class ApiDocController {

    //是否开启文档功能
    @Value("${cloud.apidoc}")
    private boolean openApiDoc;

    //数据源配置
    @Value("${spring.datasource.driver-class-name}")
    private String driver;//数据库驱动
    @Value("${spring.datasource.url}")
    private String url;//数据源连接
    @Value("${spring.datasource.username}")
    private String userName;//数据库用户名
    @Value("${spring.datasource.password}")
    private String password;//数据库密码
    private String dataBaseName = "test" +
            "";//数据库名

    /**
     * 后台管理系统文档
     * <p>
     * 返回文档基本信息和目录
     */
    @GetMapping("/api/admin")
    public ApiDoc admin() {
        
        System.out.println("enter admin");
        //是否打开文档功能
        if (openApiDoc) {

            ApiDoc apiDoc = null;
            //后台管理系统
            String packageName = "com.yellow.demo.web";
            apiDoc = new GeneratorApiDoc()
                    .setInfo(//设置文档基本信息
                            new ApiDocInfo()
                                    .setTitle("某某系统后台管理文档")
                                    .setVersion("1.0")
                                    .setDescription("问题描述\n" +
                                            "1.描述一11111\n" +
                                            "2.描述22222\n" +
                                            "3.描述333333333\n")

                    )
                    .generator(packageName);//指定生成哪个包下controller的文档
        
            System.err.println(JsonUtil.toString(apiDoc));
            return apiDoc;
//        String doc = JSONUtil.formatJsonStr(JSON.toJSONString(apiDoc));//格式化json输出
        } else {
            return null;
        }
    }

    /**
     * 模块详细信息
     */
    @GetMapping("/detail")
    public ApiDocAction detail(String methodUUID) {
        ApiDocAction detail = new GeneratorApiDoc()
                .setUrl(url)
                .setUserName(userName)
                .setPassword(password)
                .setDataBaseName(dataBaseName)
                .getApiOfMethod(methodUUID);
        System.err.println(JsonUtil.toString(detail));
        return detail;
    }

}
