package springbook.learningtest.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class uppercaseHandler implements InvocationHandler {

    Object target;

    public uppercaseHandler(Object target) {
        this.target = target;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object ret = method.invoke(target, args);
        if(ret instanceof String && method.getName().startsWith("say")) {
            System.out.println("다이내믹 프록시 InvocationHandler 구현 Invoke overriding");
            return ((String) ret).toUpperCase();
        }
        return ret;
    }
}
