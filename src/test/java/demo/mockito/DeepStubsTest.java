package demo.mockito;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class DeepStubsTest {

    @Test
    public void testVerify() {
        We mock = mock(We.class, Mockito.RETURNS_DEEP_STUBS);

        mock.we().are().so().deep();

        verify(mock.we().are().so()).deep();
    }

    @Test
    public void testAssert() {
        We mock = mock(We.class, Mockito.RETURNS_DEEP_STUBS);

        when(mock.we().are().so().deep()).thenReturn(true);

        assertTrue(mock.we().are().so().deep());
    }

    interface We { Are we(); }
    interface Are { So are(); }
    interface So { Deep so(); }
    interface Deep { boolean deep(); }
}
