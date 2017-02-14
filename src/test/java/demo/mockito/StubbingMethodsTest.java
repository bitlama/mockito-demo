package demo.mockito;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class StubbingMethodsTest {

    private PasswordEncoder passwordEncoder;

    @Before
    public void beforeMethod() {
        passwordEncoder = mock(PasswordEncoder.class);
    }

    @Test
    public void thenReturn() {
        when(passwordEncoder.encode("1")).thenReturn("a");

        assertEquals("a", passwordEncoder.encode("1"));
    }

    @Test
    public void thenReturnConsecutive() {
        when(passwordEncoder.encode("1")).thenReturn("a", "b");

        assertEquals("a", passwordEncoder.encode("1"));
        assertEquals("b", passwordEncoder.encode("1"));
        assertEquals("b", passwordEncoder.encode("1"));
    }

    @Test
    public void thenAnswerGetArgument() {
        when(passwordEncoder.encode("1")).thenAnswer(
                invocation -> invocation.getArgument(0) + "!");

        assertEquals("1!", passwordEncoder.encode("1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void thenAnswerThrowException() {
        when(passwordEncoder.encode("1")).thenAnswer(invocation -> {
            throw new IllegalArgumentException();
        });

        passwordEncoder.encode("1");
    }

    @Test
    public void thenAnswerCallRealMethod() {
        Date mock = mock(Date.class);
        doAnswer(InvocationOnMock::callRealMethod).when(mock).setTime(42);
        doAnswer(InvocationOnMock::callRealMethod).when(mock).getTime();

        mock.setTime(42);

        assertEquals(42, mock.getTime());
    }

    @Test(expected = Exception.class)
    public void thenCallRealMethodFail() {
        when(passwordEncoder.encode("1")).thenCallRealMethod();
    }

    @Test
    public void thenCallRealMethod() {
        Date mock = mock(Date.class);
        doCallRealMethod().when(mock).setTime(42);
        when(mock.getTime()).thenCallRealMethod();

        mock.setTime(42);

        assertEquals(42, mock.getTime());
    }

    @Test(expected = IllegalArgumentException.class)
    public void thenThrowExceptionByInstance() {
        when(passwordEncoder.encode("1")).thenThrow(new IllegalArgumentException());

        passwordEncoder.encode("1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void thenThrowExceptionByClass() {
        when(passwordEncoder.encode("1")).thenThrow(IllegalArgumentException.class);

        passwordEncoder.encode("1");
    }
}
