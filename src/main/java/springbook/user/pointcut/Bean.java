package springbook.user.pointcut;

public class Bean {
	public void method() throws RuntimeException {

	}

	/*
	  AspectJ 표현식 지시자 -> execution()
	  execution( [접근제한자 패턴] 리턴 타입 패턴 [패키지 및 클래스 타입 패턴.]메소드 이름 패턴 ( 타입 패턴 | ".." , ... ) [throws 예외 패턴]
	  ex) public int springbook.user.pointcut.Target.minus(int,int) throws java.lang.RuntimeException
	  public -> 접근제한자 패턴
	  int -> 리턴 타입 패턴
	  springbook.uer.pointcut.Target -> 패키지 및 클래스 타입 패턴
	  minus -> 메소드 이름 패턴
	  (int,int) -> 타입 패턴
	  throws java.lang.RuntimeException -> Throws 예외 패턴
	 */
}
