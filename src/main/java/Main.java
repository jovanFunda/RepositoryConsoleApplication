import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {

            Class.forName("GoogleDriveRepository");
            Repository repository = RepositoryManager.getRepository();

            if(args.length != 1) {
                System.out.println("Application should have one argument(path to root directory)!");
                System.exit(1);
            }

            Scanner sc = new Scanner(System.in);

            boolean firstMaking = repository.Init(args[0]);
            if(firstMaking) {
                System.out.println("Hello! What do you want your username and password to be: ");
                String credential = sc.nextLine();
                String[] creds = credential.split(" ");
                repository.AddUser(creds[0], creds[1], "adminPermission");
                repository.LogIn(creds[0], creds[1]);
            } else {
                loggingLoop(repository, sc);
            }

            System.out.println("Welcome " + repository.loggedUser.getUsername() + "!");
            System.out.println("Available commands are: createDirectory, createDirectoryWithPattern, createFile, addUser, moveFile, listFiles, deleteFile, banExtension, setMaxSize, logOut, exit");

            while(repository.loggedUser != null) {

                String option = sc.nextLine();

                switch (option) {
                    case "createDirectory":
                        System.out.println("Enter name for new directory: ");
                        String directoryName = sc.nextLine();
                        repository.CreateDirectory(directoryName);
                        System.out.println("Repository " + directoryName + " created!");
                        break;
                    case "createDirectoryWithPattern":
                        System.out.println("Type folderName(numberOne,numberTwo) to use pattern ");
                        String pattern = sc.nextLine();
                        repository.parseDirs(pattern);
                        break;
                    case "createFile":
                        System.out.println("Enter name for new file: ");
                        String fileName = sc.nextLine();
                        repository.CreateFile(fileName);
                        System.out.println("Create file " + fileName + "!");
                        break;
                    case "addUser":
                        System.out.println("Enter username, password and privilege(user, spectator)");
                        String user = sc.nextLine();
                        String[] userProperties = user.split(" ");
                        repository.AddUser(userProperties[0], userProperties[1], userProperties[2]);
                        break;
                    case "moveFile":
                        System.out.println("Enter full path of your file that you want to move ");
                        String from = sc.nextLine();
                        System.out.println("Enter full path where you want to put your file: ");
                        String to = sc.nextLine();
                        repository.MoveFile(from, to);
                        System.out.println("File successfuly moved");
                        break;
                    case "listFiles":
                        System.out.println("Enter the file to be searched for ");
                        String file_name = sc.nextLine();
                        System.out.println("Enter the directory where to search ");
                        String dir_path = sc.nextLine();
                        repository.ListFiles(file_name, dir_path);
                        break;
                    case "deleteFile":
                        System.out.println("Enter name of your file you want to delete: ");
                        String file = sc.nextLine();
                        repository.DeleteFile(file);
                        System.out.println("File " + file + " successfuly deleted.");
                        break;
                    case "banExtension":
                        System.out.println("What extension do you want to exclude? (exe, png, jpg...)");
                        String extension = sc.nextLine();
                        repository.ExcludeExtension(extension);
                        System.out.println("Extension " + extension + " excluded!");
                        break;
                    case "setMaxSize":
                        System.out.println("How much do you want to be max size of your repository in bytes? ");
                        int size = sc.nextInt();
                        repository.SetMaxBytes(size);
                        System.out.println("Repository max size is set to " + size + "!");
                        break;
                    case "logOut":
                        repository.LogOut();
                        System.out.println("Logged out..");
                        loggingLoop(repository, sc);
                        break;
                    case "exit":
                        System.out.println("Exiting..");
                        return;
                }

                System.out.println("Available commands are: createDirectory, createDirectoryWithPattern, createFile, addUser, moveFile, listFiles, deleteFile, banExtension, setMaxSize, logOut, exit");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void loggingLoop(Repository repository, Scanner sc) {
        while(repository.loggedUser == null) {
            System.out.println("Log in with your credentials as 'Username Password' or type exit");
            String[] credentials = sc.nextLine().split(" ");
            if(credentials[0].equals("exit"))
                System.exit(1);
            String username = credentials[0];
            String password = credentials[1];
            try {
                repository.LogIn(username, password);
            } catch (UserDoesntExistException e) {
                System.out.println("Couldn't find user with these parameters in our database! Try again.");
            }
        }
    }
}