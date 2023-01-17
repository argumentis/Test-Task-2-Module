import javafx.util.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(Parameterized.class)
public class CalculateDiscountTest {
    private Pair<ShoppingCart.ItemType, Integer> itemParams;
    private int expectedResult;

    public CalculateDiscountTest(Pair<ShoppingCart.ItemType, Integer> itemParams, int expectedResult) {
        this.itemParams = itemParams;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection parameters() {
        return Arrays.asList(new Object[][]{
                {new Pair<>(ShoppingCart.ItemType.REGULAR, 1), 0},
                {new Pair<>(ShoppingCart.ItemType.REGULAR, 9), 0},
                {new Pair<>(ShoppingCart.ItemType.REGULAR, 10), 1},
                {new Pair<>(ShoppingCart.ItemType.REGULAR, 11), 1},
                {new Pair<>(ShoppingCart.ItemType.REGULAR, 21), 2},
                {new Pair<>(ShoppingCart.ItemType.REGULAR, 101), 10},
                {new Pair<>(ShoppingCart.ItemType.REGULAR, 201), 20},
                {new Pair<>(ShoppingCart.ItemType.REGULAR, 800), 80},
                {new Pair<>(ShoppingCart.ItemType.REGULAR, 1000), 80},
                {new Pair<>(ShoppingCart.ItemType.SECOND_FREE, 1), 0},
                {new Pair<>(ShoppingCart.ItemType.SECOND_FREE, 2), 50},
                {new Pair<>(ShoppingCart.ItemType.SECOND_FREE, 9), 50},
                {new Pair<>(ShoppingCart.ItemType.SECOND_FREE, 10), 51},
                {new Pair<>(ShoppingCart.ItemType.SECOND_FREE, 11), 51},
                {new Pair<>(ShoppingCart.ItemType.SECOND_FREE, 21), 52},
                {new Pair<>(ShoppingCart.ItemType.SECOND_FREE, 101), 60},
                {new Pair<>(ShoppingCart.ItemType.SECOND_FREE, 300), 80},
                {new Pair<>(ShoppingCart.ItemType.SECOND_FREE, 400), 80},
                {new Pair<>(ShoppingCart.ItemType.SALE, 1), 70},
                {new Pair<>(ShoppingCart.ItemType.SALE, 9), 70},
                {new Pair<>(ShoppingCart.ItemType.SALE, 10), 71},
                {new Pair<>(ShoppingCart.ItemType.SALE, 100), 80},
                {new Pair<>(ShoppingCart.ItemType.SALE, 200), 80},
                {new Pair<>(ShoppingCart.ItemType.NEW, 1), 0},
                {new Pair<>(ShoppingCart.ItemType.NEW, 9), 0},
                {new Pair<>(ShoppingCart.ItemType.NEW, 10), 0},
                {new Pair<>(ShoppingCart.ItemType.NEW, 11), 0},
                {new Pair<>(ShoppingCart.ItemType.NEW, 300), 0}
        });
    }

    @Test
    public void calculateDiscount() {
        System.out.println("Parameterized Number is : " + itemParams);
        assertEquals(expectedResult, ShoppingCart.calculateDiscount(itemParams.getKey(), itemParams.getValue()));
    }
}
