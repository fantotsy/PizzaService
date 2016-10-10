package ua.fantotsy.infrastructure.utils;

public final class Utils {

    public static boolean isAllowedPercentage(int percentage) {
        return ((percentage >= 1) && (percentage <= 100));
    }

    public static double getPercentageOfNumber(double number, int percentage) {
        return (number * ((double) percentage / 100));
    }
}