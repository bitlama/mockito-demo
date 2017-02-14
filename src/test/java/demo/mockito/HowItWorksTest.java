package demo.mockito;

import org.junit.Test;

import static org.mockito.AdditionalMatchers.or;
import static org.mockito.Mockito.*;

public class HowItWorksTest {

    @Test
    public void testSnippet() {
        // 1: create
        PasswordEncoder mock = mock(PasswordEncoder.class);

        // 2: stub
        when(mock.encode("a")).thenReturn("1");

        // 3: act
        mock.encode("a");

        // 4: verify
        verify(mock).encode(or(eq("a"), endsWith("b")));
    }

    @Test
    public void testSnippetHello() {
        PasswordEncoder mock = mock(PasswordEncoder.class);

        // 2
        mock.encode("a");
        when("Hi, Mockito!").thenReturn("1");

        mock.encode("a");
        verify(mock).encode(or(eq("a"), endsWith("b")));
    }

    @Test
    public void testMatcherInMethod() {
        // 1: create
        PasswordEncoder mock = mock(PasswordEncoder.class);

        // 2: stub
        when(mock.encode("a")).thenReturn("1");

        // 3: act
        mock.encode("a");

        // 4: verify
        verify(mock).encode(matchCondition());
    }

    private String matchCondition() {
        return or(eq("a"), endsWith("b"));
    }
}
