package com.dhy.annotation;

import java.lang.reflect.Field;

/**
 * Created by Dai on 2016/8/6.
 */
public class FruitInfoUtil {
    public static void getFruitInfo(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(FruitName.class)) {
                FruitName fruitName = field.getAnnotation(FruitName.class);
                System.out.println("水果名: " + fruitName.value() );
            }
            else if (field.isAnnotationPresent(FruitColor.class)) {
                FruitColor fruitColor = field.getAnnotation(FruitColor.class);
                System.out.println("颜色： " + fruitColor.fruitColor());
            }
            else if (field.isAnnotationPresent(FruitProvider.class)) {
                FruitProvider fruitProvider = field.getAnnotation(FruitProvider.class);
                System.out.println("供应商编号 ：" + fruitProvider.id() + "  供应商名字 ： " + fruitProvider.name()
                    + "  供应商地址： " + fruitProvider.address());

            }
        }
    }
}
