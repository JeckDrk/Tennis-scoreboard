package TennisScoreboard.validators;

import TennisScoreboard.exception.InputException;

import java.util.regex.Pattern;

public class InputValidator {

    public static void namesValidator(String name1, String name2) throws InputException {
        if (name1 == null || name2 == null || name1.isEmpty() || name2.isEmpty()) {
            throw new InputException("Not correct names! Names mustn't be empty!");
        } else if (!Pattern.matches("^[\\sa-zA-Z.]{2,120}$", name1)) {
            throw new InputException("Not correct names! Names must be between 2 and 120 Latin characters!");
        } else if (name1.equals(name2)) {
            throw new InputException("Not correct names! Names mustn't are equal!");
        }
    }
}
