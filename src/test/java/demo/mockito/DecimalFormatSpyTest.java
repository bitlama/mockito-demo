package demo.mockito;

import org.junit.Test;

import java.text.DecimalFormat;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;

public class DecimalFormatSpyTest {

    @Test
    public void test() {
        DecimalFormat decimalFormat = spy(new DecimalFormat());
        assertEquals("42", decimalFormat.format(42L));
    }
}
