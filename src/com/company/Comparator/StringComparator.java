package com.company.Comparator;

public class StringComparator implements ComparatorInterface<String> {
    @Override
    public int Compare(String first, String second) {
        int firstLength = first.length();
        int secondLength = second.length();
        int minLength = Math.min(firstLength, secondLength);

        var comparing = 0;
        int i = 0;

        while (comparing == 0 && i < minLength) {
            comparing = (int) Math.signum(Character.compare(first.charAt(i), second.charAt(i)));
            i++;
        }

        if (comparing == 0)
            return (int) Math.signum(firstLength - secondLength);

        return comparing;
    }
}
