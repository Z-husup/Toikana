public class Dish {
    private Integer id;
    private String name;
    private Integer measureId;
    private Integer price;
    private String category;
    private Integer restaurantId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMeasureId() {return measureId;}

    public void setMeasureId(Integer measureId){this.measureId = measureId;}

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name=" + name +
                ", numbers=" + measureId +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", restaurantId=" + restaurantId +
                '}';
    }
}
