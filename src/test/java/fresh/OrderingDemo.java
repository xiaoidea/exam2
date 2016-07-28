package fresh;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

/**
 * Created by Dai on 2016/7/23.
 */
class Person {
    int age;
    String name;

    public Person(int age, String name) {
        super();
        this.age = age;
        this.name = name;
    }

    @Override
    public String toString() {
        return name +" : " + age;
    }
}

public class OrderingDemo {

    @Test
    public void orderingDemo() {
        Person p1 = new Person(14, "lily");
        Person p2 = new Person(17, "david");
        Person p3 = new Person(23, "tom");
        List list = Lists.newArrayList(p1, p2, p3);

        //按Person年龄排序
        Ordering<Person> natural = Ordering.natural().reverse().nullsFirst().onResultOf(new Function<Person, Comparable>() {
            public Comparable apply(Person input) {
                return input.age;
            }
        });

        Collections.sort(list, natural);
        System.out.println(list);

        //按姓名字母序排序
        natural = Ordering.natural().nullsFirst().onResultOf(new Function<Person, Comparable>() {
            public Comparable apply(Person input) {
                return input.name;
            }
        });

        Collections.sort(list, natural);
        System.out.println(list);

        //按姓名长度排序
        natural = Ordering.natural().nullsFirst().onResultOf(new Function<Person, Comparable>() {
            public Comparable apply(Person input) {
                return input.name.length();
            }
        });

        Collections.sort(list, natural);
        System.out.println(list);
    }
}


