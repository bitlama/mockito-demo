package demo.mockito;

import org.junit.Test;

import static org.mockito.Mockito.mock;

public class PasswordEncoderMockDemoTest {

    @Test
    public void simpleMock() {
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    }
}
