package uni.ami.magicball.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Answer {
    YES("Да"),
    NO("Нет"),
    IDK("Я не знаю");

    private final String value;

    Answer(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static List<Answer> asList() {
        return new ArrayList<>(Arrays.asList(values()));
    }
}
