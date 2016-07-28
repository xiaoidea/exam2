package fresh;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.List;
import java.util.Set;

/**
 * Created by Dai on 2016/7/24.
 */
public class PredicateAndFunction {

    @Test
    public void predicateDemo() {
        Set<String> nameSet = Sets.newHashSet("lily", "tom", "david");

        Set<String> filteredSet = Sets.filter(nameSet, new Predicate<String>() {
            public boolean apply(String input) {
                return (input.length() > 3) ? true : false ;
            }
        });

        System.out.println(filteredSet);
        //[david, lily]
    }

    @Test
    public void functionDemo() {
        List<String> nameList = Lists.newArrayList("lily", "tom", "david");

        List<String> transformedList = Lists.transform(nameList, new Function<String, String>() {
            public String apply(String input) {
                return "{" + input + "}";
            }
        });

        System.out.println(transformedList);
        //[{lily}, {tom}, {david}]
    }
}
