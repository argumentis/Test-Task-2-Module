import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class FormatTicketTest {
    private ShoppingCart shoppingCart;
    private String expectedResult;

    public FormatTicketTest(ShoppingCart shoppingCart, String expectedResult) {
        this.shoppingCart = shoppingCart;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection parameters() {
        ShoppingCart cart1 = new ShoppingCart();
        String expectedResult1 = "No items.";
        ShoppingCart cart2 = new ShoppingCart();
        cart2.addItem("Apple", 0.99, 5, ShoppingCart.ItemType.NEW);
        cart2.addItem("Banana", 20.00, 4, ShoppingCart.ItemType.SECOND_FREE);
        cart2.addItem("A long piece of toilet paper", 17.20, 1, ShoppingCart.ItemType.SALE);
        cart2.addItem("Nails", 2.00, 500, ShoppingCart.ItemType.REGULAR);
        String expectedResult2 =
                "# Item                          Price Quan. Discount   Total \n" +
                "------------------------------------------------------------\n" +
                "1 Apple                          $.99     5        -   $4.95 \n" +
                "2 Banana                       $20.00     4      50%  $40.00 \n" +
                "3 A long piece of toilet paper $17.20     1      70%   $5.16 \n" +
                "4 Nails                         $2.00   500      50% $500.00 \n" +
                "------------------------------------------------------------\n" +
                "4                                                    $550.11 ";
        ShoppingCart cart3 = new ShoppingCart();
        cart3.addItem("Coca-Cola", 10.00, 4, ShoppingCart.ItemType.REGULAR);
        cart3.addItem("Banana", 20.00, 1, ShoppingCart.ItemType.SECOND_FREE);
        String expectedResult3 =
                "# Item       Price Quan. Discount  Total \n" +
                "----------------------------------------\n" +
                "1 Coca-Cola $10.00     4        - $40.00 \n" +
                "2 Banana    $20.00     1        - $20.00 \n" +
                "----------------------------------------\n" +
                "2                                 $60.00 ";
        ShoppingCart cart4 = new ShoppingCart();
        cart4.addItem("Ice Cream", 5.00, 15, ShoppingCart.ItemType.SALE);
        String expectedResult4 =
                "# Item      Price Quan. Discount  Total \n" +
                "---------------------------------------\n" +
                "1 Ice Cream $5.00    15      71% $21.75 \n" +
                "---------------------------------------\n" +
                "1                                $21.75 ";
        return Arrays.asList(new Object[][]{
                {cart1, expectedResult1},
                {cart2, expectedResult2},
                {cart3, expectedResult3},
                {cart4, expectedResult4}
        });
    }

    @Test
    public void formatTicket() {
        assertEquals(expectedResult, shoppingCart.formatTicket());
    }
}
