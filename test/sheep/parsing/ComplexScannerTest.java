package sheep.parsing;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.List;

public class ComplexScannerTest {

    @Before
    public void setUp() {
    }

    /**
     * ensure scanner works when given valid input
     * @throws ParseException
     */
    @Test
    public void validInput() throws ParseException {

        String input = "1 + 2";

        List<ComplexScanner.Token> expectedTokens = List.of(
                new ComplexScanner.Token(ComplexScanner.TokenType.CONST, "1"),
                new ComplexScanner.Token(ComplexScanner.TokenType.OP, "+"),
                new ComplexScanner.Token(ComplexScanner.TokenType.CONST, "2")
        );

        List<ComplexScanner.Token> actualTokens = ComplexScanner.tokenize(input);


        assertEquals(expectedTokens, actualTokens);
    }

    /**
     * ensure it throws exception on invalid input
     * @throws ParseException
     */
    @Test(expected = ParseException.class)
    public void invalidInput() throws ParseException {
        String input = "1 + (2 * 3";
        ComplexScanner.tokenize(input);
    }

    /**
     * ensure multiple operators works
     * @throws ParseException
     */
    @Test
    public void multipleOperators() throws ParseException {
        String input = "3 * 4 - 5 / 6";
        List<ComplexScanner.Token> expectedTokens = List.of(
                new ComplexScanner.Token(ComplexScanner.TokenType.CONST, "3"),
                new ComplexScanner.Token(ComplexScanner.TokenType.OP, "*"),
                new ComplexScanner.Token(ComplexScanner.TokenType.CONST, "4"),
                new ComplexScanner.Token(ComplexScanner.TokenType.OP, "-"),
                new ComplexScanner.Token(ComplexScanner.TokenType.CONST, "5"),
                new ComplexScanner.Token(ComplexScanner.TokenType.OP, "/"),
                new ComplexScanner.Token(ComplexScanner.TokenType.CONST, "6")
        );
        List<ComplexScanner.Token> actualTokens = ComplexScanner.tokenize(input);
        assertEquals(expectedTokens, actualTokens);
    }

    /**
     * ensure args work
     * @throws ParseException
     */
    @Test
    public void functionWithArguments() throws ParseException {
        String input = "SUM(1, 2, 3)";
        List<ComplexScanner.Token> expectedTokens = List.of(
                new ComplexScanner.Token(ComplexScanner.TokenType.FUNC,
                        "SUM", "1,2,3")
        );
        List<ComplexScanner.Token> actualTokens = ComplexScanner.tokenize(input);
        assertEquals(expectedTokens, actualTokens);
    }

    /**
     * test nested functions
     * @throws ParseException
     */
    @Test
    public void nestedFunctions() throws ParseException {
        String input = "MEAN(SUM(1, 2), AVG(3, 4))";
        List<ComplexScanner.Token> expectedTokens = List.of(
                new ComplexScanner.Token(ComplexScanner.TokenType.FUNC,
                        "MEAN", "SUM(1,2),AVG(3,4)")
        );
        List<ComplexScanner.Token> actualTokens = ComplexScanner.tokenize(input);
        assertEquals(expectedTokens, actualTokens);
    }

    /**
     * test more complex functions
     */
    @Test
    public void complexMixedExpression() throws ParseException {
        String input = "SUM(1, 2) + AVG(3, 4) * MAX(5, 6)";
        List<ComplexScanner.Token> expectedTokens = List.of(
                new ComplexScanner.Token(ComplexScanner.TokenType.FUNC, "SUM", "1,2"),
                new ComplexScanner.Token(ComplexScanner.TokenType.OP, "+"),
                new ComplexScanner.Token(ComplexScanner.TokenType.FUNC, "AVG", "3,4"),
                new ComplexScanner.Token(ComplexScanner.TokenType.OP, "*"),
                new ComplexScanner.Token(ComplexScanner.TokenType.FUNC, "MAX", "5,6")
        );
        List<ComplexScanner.Token> actualTokens = ComplexScanner.tokenize(input);
        assertEquals(expectedTokens, actualTokens);
    }


    /**
     * test if input is empty
     * @throws ParseException
     */
    @Test
    public void emptyString() throws ParseException {
        String input = "";
        List<ComplexScanner.Token> expectedTokens = List.of();
        List<ComplexScanner.Token> actualTokens = ComplexScanner.tokenize(input);
        assertEquals(expectedTokens, actualTokens);
    }

    /**
     * test for chars which aren't longs
     * @throws ParseException
     */
    @Test
    public void unrecognizedChars() throws ParseException {
        String input = "1 + @";

        List<ComplexScanner.Token> expectedTokens = List.of(
                new ComplexScanner.Token(ComplexScanner.TokenType.CONST, "1"),
                new ComplexScanner.Token(ComplexScanner.TokenType.OP, "+"),
                new ComplexScanner.Token(ComplexScanner.TokenType.REFERENCE, "@")
        );
        List<ComplexScanner.Token> actualTokens = ComplexScanner.tokenize(input);
        assertEquals(expectedTokens, actualTokens);
    }


}
