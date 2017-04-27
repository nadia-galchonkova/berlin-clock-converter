package com.ubs.opsit.interviews;


import java.util.function.Predicate;

/**
 * Implementation of TimeConverter for Berlin Clock
 * Created by Nadia Galchonkova on 4/25/17.
 */
public class BerlinClockTimeConverter implements TimeConverter {

    protected static final String NEW_LINE = System.lineSeparator();
    private static final char YELLOW = 'Y';
    private static final char RED = 'R';
    private static final char OFF = 'O';

    @Override
    public String convertTime(String timeString) {
        String [] time = timeString.split(":");
        int hours = Integer.valueOf(time[0]);
        int minutes = Integer.valueOf(time[1]);
        int seconds = Integer.valueOf(time[2]);

        StringBuilder timeBuilder = new StringBuilder();
        timeBuilder.append(convertSeconds(seconds)).append(NEW_LINE)
                   .append(createTopRowHours(hours)).append(NEW_LINE)
                   .append(createLowRowHours(hours)).append(NEW_LINE)
                   .append(createTopRowMinutes(minutes)).append(NEW_LINE)
                   .append(createLowRowMinutes(minutes));
        return timeBuilder.toString();
    }

    private char convertSeconds(int seconds) {
        return seconds % 2 == 0 ? YELLOW : OFF;
    }

    private char[] createTopRowMinutes(int minutes) {
        Predicate<Integer> switchEvery5Minutes = lampNum -> minutes >= 5 * (lampNum + 1);
        char[] lamps = lightTheLamps(new char[11], switchEvery5Minutes, YELLOW);
        repaintQuarterLampsRed(lamps);
        return lamps;
    }

    private char[] createLowRowMinutes(int minutes) {
        Predicate<Integer> switchEveryMinuteAfter5 = i -> minutes % 5 > i;
        return lightTheLamps(new char[4], switchEveryMinuteAfter5, YELLOW);
    }

    private char[] createTopRowHours(int hours) {
        Predicate<Integer> switchOnEvery5Hours = i -> hours >= 5 * (i + 1);
        return lightTheLamps(new char[4], switchOnEvery5Hours, RED);
    }

    private char[] createLowRowHours(int hours) {
        Predicate<Integer> switchEveryHourLeftAfter5 = i -> hours % 5 > i;
        return lightTheLamps(new char[4], switchEveryHourLeftAfter5, RED);
    }

    private char[] lightTheLamps(char[] lamps, Predicate<Integer> conditionToBeOn, char lightOn) {
        for (int i = 0; i < lamps.length; i++) {
            if (conditionToBeOn.test(i)) {
                lamps[i] = lightOn;
            } else {
                lamps[i] = OFF;
            }
        }
        return lamps;
    }

    private void repaintQuarterLampsRed(char[] lamps) {
        for (int i = 2; i < 9; i += 3) {
            if (lamps[i] == 'Y') {
                lamps[i] = 'R';
            }
        }
    }

}


