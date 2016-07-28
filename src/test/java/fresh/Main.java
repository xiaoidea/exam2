package fresh;

/**
 * Created by Dai on 2016/7/26.
 */
interface Prototype {
    Object clone();
}

class ConcretePrototype1 implements Prototype {
    @Override
    public Object clone() {
        Prototype prototype = new ConcretePrototype1();
        return prototype;
    }
}

class ConcretePrototype2 implements Prototype {
    @Override
    public Object clone() {
        Prototype prototype = new ConcretePrototype2();
        return prototype;
    }
}

class Client {
    public void operation(Prototype example) {
        Prototype copy = (Prototype) example.clone();
    }
}

public class Main {

}

