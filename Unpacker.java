package unpacking;

public class Unpacker {
    /**
     * Method that unpacks given string
     * @param packedString - packed string
     * @return unpacked string
     */
    public static String unpackString(String packedString) {
        StringBuilder unpackedString = unpackStringRec(new StringBuilder(packedString));

        if (unpackedString == null) {
            return null;
        } else {
            return unpackedString.toString();
        }
    }

    /**
     * Recursive method that actually does all the work
     * @param packedString - packed (sub)string
     * @return - unpacked (sub)string
     */
    private static StringBuilder unpackStringRec(StringBuilder packedString) {
        StringBuilder unpackedString = new StringBuilder();

        int l = packedString.length();
        int i = 0;
        StringBuilder subStringNumber = new StringBuilder();
        StringBuilder packedSubstring = new StringBuilder();

        while (i < l) {
            char c = packedString.charAt(i);

            /**
             * If a letter is met, just add it to the unpacked string
             */
            if (Character.isLetter(c)) {
                i = appendSymbol(unpackedString, c, i);
            }
            /**
             * If a digit is met, read the whole number
             */
            else if (Character.isDigit(c)) {
                i = getNumber(packedString, l, subStringNumber, c, i);
                /**
                 * If we reached the end of the packed string while trying to
                 * read the number, then the input string is invalid
                 */
                if (i < 0) {
                    return null;
                }
            }
            /**
             * If an opening bracket is met, read a substring in the brackets
             * and add recursively unpacked substring to the unpacked string
             */
            else if (c == '[') {
                /**
                 * if there is a substring in brackets without a number between it,
                 * then the input string is invalid
                 */
                if (subStringNumber.length() == 0) {
                    return null;
                }

                i = getPackedSubstring(packedString, packedSubstring, l, i);
                /**
                 * If we reached the end of the packed string while trying to reach
                 * the closing bracket, then the input string is invalid
                 */
                if (i < 0) {
                    return null;
                }

                StringBuilder unpackedSubstring = unpackStringRec(packedSubstring);

                /**
                 * If we failed to unpack the substring,
                 * then the input string is invalid
                 */
                if (unpackedSubstring == null) {
                    return null;
                }

                int number = Integer.parseInt(subStringNumber.toString());
                unpackedString.append(unpackedSubstring.toString().repeat(Math.max(0, number)));

                subStringNumber = new StringBuilder();
                packedSubstring = new StringBuilder();
            }
            /**
             * If we met an unacceptable symbol,
             * then the input string is invalid
             */
            else {
                return null;
            }
        }

        return unpackedString;
    }

    /**
     * Method that appends a symbol to the unpacked (sub)string
     * @param unpackedString - unpacked (sub)string
     * @param c - symbol to be appended
     * @param i - index of the symbol to be appended in the packed string
     * @return index of the next symbol in the packed string
     */
    private static int appendSymbol(StringBuilder unpackedString, char c, int i) {
        unpackedString.append(c);
        return (i + 1);
    }

    /**
     * Method that reads a number from the packed (sub)string
     * @param packedString - packed sub(string)
     * @param packedStringLength - length of the packed (sub)string
     * @param subStringNumber - a string variable where the number will be put
     * @param c - first already read digit of the number
     * @param i - index of the first digit
     * @return index of the next symbol after the number in the packed (sub)string
     * or -1 if end of the packed (sub)string was reached (i.e. the packed
     * substring is invalid)
     */
    private static int getNumber(StringBuilder packedString, int packedStringLength, StringBuilder subStringNumber,
                                 char c, int i) {
        subStringNumber.append(c);

        while (true) {
            if (i == packedStringLength) {
                return -1;
            }

            c = packedString.charAt(++i);

            if (Character.isDigit(c)) {
                subStringNumber.append(c);
            } else {
                break;
            }
        }

        return i;
    }

    /**
     * Method that reads a substring inside brackets
     * @param packedString - packed string
     * @param packedSubstring - packed substring to be read
     * @param packedStringLength - length of the packed string
     * @param i - index of symbol of the packed substring to be read
     * @return index of the next symbol after the closing bracket in the packed string
     * or -1 if the end of the packed string is reached (i.e. there is no closing bracket,
     * the packed string is invalid)
     */
    private static int getPackedSubstring(StringBuilder packedString, StringBuilder packedSubstring,
                                          int packedStringLength, int i) {

        /**
         * Difference between number of opening brackets
         * and number of closing brackets
         *
         * We need to stop then this difference
         * becomes 0
         */
        int brackets = 1;
        char c;

        while (true) {
            if (i == packedStringLength) {
                return -1;
            }

            c = packedString.charAt(++i);

            if (c == '[') {
                ++brackets;
            } else if (c == ']') {
                --brackets;
            }

            if (brackets == 0) {
                break;
            }

            packedSubstring.append(c);
        }

        return (i + 1);
    }
}
