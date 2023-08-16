package springbook.learningtest.mock;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static springbook.user.service.UserService.*;


@ExtendWith(MockitoExtension.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MockTest {

    @Mock
    User userMock;

    @Spy
    User userSpy;

    @BeforeAll
    public void setUp(){
        userMock = new User("lee", "이상민", "lee", Level.BRONZE, MIN_LOGCOUNT_FOR_GOLD, 70,"dlrldyd1002@gamil.com");
        System.out.println(userMock.getId());
        userSpy = new User("lee", "이상민", "lee", Level.BRONZE, MIN_LOGCOUNT_FOR_GOLD, 70,"dlrldyd1002@gamil.com");
        System.out.println(userSpy.getId());

    }

    @Test
    void testMockAnnotationReferenceTypeAndPrimitiveType(){
        assertThat(userMock.getId(), is(nullValue()));
        assertThat(userMock.getLogin(), is(0));

        System.out.println(userMock.getId());
    }

    @Test
    void testSpyAnnotationReferenceTypeAndPrimitiveType(){
        assertThat(userSpy.getId(), is(notNullValue()));
        assertThat(userSpy.getLogin(), is(80));

        System.out.println(userSpy.getId());
    }





}
