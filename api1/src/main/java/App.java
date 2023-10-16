public class App {

    public static void main(String[] args) {

        System.out.println(Finder.getLocationFromCoordinates(55.67, 37.48).toString() + "\n");

        Finder.getLocationFromQuery("Москва, МИРЭА - Российский технологический университет")
                .forEach(item -> System.out.println(item.toString()));
    }
}
