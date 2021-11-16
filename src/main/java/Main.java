public class Main {

    public static void main(String[] args) {
        try {

            Class.forName("LocalRepository");

            Repository repository = RepositoryManager.getRepository();
            repository.CreateDirectory("String");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}