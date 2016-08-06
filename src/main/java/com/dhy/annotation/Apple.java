package com.dhy.annotation;

/**
 * Created by Dai on 2016/8/6.
 */
public class Apple {
    @FruitName("Apple")
    private String name;

    @FruitColor(fruitColor = FruitColor.Color.RED)
    private String color;

    @FruitProvider(id=1, name="hongfushi", address = "shanxi")
    private String provider;

}
