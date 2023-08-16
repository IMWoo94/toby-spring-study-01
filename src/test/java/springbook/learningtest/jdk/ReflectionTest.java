package springbook.learningtest.jdk;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ReflectionTest {

    @Test
    public void invokeMethod() throws Exception{
        String name = "spring";

        // length();
        assertThat(name.length(), is(6));

        Method lengthMethod = String.class.getMethod("length");
        assertThat((Integer)lengthMethod.invoke(name), is(6));

        // charAt()
        assertThat(name.charAt(0), is('s'));

        Method chatAtMethod = String.class.getMethod("charAt", int.class);
        assertThat((Character)chatAtMethod.invoke(name, 0), is('s'));


    }

    @Test
    public void simpleProxy(){
        Hello hello = new HelloTarget();
        assertThat(hello.sayHello("Toby"), is("Hello Toby"));
        assertThat(hello.sayHi("Toby"), is("Hi Toby"));
        assertThat(hello.sayThankYou("Toby"), is("ThankYou Toby"));

        Hello proxiedHello = new HelloUppercase(new HelloTarget());
        assertThat(proxiedHello.sayHello("Toby"), is("HELLO TOBY"));
        assertThat(proxiedHello.sayHi("Toby"), is("HI TOBY"));
        assertThat(proxiedHello.sayThankYou("Toby"), is("THANKYOU TOBY"));

        Hello dynamicProxiedHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[] {Hello.class},
                new uppercaseHandler(new HelloTarget())
                );
        assertThat(dynamicProxiedHello.sayHello("Toby"), is("HELLO TOBY"));
        assertThat(dynamicProxiedHello.sayHi("Toby"), is("HI TOBY"));
        assertThat(dynamicProxiedHello.sayThankYou("Toby"), is("THANKYOU TOBY"));

    }
}
