package com.nerdyjokes;

import java.util.List;
import java.util.Objects;

public class JokeResponse {

    private String type;
    private Value value;

    public JokeResponse() {
        type = null;
        value = null;
    }

    public JokeResponse(final String type, final Value value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public Value getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final JokeResponse jokeResponse = (JokeResponse) o;
        return Objects.equals(getType(), jokeResponse.getType()) &&
                Objects.equals(getValue(), jokeResponse.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getValue());
    }

    @Override
    public String toString() {
        return "JokeResponse{" +
                "type='" + type + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public static class Value {

        private int id;
        private String joke;
        private List<String> categories;

        public Value() {
            id = 0;
            joke = null;
            categories = null;
        }

        public Value(final int id, final String joke, final List<String> categories) {
            this.id = id;
            this.joke = joke;
            this.categories = categories;
        }

        public int getId() {
            return id;
        }

        public String getJoke() {
            return joke;
        }

        public List<String> getCategories() {
            return categories;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Value value = (Value) o;
            return getId() == value.getId() &&
                    Objects.equals(getJoke(), value.getJoke()) &&
                    Objects.equals(getCategories(), value.getCategories());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getJoke(), getCategories());
        }

        @Override
        public String toString() {
            return "Value{" +
                    "id=" + id +
                    ", joke='" + joke + '\'' +
                    ", categories=" + categories +
                    '}';
        }
    }
}
