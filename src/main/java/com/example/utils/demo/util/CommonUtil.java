package com.example.utils.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

/**
 * 用于存放一些公共方法
 */
@Slf4j
public class CommonUtil {

    /**
     * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
     * @param obj
     * @return
     */
    public static boolean isNullOrEmpty(Object obj){
        if (obj == null)
            return true;
        if (obj instanceof CharSequence)
            return ((CharSequence) obj).length() == 0;
        if (obj instanceof Collection)
            return ((Collection) obj).isEmpty();
        if (obj instanceof Map)
            return ((Map) obj).isEmpty();
        if (obj instanceof Object[]) {
            Object[] objects = (Object[]) obj;
            if (objects.length == 0) {
                return true;
            }
            boolean empty = true;
            for (Object object: objects) {
                if (!isNullOrEmpty(object)) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        return false;
    }

    /**
     * 判断对象是否为空，属性值是否为空
     * @param obj
     * @return
     */
    public static boolean objIsNull(Object obj) {
        if (obj == null)
            return true;
        Class clazz = obj.getClass(); // 得到类对象
        Field fields[] = clazz.getDeclaredFields(); // 得到所有属性
        boolean flag = true; //定义返回结果，默认为true
        for(Field field : fields){
            //设置属性值可见
            field.setAccessible(true);
            Object fieldValue = null;
            try {
                fieldValue = field.get(obj); //得到属性值
                Type fieldType =field.getGenericType();//得到属性类型
                String fieldName = field.getName(); // 得到属性名
                log.info("属性类型：{} ,属性名：{} ,属性值：{}", fieldType, fieldName, fieldValue);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if(!isNullOrEmpty(fieldValue)){  //只要有一个属性值不为null 就返回false 表示对象不为null
                flag = false;
                break;
            }
        }
        return flag;
    }

    /**
     * 根据当前日期，打散目录
     * yyyy/MM/dd
     * @return
     */
    public static String[] getDateFolder() {
        String[] retVal = new String[3];

        LocalDate localDate = LocalDate.now();
        retVal[0] = localDate.getYear() + "";

        int month = localDate.getMonthValue();
        retVal[1] = month < 10 ? "0" + month : month + "";

        int day = localDate.getDayOfMonth();
        retVal[2] = day < 10 ? "0" + day : day + "";

        return retVal;
    }

    /**
     * 获取文件后缀
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index != -1) {
            String suffix = fileName.substring(index + 1);
            if (!suffix.isEmpty()) {
                return suffix;
            }
        }
        throw new IllegalArgumentException("非法的文件名称：" + fileName);
    }

    public static String getJarRootPath(Long accId) throws FileNotFoundException {
        StringBuffer pathBuffer = new StringBuffer();
        String path = ResourceUtils.getURL("classpath:").getPath();
        if (path == null || path.trim().isEmpty()) {
            log.info("根目录不存在，赋值初始路径");
            path = "/";
        }
        log.info("ResourceUtils.getURL(\"classpath:\").getPath() -> "+path);
        pathBuffer.append(path);
        pathBuffer.append("updownload/");
        pathBuffer.append(accId);
        //创建File时会自动处理前缀和jar包路径问题  => /root/tmp
        File rootFile = new File(pathBuffer.toString());
        if (!rootFile.exists() && !rootFile.isDirectory()) {
            boolean created = rootFile.mkdirs();
            if (!created) {
                log.error("路径: '" + rootFile.getAbsolutePath() + "'创建失败");
                throw new RuntimeException("路径: '" + rootFile.getAbsolutePath() + "'创建失败");
            }
        }
        log.info("上传或下载文件所在路径: "+rootFile.getAbsolutePath());
        //需要给绝对路径
        return rootFile.getAbsolutePath()+"/";
    }

}
