/*
 * 版权所有 © 北京晟壁科技有限公司 2008-2027。保留一切权利!
 */
package com.zlxtk.boot.framework.util;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用途：读取Boot项目Classpath路径下的文件
 * 作者: lishuyi
 * 时间: 2018/8/2  21:13
 */
@Slf4j
public class BootAppFileReader {

    public static BufferedReader getClasspathFile(String filepath){
        filepath = ResourceUtils.CLASSPATH_URL_PREFIX + filepath;
        BufferedReader bufferedReader = null;
        try {
            ClassPathResource resource = new ClassPathResource(filepath);
            InputStream inputStream = resource.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        } catch (Exception e){
            try {
                bufferedReader = new BufferedReader(new FileReader(ResourceUtils.getFile(filepath)));
            } catch (FileNotFoundException e1) {
            }
        }
        if(bufferedReader == null){
            log.error("######################Load classpath file {} error.", filepath);
        }
        return bufferedReader;
    }

    public static String getClasspathFileToString(String filepath){
        BufferedReader bufferedReader = getClasspathFile(filepath);
        List<String> lines = bufferedReader.lines().collect(Collectors.toList());
        String content = Joiner.on("\n").join(lines);
        return content;
    }
}
