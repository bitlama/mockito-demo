package demo.mockito;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.exceptions.verification.NoInteractionsWanted;
import org.mockito.exceptions.verification.VerificationInOrderFailure;
import org.mockito.exceptions.verification.junit.ArgumentsAreDifferent;

import static org.mockito.Mockito.*;

public class VerificationTest {

    private PasswordEncoder passwordEncoder;

    @Before
    public void beforeTest() {
        passwordEncoder = mock(PasswordEncoder.class);
        when(passwordEncoder.encode("a")).thenReturn("1");
    }

    @Test
    public void artificialExample() {
        passwordEncoder.encode("a");
        verify(passwordEncoder).encode("a");
    }

    @Test(expected = ArgumentsAreDifferent.class)
    public void artificialExampleFail() {
        passwordEncoder.encode("a");
        verify(passwordEncoder).encode("b");
    }

    @Test
    public void artificialExampleWithArgumentMatchers() {
        passwordEncoder.encode("a");
        verify(passwordEncoder).encode(anyString());

/*
        // verify the exact number of invocations
        verify(passwordEncoder, times(42)).encode(anyString());

        // verify that there was at least one invocation
        verify(passwordEncoder, atLeastOnce()).encode(anyString());

        // verify that there were at least five invocations
        verify(passwordEncoder, atLeast(5)).encode(anyString());

        // verify the maximum number of invocations
        verify(passwordEncoder, atMost(5)).encode(anyString());

        // verify that there were no invocations
        verify(passwordEncoder, never()).encode(anyString());

        // verify that it was the only invocation and
        // that there're no more unverified interactions
        verify(passwordEncoder, only()).encode(anyString());
*/
    }

    @Test
    public void testOnly() {
        passwordEncoder.encode("a");
        verify(passwordEncoder, only()).encode(anyString());
    }

    @Test
    public void testNever() {
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    public void testVerifyNoMoreInteractionsSuccess1() {
        verifyNoMoreInteractions(passwordEncoder);
    }

    @Test
    public void testVerifyNoMoreInteractionsSuccess2() {
        passwordEncoder.encode("a");
        passwordEncoder.encode("b");

        verify(passwordEncoder).encode("a");
        verify(passwordEncoder).encode("b");
        verifyNoMoreInteractions(passwordEncoder);
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testVerifyNoMoreInteractionsFailure() {
        passwordEncoder.encode("a");
        verifyNoMoreInteractions(passwordEncoder);
    }

    @Test
    public void testVerifyZeroInteractionsSuccess() {
        verifyZeroInteractions(passwordEncoder);
    }

    @Test(expected = NoInteractionsWanted.class)
    public void testVerifyZeroInteractionsFailure() {
        passwordEncoder.encode("a");
        verifyZeroInteractions(passwordEncoder);
    }

    @Test
    public void verifyTimeout() {
        usePasswordEncoderInOtherThread();
        verify(passwordEncoder, timeout(500)).encode("a");
    }

    @Test
    public void verifyAfter() {
        usePasswordEncoderInOtherThread();
        verify(passwordEncoder, after(500)).encode("a");
    }

    private void usePasswordEncoderInOtherThread() {
        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            passwordEncoder.encode("a");
        }).start();
    }

    @Test
    public void verifyInOrderSuccess() {
        PasswordEncoder first = mock(PasswordEncoder.class);
        PasswordEncoder second = mock(PasswordEncoder.class);

        // simulate calls
        first.encode("f1");
        second.encode("s1");
        first.encode("f2");

        // verify call order
        InOrder inOrder = inOrder(first, second);
        inOrder.verify(first).encode("f1");
        inOrder.verify(second).encode("s1");
        inOrder.verify(first).encode("f2");
    }

    @Test(expected = VerificationInOrderFailure.class)
    public void verifyInOrderFail() {
        PasswordEncoder first = mock(PasswordEncoder.class);
        PasswordEncoder second = mock(PasswordEncoder.class);

        // simulate calls
        first.encode("f1");
        second.encode("s1");
        first.encode("f2");

        // verify call order
        InOrder inOrder = inOrder(first, second);
        inOrder.verify(first).encode("f1");
        inOrder.verify(first).encode("f2");
        inOrder.verify(second).encode("s1");
    }
}
