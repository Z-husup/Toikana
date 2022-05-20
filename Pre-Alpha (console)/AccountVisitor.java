import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Scanner;


public class AccountVisitor {
    public static void main(String[] args) throws Exception {


        System.out.println("We provide the following event types:");
        Map<Integer, Event> eventTypes = getEventTypes();

        System.out.println("--------------------------------------");
        System.out.printf("%5s %16s %14s", "ID", "NAME", "PRICE\n");

        for (Event event : eventTypes.values()) {
            System.out.println("--------------------------------------");
            System.out.printf("%5s %16s %14s\n",
                    event.getId(), event.getName(), event.getPrice());
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter a desired event'S ID: ");
        int eventType = sc.nextInt();
        if (!eventTypes.containsKey(eventType)) {
            System.out.println("We don't provide such an event");
            return;
        }

        System.out.print("Enter amount of people: ");
        int peopleAmount = sc.nextInt();

        System.out.println("Write as an example: 12/03/2022");
        System.out.print("Enter date: ");
        String date = sc.next();

        System.out.print("Would you like to order some food? (YES/NO): ");
        String foodReply = sc.next().toLowerCase();
        if (!foodReply.equals("yes")) {
            return;
        }

        System.out.println("Catalog of restaurants/Menu");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%5s %15s %14s %24s %24s %25s %25s", "ID", "NAME","CAPACITY", "ADDRESS", "LOCATION", "CONTACT", "OPENED\n");

        List<Restaurant> restaurants = readAllRestaurants();
        for (Restaurant restaurant : restaurants) {
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%5s %15s %14s %24s %24s %28s %25s\n",
                    restaurant.getId(), restaurant.getName(),restaurant.getCapacity(), restaurant.getAddress(), restaurant.getLocation(), restaurant.getContact(), restaurant.getOpened());
            System.out.println();

        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("Choose restaurant ID: ");
        int restaurantId = scanner.nextInt();

        List<Dish> dishes = getDishesByRestaurant(restaurantId);
        Map<Integer, String> measures = readMeasures();

        System.out.println("-----------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%5s %30s %30s %20s %30s", "ID", "NAME", "MEASURE", "PRICE", "CATEGORY");
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------------------------------------------------");
        for (Dish dish : dishes) {
            String measure = measures.get(dish.getMeasureId());
            System.out.format("%5s %30s %30s %20s %30s\n",
                    dish.getId(), dish.getName(), "1 " + measure, dish.getPrice(), dish.getCategory());
        }

        Map<Integer, Dish> dishIdToDish = new HashMap<>();
        for (Dish dish : dishes) {
            dishIdToDish.put(dish.getId(), dish);
        }

        List<Order> orders = new ArrayList<>();
        while (true) {
            System.out.print("Would you like to order (yes/no): ");
            String response = scanner.next().toLowerCase();
            if (response.equals("yes")) {
                System.out.print("Enter ID of the dish: ");
                Integer dishId = scanner.nextInt();

                Integer dishMeasureId = dishIdToDish.get(dishId).getMeasureId();
                String measureName = measures.get(dishMeasureId);

                System.out.printf("Enter %s: ", measureName);
                Integer amount = scanner.nextInt();

                Order order = new Order();
                order.setDishId(dishId);
                order.setAmount(amount);

                orders.add(order);
            } else {
                break;
            }
        }

        System.out.printf("Event: %s   \nAmount: %s  \nDate: %s\n", eventTypes.get(eventType).getName(), peopleAmount, date);

        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("You have successfully ordered! Your orders: ");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%5s %25s %25s %17s %10s %10s", "ID", "NAME", "MEASURE", "AMOUNT", "PRICE", "SUM\n");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");

        int dishesTotalPrice = 0;
        for (Order order : orders) {
            String dishName = dishIdToDish.get(order.getDishId()).getName();
            Integer measureId = dishIdToDish.get(order.getDishId()).getMeasureId();
            Integer price = dishIdToDish.get(order.getDishId()).getPrice();
            Integer sum = order.getAmount() * price;
            dishesTotalPrice += sum;


            System.out.format("%5s %25s %25s %15s %10s %10s\n",
                    order.getDishId(), dishName, measures.get(measureId), order.getAmount(), price, sum);
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
        }


        int totalSum = dishesTotalPrice + eventTypes.get(eventType).getPrice();
        System.out.printf("Total price of your orders: %d KGS\n", totalSum);
    }

    private static List<Restaurant> readAllRestaurants() throws Exception {
        List<Restaurant> restaurants = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("restaurants.csv"))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(";");
                Integer id = Integer.parseInt(parts[0]);
                String name = parts[1];
                Integer capacity = Integer.parseInt(parts[2]);
                String address = parts[3];
                String location = parts[4];
                String contact = parts[5];
                String opened = parts[6];

                Restaurant restaurant = new Restaurant();
                restaurant.setId(id);
                restaurant.setName(name);

                restaurant.setCapacity(capacity);
                restaurant.setAddress(address);
                restaurant.setLocation(location);
                restaurant.setContact(contact);
                restaurant.setOpened(opened);
                restaurants.add(restaurant);
            }
        }
        return restaurants;

    }

    private static List<Dish> getDishesByRestaurant(Integer restaurantId) throws Exception {
        List<Dish> dishes = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("dishes.csv"))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(";");

                try {
                    Integer id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    Integer measureId = Integer.parseInt(parts[2]);
                    Integer price = Integer.parseInt(parts[3]);
                    String category = parts[4];
                    Integer dishRestaurantId = Integer.parseInt(parts[5]);

                    if (dishRestaurantId.equals(restaurantId)) {
                        Dish dish = new Dish();
                        dish.setId(id);
                        dish.setName(name);
                        dish.setMeasureId(measureId);
                        dish.setPrice(price);
                        dish.setCategory(category);
                        dish.setRestaurantId(dishRestaurantId);

                        dishes.add(dish);
                    }
                } catch (Exception e) {
                    System.out.println("Error occurred while parsing" + " " + Arrays.toString(parts));
                    throw e;
                }
            }
        }
        return dishes;
    }

    private static Map<Integer, String> readMeasures() throws FileNotFoundException {
        Map<Integer, String> measures = new HashMap<>();

        try (Scanner scanner = new Scanner(new File("measures.csv"))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(";");
                Integer id = Integer.parseInt(parts[0]);
                String name = parts[1];
                measures.put(id, name);
            }
        }
        return measures;
    }

    private static Map<Integer, Event> getEventTypes() throws Exception {
        Map<Integer, Event> events = new HashMap<>();
        try (Scanner scanner = new Scanner(new File("event.csv"))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(";");
                try {
                    Integer id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    Integer price = Integer.parseInt(parts[2]);
                    Event event = new Event();
                    event.setId(id);
                    event.setName(name);
                    event.setPrice(price);

                    events.put(id, event);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        return events;
    }
}
