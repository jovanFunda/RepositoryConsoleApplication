import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {

            Class.forName("GoogleDriveRepository");
            Repository repository = RepositoryManager.getRepository();

            if(args.length != 1) {
                System.out.println("Application should have only one argument(path to root directory)!");
                System.exit(1); // status 1?
            }

            repository.Init("root/TestDir"); // samo staviti args[0]
            Scanner sc = new Scanner(System.in);

            loggingLoop(repository, sc);

            System.out.println("Welcome back " + repository.loggedUser.getUsername() + "!");

            while(repository.loggedUser != null) {

                System.out.println("1) Create directory");
                System.out.println("2) Create file");
                System.out.println("9) Log out");
                System.out.println("0) Exit");

                int option = sc.nextInt();

                switch (option) {
                    case 1:
                        System.out.println("Enter name for new directory:");
                        String directoryName = sc.nextLine();
                        repository.CreateDirectory(directoryName);
                        break;
                    case 2:
                        System.out.println("Enter name for new file:");
                        String fileName = sc.nextLine();
                        repository.CreateFile(fileName);
                        break;
                    case 9:
                        repository.LogOut();
                        System.out.println("Logged out..");
                        loggingLoop(repository, sc);
                        break;
                    case 0:
                        System.exit(1); // valjda 1
                        break;
                }
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void loggingLoop(Repository repository, Scanner sc) throws UserDoesntExistException {
        while(repository.loggedUser == null) {
            System.out.println("Log in with your credentials as 'Username Password'");
            String[] credentials = sc.nextLine().split(" ");
            String username = credentials[0];
            String password = credentials[1];
            repository.LogIn("Test1", "123456");
        }
    }
}