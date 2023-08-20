package springbook.learningtest.jdk.proxy;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.lang.reflect.Proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import springbook.learningtest.jdk.Hello;
import springbook.learningtest.jdk.HelloTarget;
import springbook.user.handler.UppercaseHandler;

public class DynamicProxyTest {

	@Test
	@DisplayName("Java 제공 JDK Dynamic Proxy")
	public void simpleProxy() {

		Hello proxiedHello = (Hello)Proxy.newProxyInstance(
			getClass().getClassLoader(),
			new Class[] {Hello.class},
			new UppercaseHandler(new HelloTarget())
		);

		Hello noProxiedHello = new HelloTarget();

		System.out.println("jdk dynamic proxy : " + proxiedHello.getClass());
		System.out.println("smple new object : " + noProxiedHello.getClass());

		System.out.println(proxiedHello.sayHello("proxyHello"));
		System.out.println(noProxiedHello.sayHello("proxyHello"));

	}

	@Test
	@DisplayName("spring 제공 ProxyFactoryBean")
	public void proxyFactroyBean() {
		ProxyFactoryBean pfBean = new ProxyFactoryBean();
		// 타켓 설정
		pfBean.setTarget(new HelloTarget());

		// 부가 기능(어드바이스)를 추가 한다. 여러 개 추가 가능
		pfBean.addAdvice(new UppercaseAdvice());
		pfBean.addAdvice(new ReplaceAdvice());

		// 굳이 지정하지 않아도 자동검출 기능을 활용해 타겟의 오브젝트가 구현하는 인터페이스 정보를 알아낸다.
		pfBean.setInterfaces(Hello.class);

		Hello proxiedHello = (Hello)pfBean.getObject();
		System.out.println(proxiedHello.getClass());
		assertThat(proxiedHello.sayHello("Toby"), is("HELLO LEE"));
		assertThat(proxiedHello.sayHi("Toby"), is("HI LEE"));
		assertThat(proxiedHello.sayThankYou("Toby"), is("THANKYOU LEE"));

	}

	static class UppercaseAdvice implements MethodInterceptor {
		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
			System.out.println("UppercaseAdvice start");
			String ret = (String)invocation.proceed();
			System.out.println("UppercaseAdvice end");
			return ret.toUpperCase();
		}
	}

	static class ReplaceAdvice implements MethodInterceptor {

		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
			System.out.println("ReplaceAdvice start");
			Object object = invocation.proceed();
			System.out.println("ReplaceAdvice end");
			if (object instanceof String) {
				return ((String)object).replace("Toby", "lee");
			}
			return object;
		}
	}

	@Test
	public void pointcutAdvisor() {
		ProxyFactoryBean pfBean = new ProxyFactoryBean();
		pfBean.setTarget(new HelloTarget());

		// 메소드 이름을 비교해서 대상을 선정하는 알고리즘을 제공하는 포인트컷 생성
		NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
		// 이름 비교조건 설정, sayH로 시작하는 모든 메소드를 선택하게 한다.
		pointcut.setMappedName("sayH*");
		//pointcut.setMappedNames(new String[] {"sayHello", "sayHi"});

		// 포인트컷과 어드바이스를 Advisor 로 묶어서 한번에 추가
		pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));

		Hello proxiedHello = (Hello)pfBean.getObject();

		assertThat(proxiedHello.sayHello("Toby"), is("HELLO TOBY"));
		assertThat(proxiedHello.sayHi("Toby"), is("HI TOBY"));
		assertThat(proxiedHello.sayThankYou("Toby"), is("ThankYou Toby"));

	}
}
