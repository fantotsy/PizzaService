package ua.fantotsy.infrastructure.utils;

public final class Utils {

    public static double getPercentageOfNumber(double number, int percentage) {
        return (number * ((double) percentage / 100));
    }
}