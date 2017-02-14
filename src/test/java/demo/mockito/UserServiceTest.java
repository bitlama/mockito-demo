package demo.mockito;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Test
    public void testIsValidUser() {
        UserRepository userRepository = createUserRepository();
        PasswordEncoder passwordEncoder = createPasswordEncoder();
        UserService userService = new UserService(userRepository, passwordEncoder);

        boolean userIsValid = userService.isValidUser("user id", "password");
        assertTrue(userIsValid);

        // userRepository had to be used to find a user with id="user id"
        verify(userRepository).findById("user id");

        // passwordEncoder had to be used to compute a hash of "password"
        verify(passwordEncoder).encode("password");
    }

    private PasswordEncoder createPasswordEncoder() {
        PasswordEncoder mock = mock(PasswordEncoder.class);
        when(mock.encode(anyString())).thenReturn("hash");
        return mock;
    }

    private UserRepository createUserRepository() {
        User user = new User("user id", "hash", true);
        UserRepository mock = mock(UserRepository.class);
        when(mock.findById(anyString())).thenReturn(user);
        return mock;
    }
}