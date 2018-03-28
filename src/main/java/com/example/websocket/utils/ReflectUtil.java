package com.example.websocket.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 反射工具类
 * User: org.yunai
 * Date: 12/7/12
 * Time: 11:32 AM
 */
public class ReflectUtil {
    private  static Logger logger = LoggerFactory.getLogger(ReflectUtil.class);

    /**
     * 深度扫描指定包，并且注解为指定类的所有类集合
     *
     * @param basePackage     包地址。比如，com.eku
     * @param annotationClass 注解！
     * @return 类集合
     */
    public static Set<Class<?>> scanClass(String basePackage, Class<?> annotationClass) {
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                ClassUtils.convertClassNameToResourcePath(basePackage) + "/" + "**/*.class";
        Set<Class<?>> sets = new HashSet<>();
        try {
            ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
            MetadataReaderFactory metadataReaderFactory = new SimpleMetadataReaderFactory(resourcePatternResolver);
            for (Resource resource : resources) {
                MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                if (metadataReader.getAnnotationMetadata().isAnnotated(annotationClass.getName())) {
                    sets.add(Class.forName(metadataReader.getClassMetadata().getClassName()));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return sets;
    }

    /**
     * 设置Field
     *
     * @param target 目标
     * @param name 属性名
     * @param value 属性值
     */
    public static void setField(Object target, String name, Object value) {
        Field field = ReflectionUtils.findField(target.getClass(), name);
        if (field == null) {
            throw new RuntimeException(String.format("class(%s) name(%s) 不存在", target.getClass(), name));
        }
        if (!field.isAccessible()) {
            field.setAccessible(true);
            ReflectionUtils.setField(field, target, value);
            field.setAccessible(false);
        } else {
            ReflectionUtils.setField(field, target, value);
        }
    }
    /**
     * 获取类中的属性值
     * @param clazz
     * @return
     */
    public static Map<String,Object> getFieldValue(Class clazz){
        Map<String,Object> fieldValueMap = new HashMap<>();
        try {
            Field[] fields = clazz.getFields();
            for( Field field : fields ){
                fieldValueMap.put(field.getName(),field.get(clazz));
            }
        }catch (Exception e){
            logger.error("get field value fail,className:" + clazz.getName(),e);
        }
        return  fieldValueMap;
    }

    /**
     * 扫描指定路径下的枚举类
     * @param basePackage
     * @return
     */
    public static Set<Class<?>> scanEnumClass(String basePackage) {
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                ClassUtils.convertClassNameToResourcePath(basePackage) + "/" + "**/*.class";
        Set<Class<?>> sets = new HashSet<>();
        try {
            ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
            for (Resource resource : resources) {
                try {
                    logger.info("class path:"+basePackage+"."+ StringUtils.substring(resource.getFilename(),0,resource.getFilename().length()-6));
                    Class cz = Class.forName(basePackage+"."+ StringUtils.substring(resource.getFilename(),0,resource.getFilename().length()-6));
                    if(cz.isEnum()){
                        sets.add(cz);
                    }
                }catch (Exception e){
                    logger.error("scanEnumClass method get class name error",e);
                }
            }
        } catch (Exception e) {
            logger.error("scanEnumClass method get class name error",e);
            throw new RuntimeException(e);
        }
        return sets;
    }

    /**
     * 获取枚举类中的属性值
     * @param classSet
     * @param methodName
     * @return
     */
    public  static Map<Object,Object> getEnumValue(Set<Class<?>> classSet,String methodName){
        Map<Object,Object> fieldValue = new HashMap<>();
        for (Class c:classSet) {
            if (c.isEnum()) {
                try {
                    Method toValue = c.getMethod(methodName);
                    Object[] enumConstants =  c.getEnumConstants();
                    for (Object o: enumConstants) {
                        fieldValue.put(toValue.invoke(o),toValue.invoke(o));
                    }
                } catch (Exception e) {
                    logger.error("getEnumValue method get enum value fail",e);
                }

            }
        }
        return  fieldValue;
    }
}