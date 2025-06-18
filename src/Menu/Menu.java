package Menu;
import view.AdminView;
import view.UserView;
import controller.UserController;
import java.util.Scanner;
public class Menu {
    static Scanner scanner;
    void Menu(Scanner scanner){
        this.scanner=scanner;
    }
    public static void menu() {
        scanner=new Scanner(System.in);
        while (true) {
            System.out.println("1. Sign Up\n2. Sign In\n3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    UserView.signUp();
                    break;
                case 2:
                    UserView.signIn();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
