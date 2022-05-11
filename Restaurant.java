public class Restaurant {
    private Integer id;
    private String name;
    private Integer capacity;
    private String address;
    private String location;
    private String contact;
    private String opened;

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
    public Integer getCapacity() { return capacity;}

    public void setCapacity(Integer capacity) { this.capacity = capacity;}

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getOpened() {
        return opened;
    }

    public String getAddress() {
        return address;
    }

    public String getLocation() {
        return location;
    }

    public String getContact() {
        return contact;
    }

    public void setOpened(String opened) {
        this.opened = opened;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capacity='" + capacity + '\'' +
                ",address='" + address + '\'' +
                ",location='" + location + '\'' +
                ",contact='" + contact + '\'' +
                ",opened='" + opened + '\'' +
                '}';
    }


}
