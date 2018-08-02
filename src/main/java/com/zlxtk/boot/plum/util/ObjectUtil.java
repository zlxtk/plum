
package com.zlxtk.boot.plum.util;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.zlxtk.boot.plum.base.exception.Exceptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.persistence.Column;
import javax.persistence.Id;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 用途： 对象工具类
 * 作者: lishuyi
 * 时间: 2018/6/7  0:38
 */
@Slf4j
public class ObjectUtil extends org.apache.commons.lang3.ObjectUtils {
    /**
     * 判断对象的所有属性是否均为空
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        for (PropertyDescriptor pd : PropertyUtils.getPropertyDescriptors(obj)) {
            try{
                if (!pd.getName().equals("class")&& pd.getReadMethod()!=null && !pd.getReadMethod().getName().equals("isEmpty")) {
                    try {
                        Field f = getIndicateField(obj, pd.getName());
                        if (!f.isAnnotationPresent(javax.persistence.Transient.class)) {
                            Object value = pd.getReadMethod().invoke(obj);
                            if (value instanceof Collection) {
                                if (((Collection<?>) value).size() != 0)
                                    return false;
                            } else if (value instanceof Map) {
                                if (((Map<?, ?>) value).size() != 0)
                                    return false;
                            } else if (value != null)
                                return false;
                        }
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        return false;
                    }
                }
            }catch(Exception e){
                Exceptions.printException(e);
            }
        }
        return true;
    }

    /**
     * 获取持久化对象的主键Id字段
     *
     * @param obj
     * @return
     */
    public static Field getEntityIdField(Object obj) {
        Field id = null;
        for (Field field : getAllFields(obj.getClass())) {
            if (field.isAnnotationPresent(Id.class)) {
                id = field;
                break;
            }
        }
        return id;
    }

    /**
     * 获取持久化对象的主键Id字段值
     * @param obj
     * @return
     */
    public static Object getEntityIdVaue(Object obj) {
        try {
            Field id = getEntityIdField(obj);
            id.setAccessible(true);
            return id.get(obj);
        } catch (IllegalAccessException e) {
            Exceptions.printException(e);
        }
        return null;
    }

    /**
     * 获取被Column标注的持久化字段
     * @param obj
     * @return
     */
    public static Set<Field> getEntityPersistentFieldExceptId(Object obj) {
        Set<Field> fields = Sets.newHashSet();
        for (Field field : getAllFields(obj.getClass())) {
            if (field.isAnnotationPresent(Column.class)) {
                fields.add(field);
            }
        }
        return fields;
    }

    /**
     * 获取被Column标注的持久化字段值
     * @param obj
     * @return
     */
    public static Map<String, Object> getEntityPersistentFieldValueExceptId(Object obj) {
        Map<String, Object> persistentFieldValues = Maps.newHashMap();
        Set<Field> persistentFields = getEntityPersistentFieldExceptId(obj);
        final BeanWrapper src = new BeanWrapperImpl(obj);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> propertyNames = new HashSet<String>();
        for (PropertyDescriptor pd : pds) {
            Field f = getIndicateField(obj, pd.getName());
            if(persistentFields.contains(f) && src.getPropertyValue(pd.getName()) != null){
                persistentFieldValues.put(pd.getName(), src.getPropertyValue(pd.getName()));
            }
        }
        return persistentFieldValues;
    }

    /**
     * 获取指定字段的Field
     * @param obj
     * @param fieldName
     * @return
     */
    public static Field getIndicateField(Object obj, String fieldName) {
        Field indicateField = null;
        for (Field field : getAllFields(obj.getClass())) {
            if (field.getName().equals(fieldName)) {
                indicateField = field;
                break;
            }
        }
        return indicateField;
    }

    /**
     * 返回所有字段
     */
    public static Field[] getAllFields(Class<?> clazz) {
        Collection<Class<?>> classes = getAllSuperClasses(clazz);
        classes.add(clazz);
        return getAllFields(classes);
    }

    /**
     * 返回所有字段
     */
    private static Field[] getAllFields(Collection<Class<?>> classes) {
        Set<Field> fields = Sets.newHashSet();
        for (Class<?> clazz : classes) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        }
        return fields.toArray(new Field[fields.size()]);
    }

    /**
     * 返回所有超类
     */
    public static Collection<Class<?>> getAllSuperClasses(Class<?> clazz) {
        Set<Class<?>> classes = Sets.newHashSet();
        Class<?> superclass = clazz.getSuperclass();
        while (superclass != null) {
            classes.add(superclass);
            superclass = superclass.getSuperclass();
        }

        return classes;
    }
}
