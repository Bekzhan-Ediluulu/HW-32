import java.util.*;

public class WendingMachine {
    private final List<Product> productsInStock = new ArrayList<>();
    private int moneyInside;
    private final List<Product>available = new ArrayList<>();

    public int getMoneyInside() {
        return moneyInside;
    }
    public void refresh(){
        available.removeIf(product -> (moneyInside<product.getPrice()));
    }
    public void setMoneyInside(int inputMoney) {
        moneyInside += inputMoney;
        for (Product product :
                productsInStock) {
            if (moneyInside >= product.getPrice() && !available.contains(product)) available.add(product);
        }
    }

    public void setProducts(){
        productsInStock.removeAll(productsInStock);
        List<Product>products = new ArrayList<>();
        products.add(new Product("Чипсы",60));
        products.add(new Product("Вода", 25));
        products.add(new Product("Сок", 50));
        products.add(new Product("Бургер", 80));
        products.add(new Product("Шоколад", 40));

        Random rnd = new Random();
        for (int i = 0; i < rnd.nextInt(5)+1; i++) {
            Product product = products.get(rnd.nextInt(products.size()));
            if (productsInStock.contains(product)) {
                i--;
            } else productsInStock.add(product);
        }
        System.out.println("В автомате доступны:");
        for (Product product :
                productsInStock) {
            System.out.printf("*%s | %d сом\n",product.getName(),product.getPrice());
        }
    }

    public List<Product> getAvailable() {
        return available;
    }
}
