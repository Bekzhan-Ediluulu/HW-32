import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static WendingMachine wendingMachine = new WendingMachine();
    static boolean simulation = true;
    public static void main(String[] args) {
        wendingMachine.setProducts();
        simulation();
    }
    public static void simulation(){
        wendingMachine.refresh();
        System.out.printf("Свободные средства на автомате: %d сом\n", wendingMachine.getMoneyInside());
        System.out.println("Доступные действия:\n" +
                "а - закинуть еще монет");
        System.out.println("r - подождать, пока не завезут что-нибудь ещё (введеные вами деньги могут пропасть!)\n" +
                "q - выйти");
        try {
            for (Product product :
                    wendingMachine.getAvailable()) {
                System.out.printf("%d - купить: %s | %d сом\n", wendingMachine.getAvailable().indexOf(product) + 1, product.getName().toLowerCase(), product.getPrice());
            }
        } catch (NullPointerException e) {
            System.out.println("Закиньте больше монет, чтобы купить");
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("Ввод: ");
        String action = sc.nextLine();
        switch (action.toLowerCase().trim()){
            case "q": simulation = false;
            break;
            case "a": putMoney();
            break;
            case "r":
                Random rnd = new Random();
                if (rnd.nextInt(10)+1<9) wendingMachine.setMoneyInside(-wendingMachine.getMoneyInside());
                wendingMachine.setProducts();
                simulation();
                break;
            default: buy(action);
            break;
        }


    }
    public static void buy(String action){


        try {
            int actionNum = Integer.parseInt(action);
            if (wendingMachine.getAvailable().size()==0 || actionNum>=wendingMachine.getAvailable().size()+1) {
                System.out.print("!!!Некорректный ввод, повторите!!!\n");
                simulation();
            }
            for (int j = 0; j < wendingMachine.getAvailable().size(); j++) {
                if (actionNum==j+1) {
                    wendingMachine.setMoneyInside(-wendingMachine.getAvailable().get(j).getPrice());
                    System.out.printf("Вы купили: %s\n", wendingMachine.getAvailable().get(j).getName().toLowerCase());
                    simulation();
                }
            }
        } catch (NullPointerException e) {
            System.out.println("!!!Недостаточно средств для покупки, закиньте еще!!!\n");
            simulation();
        } catch (InputMismatchException e) {
            System.out.print("!!!Некорректный ввод, повторите!!!\n");
            simulation();
        } catch (NumberFormatException e) {
            System.out.print("!!!Некорректный ввод, повторите ввод!!!\n");
            simulation();
        }
    }
    public static void putMoney() {
        System.out.print("Введите сумму: ");
        Scanner sc = new Scanner(System.in);
        try {
            int sum = sc.nextInt();
            wendingMachine.setMoneyInside(sum);
            System.out.printf("Вы закинули %d сом\n", sum);
            simulation();
        } catch (InputMismatchException e) {
            System.out.print("!!!Некорректный ввод, повторите!!!\n");
            putMoney();
        }

    }
}