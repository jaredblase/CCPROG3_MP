

public class Driver {
    public static void main(String[] args) {
        User.loadUsers();
        int opt;

        do {
            opt = Menu.display("Main", "Register", "Login", "Exit");
            switch (opt) {
                case 1:
                    User.register();
                    break;

                case 2:
                    Citizen citizen = User.login();
                    if(citizen != null) {
                        citizen.showMenu();
                    }
                    break;
            }
        } while (opt != 3);

        System.out.println("Terminating program...");
        User.setPassword("Hello");
    }
}
