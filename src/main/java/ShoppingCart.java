import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
/**
* Containing items and calculating price.
*/
public class ShoppingCart {
    public static enum ItemType { NEW, REGULAR, SECOND_FREE, SALE };
    /**
     * Tests all class methods.
     */
    public static void main(String[] args){
        // TODO: add tests here
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Apple", 0.99, 5, ItemType.NEW);
        cart.addItem("Banana", 20.00, 4, ItemType.SECOND_FREE);
        cart.addItem("A long piece of toilet paper", 17.20, 1, ItemType.SALE);
        cart.addItem("Nails", 2.00, 500, ItemType.REGULAR);
        System.out.println(cart.formatTicket());
    }
    /**
     * Adds new item.
     *
     * @param title item title 1 to 32 symbols
     * @param price item ptice in USD, > 0
     * @param quantity item quantity, from 1
     * @param type item type
     *
     * @throws IllegalArgumentException if some value is wrong
     */
    public void addItem(String title, double price, int quantity, ItemType type){
        if (title == null || title.length() == 0 || title.length() > 32)
            throw new IllegalArgumentException("Illegal title");
        if (price < 0.01)
            throw new IllegalArgumentException("Illegal price");
        if (quantity <= 0)
            throw new IllegalArgumentException("Illegal quantity");
        Item item = new Item();
        item.title = title;
        item.price = price;
        item.quantity = quantity;
        item.type = type;
        items.add(item);
    }

    /**
     * Formats shopping price.
     *
     * @return string as lines, separated with \n,
     * first line: # Item Price Quan. Discount Total
     * second line: ---------------------------------------------------------
     * next lines: NN Title $PP.PP Q DD% $TT.TT
     * 1 Some title $.30 2 - $.60
     * 2 Some very long $100.00 1 50% $50.00
     * ...
     * 31 Item 42 $999.00 1000 - $999000.00
     * end line: ---------------------------------------------------------
     * last line: 31 $999050.60
     *
     * if no items in cart returns "No items." string.
     */
    public String formatTicket(){
        if (items.size() == 0)
            return "No items.";
        String[] header = {"#","Item","Price","Quan.","Discount","Total"};
        int[] align = new int[] { 1, -1, 1, 1, 1, 1 };
        List<String[]> lines = getTableLines();
        double total = getTotal();
        String[] footer = { String.valueOf(items.size()),"","","","", MONEY.format(total) };
        // formatting table
        StringBuilder sb = formatTicketTable(lines, header, align, footer);
        return sb.toString();
    }

    private double getTotal() {
        double total = 0.00;
        for (Item item : items) {
            int discount = calculateDiscount(item.type, item.quantity);
            total += item.price * item.quantity * (100.00 - discount) / 100.00;
        }
        return total;
    }

    private List<String[]> getTableLines() {
        List<String[]> lines = new ArrayList<>();
        // formatting each line
        int index = 0;
        for (Item item : items) {
            int discount = calculateDiscount(item.type, item.quantity);
            double itemTotal = item.price * item.quantity * (100.00 - discount) / 100.00;
            lines.add(new String[]{
                    String.valueOf(++index),
                    item.title,
                    MONEY.format(item.price),
                    String.valueOf(item.quantity),
                    (discount == 0) ? "-" : (String.valueOf(discount) + "%"),
                    MONEY.format(itemTotal)
            });
        }

        return lines;
    }
    private StringBuilder formatTicketTable(List<String[]> lines, String[] header, int[] align, String[] footer) {
        // column max length
        int[] width = getColumnMaxLength(lines, header, footer);
        // line length
        int lineLength = getLineLength(width);
        StringBuilder sb = new StringBuilder();
        // header
        appendFormattedLine(header, align, width, sb, false);
        // separator
        appendSeparator(lineLength, sb);
        // lines
        for (String[] line : lines) {
            appendFormattedLine(line, align, width, sb, false);
        }
        if (lines.size() > 0) {
            // separator
            appendSeparator(lineLength, sb);
        }
        // footer
        appendFormattedLine(footer, align, width, sb, true);
        return sb;
    }

    private void appendFormattedLine(String[] header, int[] align, int[] width, StringBuilder sb, boolean isFooter) {
        for (int i = 0; i < header.length; i++)
            appendFormatted(sb, header[i], align[i], width[i]);
        if (!isFooter) sb.append("\n");
    }

    private void appendSeparator(int lineLength, StringBuilder sb) {
        for (int i = 0; i < lineLength; i++)
            sb.append("-");
        sb.append("\n");
    }

    private int getLineLength(int[] width) {
        int lineLength = width.length - 1;
        for (int w : width)
            lineLength += w;
        return lineLength;
    }

    private int[] getColumnMaxLength(List<String[]> lines, String[] header, String[] footer) {
        int[] width = new int[]{0,0,0,0,0,0};
        for (String[] line : lines)
            adjustColumnWidth(width, line);
        adjustColumnWidth(width, header);
        adjustColumnWidth(width, footer);
        return width;
    }

    private void adjustColumnWidth(int[] width, String[] line) {
        for (int i = 0; i < line.length; i++)
            width[i] = (int) Math.max(width[i], line[i].length());
    }

    // --- private section -----------------------------------------------------
    private static final NumberFormat MONEY;
        static {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator('.');
            MONEY = new DecimalFormat("$#.00", symbols);
        }
    /**
     * Appends to sb formatted value.
     * Trims string if its length > width.
     * @param align -1 for align left, 0 for center and +1 for align right.
     */
    public static void appendFormatted(StringBuilder sb, String value, int align, int width){
        if (value.length() > width)
            value = value.substring(0,width);
        int before = (align == 0)
            ? (width - value.length()) / 2
            : (align == -1) ? 0 : width - value.length();
        int after = width - value.length() - before;
        while (before-- > 0)
            sb.append(" ");
        sb.append(value);
        while (after-- > 0)
            sb.append(" ");
        sb.append(" ");
    }
    /**
     * Calculates item's discount.
     * For NEW item discount is 0%;
     * For SECOND_FREE item discount is 50% if quantity > 1
     * For SALE item discount is 70%
     * For each full 10 not NEW items item gets additional 1% discount,
     * but not more than 80% total
     */
    public static int calculateDiscount(ItemType type, int quantity){
        int discount = 0;
        switch (type) {
            case NEW:
                return 0;
            case REGULAR:
                discount = 0;
                break;
            case SECOND_FREE:
                if (quantity > 1)
                    discount = 50;
                break;
            case SALE:
                discount = 70;
                break;
        }
        if (discount < 80) {
            discount += quantity / 10;
        if (discount > 80)
            discount = 80;
        }
        return discount;
    }
    /** item info */
    private static class Item{
        String title;
        double price;
        int quantity;
        ItemType type;
    }
    /** Container for added items */
    private List<Item> items = new ArrayList<Item>();
}
