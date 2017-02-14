package demo.mockito;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.internal.util.MockUtil.isMock;

public class AnnotationsTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test1() {
        passwordEncoder.encode("1");
        verify(passwordEncoder).encode(anyString());
    }

    @Test
    public void test2() {
        verifyZeroInteractions(passwordEncoder, userRepository);
        assertFalse(isMock(userService));
    }
}
