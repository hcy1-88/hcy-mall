package com.hcy.controller;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2021/10/22 16:12
 */
@RestController
public class CodeController {
    @RequestMapping("/gen")
    public String gen(@RequestParam("projectName") String projectName, @RequestParam("moduleName") String moduleName, @RequestParam("tableName") String tableName){

        // 创建 AutoGenerator
        AutoGenerator autoGenerator = new AutoGenerator();

        // 设置全局配置
        GlobalConfig gc = new GlobalConfig();
        // 项目的根目录,如果你项目是 父工程+子模块，返回的是父工程的根目录；F\...\..\projectName
        // 如果你的项目就一个工程，没有子模块，还是你这个工程的根目录
        String projectPath = System.getProperty("user.dir");
        if (!checkProjectName(projectName,projectPath)){
            return "error,你的工程名有问题！";
        }
        gc.setOutputDir(projectPath+"/"+moduleName+"/src/main/java/")  //设置生成文件夹的位置
                .setAuthor("hcy")
                .setOpen(false)  // 打开文件夹
                //是否覆盖文件
                .setFileOverride(true)
                //设置主键自增策略
                .setIdType(IdType.AUTO)
                .setMapperName("%sMapper")  //所有dao都以Mapper结尾
                .setServiceName("%sService")  // Service 结尾
                .setServiceImplName("%sServiceImpl")
                .setControllerName("%sController")
                .setDateType(DateType.ONLY_DATE);


        // 设置数据源datasource
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL)
                .setUrl("jdbc:mysql://localhost:3306/hcymall?serverTimezone=GMT%2B8&characterEncoding=utf-8")
                .setUsername("root")
                .setPassword("hcy1805110063")
                .setDriverName("com.mysql.cj.jdbc.Driver");

        // 设置包信息
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName("generator")  // 将 controller、service等文件夹放在该文件夹下
        pc
                .setParent("com.hcy")  // 模块位置的文件夹
                .setMapper("mapper")  // 在 com/hcy下新建mapper文件夹
                .setService("service")
                .setController("controller")
                .setEntity("entity");
        autoGenerator.setPackageInfo(pc);

        // 如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath+"/"+moduleName+ "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        autoGenerator.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        autoGenerator.setTemplate(templateConfig);

        // 设置策略
        StrategyConfig sc = new StrategyConfig();
        sc.setEntitySerialVersionUID(true)   // 生成的实体类自动实现Serializable接口, 默认true
                .setInclude(tableName.split(","))
                //数据库 表 映射到实体类命名策略，驼峰
                .setNaming(NamingStrategy.underline_to_camel)
                //数据库表 字段 映射到实体类的命名策略，驼峰
                .setColumnNaming(NamingStrategy.underline_to_camel)
                //生成的dao,service,entity不再带tbl_前缀
                .setTablePrefix("hcy_")
                .setEntityLombokModel(true) // Lombok
                .setVersionFieldName("version")  // 版本号属性名
                .setEntityBooleanColumnRemoveIsPrefix(true) // 表中布尔类型的值去掉_前的前缀
                .setLogicDeleteFieldName("deleted");  //逻辑删除

        // 执行代码生成
        autoGenerator.setGlobalConfig(gc)
                .setDataSource(dsc)
                .setPackageInfo(pc)
                .setStrategy(sc);
        autoGenerator.execute();
        return "ok";
    }

    public boolean checkProjectName(String projectName,String path){
        // 交易工程名是否正确
        // path：  F:\idea_workspace\projects\my-mall\hcy-mall
        int i = path.lastIndexOf("\\");
        String s = path.substring(i + 1);
        if (s.equals(projectName)){
            // 输入的工程名正确
            return true;
        }else{
            return false;
        }
    }
}
