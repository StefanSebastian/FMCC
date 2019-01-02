package com.mfcc.beer_shop.model;

/**
 * @author stefansebii@gmail.com
 */
public class Beer {
    private long id;
    private String name;
    private String style;
    private String description;
    private String producer;

    public Beer() {}

    public Beer(long id, String name, String style, String description, String producer) {
        this.id = id;
        this.name = name;
        this.style = style;
        this.description = description;
        this.producer = producer;
    }

    public Beer(String name, String style, String description, String producer) {
        this.name = name;
        this.style = style;
        this.description = description;
        this.producer = producer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Beer beer = (Beer) o;

        if (id != beer.id) return false;
        if (name != null ? !name.equals(beer.name) : beer.name != null) return false;
        if (style != null ? !style.equals(beer.style) : beer.style != null) return false;
        if (description != null ? !description.equals(beer.description) : beer.description != null) return false;
        return producer != null ? producer.equals(beer.producer) : beer.producer == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (style != null ? style.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (producer != null ? producer.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Beer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", style='" + style + '\'' +
                ", description='" + description + '\'' +
                ", producer='" + producer + '\'' +
                '}';
    }
}
