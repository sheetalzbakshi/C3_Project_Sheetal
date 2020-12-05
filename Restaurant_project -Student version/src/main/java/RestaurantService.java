import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantService {
    private static List<Restaurant> restaurants = new ArrayList<>();

    public Restaurant findRestaurantByName(String restaurantName) throws restaurantNotFoundException {
        if (restaurants == null || restaurantName == null || restaurantName.length() == 0)
            throw new restaurantNotFoundException(restaurantName);
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName() != null && restaurant.getName() == restaurantName)
                return restaurant;
        }
        throw new restaurantNotFoundException(restaurantName);
    }


    public Restaurant addRestaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        Restaurant newRestaurant = new Restaurant(name, location, openingTime, closingTime);
        restaurants.add(newRestaurant);
        return newRestaurant;
    }

    public Restaurant removeRestaurant(String restaurantName) throws restaurantNotFoundException {
        Restaurant restaurantToBeRemoved = findRestaurantByName(restaurantName);
        if (restaurantToBeRemoved == null)
            throw new restaurantNotFoundException(restaurantName);
        restaurants.remove(restaurantToBeRemoved);
        return restaurantToBeRemoved;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public int GetOrderPrice(String restaurantName, List<String> items) throws restaurantNotFoundException, IllegalArgumentException {

        Restaurant restaurant = findRestaurantByName(restaurantName);

        if (items == null)
            throw new IllegalArgumentException();
        int total = 0;

        List<Item> desiredItems = restaurant.getMenu().stream().filter(item -> items.stream().anyMatch(k -> k == item.getName())).collect(Collectors.toList());

        if (desiredItems != null) {
            for (Item item : desiredItems)
                total += item.getPrice();
        }
        return total;
    }
}
