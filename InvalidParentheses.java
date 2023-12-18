import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * This class includes solution to Invalid Parentheses problem, dedicated for Tesla take home assignment.
 * Problem Statement:
 * Given a String containing three kinds of parentheses: (), [], {}.
 * Write a function to determine if a String satisfies with the following two requirements.
 * If yes, return true, otherwise, return false;
 * requirement 1. it has to be a valid parentheses combination,
 * meaning that open brackets must be closed by the same type of brackets in the correct order.
 * requirement 2. the priority order should be {} > [] > (),
 * higher priority parentheses should not appear inside lower priority parentheses.
 */
public class InvalidParentheses {
    // More kinds of parentheses can be added in the configuration map.
    // key -> parentheses, value -> priority
    private static final Map<String, Integer> parenConfiguration =
            Map.of("()", 0, "[]", 1, "{}", 2);
    private final HashMap<Character, Integer> priorityMap = new HashMap<>();
    private final HashMap<Character, Character> parenMap = new HashMap<>();
    private final HashSet<Character> validChars = new HashSet<>();


    public InvalidParentheses() {
        for (Map.Entry<String, Integer> entry : parenConfiguration.entrySet()) {
            final String parentheses = entry.getKey();
            final Integer priority = entry.getValue();
            final char openBracket = parentheses.charAt(0);
            final char closeBracket = parentheses.charAt(1);

            priorityMap.put(openBracket, priority);
            parenMap.put(closeBracket, openBracket);
            validChars.add(openBracket);
            validChars.add(closeBracket);
        }
    }

    /**
     * Validates the string containing parentheses.
     *
     * @param parentheses String containing parentheses.
     * @return true if the string is valid based on the defined requirements, false otherwise.
     * @throws IllegalArgumentException if the input string is null, empty, or contains invalid characters.
     */
    private boolean checkValidity(String parentheses)  {
        if (parentheses == null) {
            throw new IllegalArgumentException("Null Input!");
        }
        if (parentheses.isEmpty()) {
            throw new IllegalArgumentException("Input is empty!");
        }
        //early detection
        for (final char c : parentheses.toCharArray()) {
            if (!validChars.contains(c)) {
                throw new IllegalArgumentException("Contains Invalid Characters: " + c );
            }
        }

        // contains only left parentheses
        ArrayDeque<Character> stack = new ArrayDeque<>();
        for (final char c : parentheses.toCharArray()) {
            if (priorityMap.containsKey(c)) {
                //ensure priority order
                if (!stack.isEmpty() && priorityMap.get(stack.peek()) < priorityMap.get(c)) {
                    return false;
                }
                stack.push(c);
            } else {
                if (stack.isEmpty() || stack.peek()!= parenMap.get(c)) {
                    return false;
                }
                stack.pop();
            }
        }
        return stack.isEmpty();
    }



    public static void main(String[] args) {
        final InvalidParentheses invalidParentheses = new InvalidParentheses();
        // Test cases
        //note: I didn't use JUnit for simplicity consideration.
        //in real projects we should use JUnit to automate the testing process.
        String[] testStrings = {"{}[]()", "{[}]", "{[][()]}", "[({})]", "{}[", null, "", "abc()"};
        for (String test : testStrings) {
            try {
                boolean isValid = invalidParentheses.checkValidity(test);
                System.out.println("Test String: " + test + " - " + isValid);
            } catch (IllegalArgumentException e) {
                System.out.println("Test String: " + test + " - Exception: " + e.getMessage());
            }
        }
    }
}
