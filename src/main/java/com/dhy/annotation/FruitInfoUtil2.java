package com.dhy.annotation;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * Created by Dai on 2016/8/6.
 */
@SupportedAnnotationTypes({"FruitColor", "FruitName", "FruitProvider"})
public class FruitInfoUtil2 extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(FruitName.class)) {
            if (element.getKind() == ElementKind.FIELD) {
                FruitName fruitName = element.getAnnotation(FruitName.class);
                System.out.println("水果名: " + fruitName.value() );
            }
        }

        for (Element element : roundEnv.getElementsAnnotatedWith(FruitColor.class)) {
            if (element.getKind() == ElementKind.FIELD) {
                FruitColor fruitColor = element.getAnnotation(FruitColor.class);
                System.out.println("颜色： " + fruitColor.fruitColor());
            }
        }

        for (Element element : roundEnv.getElementsAnnotatedWith(FruitProvider.class)) {
            if (element.getKind() == ElementKind.FIELD) {
                FruitProvider fruitProvider = element.getAnnotation(FruitProvider.class);
                System.out.println("供应商编号 ：" + fruitProvider.id() + "  供应商名字 ： " + fruitProvider.name()
                        + "  供应商地址： " + fruitProvider.address());
            }
        }

        return false;
    }
}