import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppendFormattedTest {
    @Test
    public void appendFormatted() {
        StringBuilder sb = new StringBuilder();
        ShoppingCart.appendFormatted(sb, "TestString", -1, 10);
        assertEquals(sb.toString(), "TestString ");
        sb.setLength(0);
        ShoppingCart.appendFormatted(sb, "TestString", -1, 12);
        assertEquals(sb.toString(), "TestString   ");
        sb.setLength(0);
        ShoppingCart.appendFormatted(sb, "TestString", -1, 9);
        assertEquals(sb.toString(), "TestStrin ");
        sb.setLength(0);
        ShoppingCart.appendFormatted(sb, "TestString", -1, 1);
        assertEquals(sb.toString(), "T ");
        sb.setLength(0);

        ShoppingCart.appendFormatted(sb, "TestString", 0, 10);
        assertEquals(sb.toString(), "TestString ");
        sb.setLength(0);
        ShoppingCart.appendFormatted(sb, "TestString", 0, 12);
        assertEquals(sb.toString(), " TestString  ");
        sb.setLength(0);
        ShoppingCart.appendFormatted(sb, "TestString", 0, 11);
        assertEquals(sb.toString(), "TestString  ");
        sb.setLength(0);
        ShoppingCart.appendFormatted(sb, "TestString", 0, 13);
        assertEquals(sb.toString(), " TestString   ");
        sb.setLength(0);
        ShoppingCart.appendFormatted(sb, "TestString", 0, 9);
        assertEquals(sb.toString(), "TestStrin ");
        sb.setLength(0);
        ShoppingCart.appendFormatted(sb, "TestString", 0, 1);
        assertEquals(sb.toString(), "T ");
        sb.setLength(0);


        ShoppingCart.appendFormatted(sb, "TestString", 1, 10);
        assertEquals(sb.toString(), "TestString ");
        sb.setLength(0);
        ShoppingCart.appendFormatted(sb, "TestString", 1, 12);
        assertEquals(sb.toString(), "  TestString ");
        sb.setLength(0);
        ShoppingCart.appendFormatted(sb, "TestString", -1, 9);
        assertEquals(sb.toString(), "TestStrin ");
        sb.setLength(0);
        ShoppingCart.appendFormatted(sb, "TestString", -1, 1);
        assertEquals(sb.toString(), "T ");
        sb.setLength(0);
    }
}