public class Driver {
    public static void main(String[] args) {
        UserSystem.loadSystem();
        int opt;

        do {
            opt = Menu.display("Main", "Register", "Login", "Exit");
            switch (opt) {
                case 1 -> UserSystem.register();
                case 2 -> {
                    Citizen citizen = UserSystem.login();
                        if (citizen != null) {
                            citizen.showMenu();
                            System.out.println("Logged out.");
                        }
                }
            }
            System.out.println();
        } while (opt != 3);

        UserSystem.saveSystem();
        System.out.println("Terminating program...");
    }
}
