package com.ubs.opsit.interviews;

import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;

import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Test for BerlinClockTimeConverter
 * Created by Nadia Galchonkova on 4/25/17.
 */
public class BerlinClockTimeConverterTest {

    private BerlinClockTimeConverter timeConverter = new BerlinClockTimeConverter();

    @Test
    public void topYellowLampBlinksEveryTwoSecondsOn(){
        int randomOdd = 2 * (int) (Math.random() * 29);
        String inputTime = "14:22:" + randomOdd;
        String resultTime = timeConverter.convertTime(inputTime);
        logInputOutput(inputTime, resultTime);
        assertEquals(String.format("Top yellow lamp should be yellow lighted at %d seconds", randomOdd), 'Y',
                     resultTime.charAt(0));
    }

    @Test
    public void topYellowLampBlinksEveryTwoSecondsOff() {
        int randomEven = 1 + 2 * (int) (Math.random() * 29);
        String inputTime = "00:00:" + randomEven;
        String convertedTime = timeConverter.convertTime(inputTime);
        logInputOutput(inputTime, convertedTime);
        assertEquals(String.format("Top yellow lamp should be off at %d seconds", randomEven), 'O', convertedTime.charAt(0));
    }

    @Test
    public void topTwoRowsOfLampsAreRedOrOff(){
        String inputTime = getRandomTimeString();
        String resultTime = timeConverter.convertTime(inputTime);
        logInputOutput(inputTime, resultTime);
        assertTrue("Hours rows should contain only red(R) or off(O) lamps", Pattern.matches("^[RO]*$",
                getHoursRows(resultTime)));
    }

    @Test
    public void inTheTopRowThereAre4RedLamps(){
        String inputTime = getRandomTimeString();
        String resultTime = timeConverter.convertTime(inputTime);
        logInputOutput(inputTime, resultTime);
        assertEquals("Hours rows should have 4 lamps", 4, getRow(1, resultTime).length());
    }

    @Test
    public void topHoursRowLightRedLampEvery5Hours() {
        String inputTime = "18:22:22";
        String resultTime = timeConverter.convertTime(inputTime);
        logInputOutput(inputTime, resultTime);
        assertEquals("Top hours row should light red lamp every 5 hours", "RRRO", getRow(1, resultTime));
    }

    @Test
    public void lowHoursRowLightRedLampEveryHourLeftAfter5() {
        String inputTime = "22:22:22";
        String resultTime = timeConverter.convertTime(inputTime);
        logInputOutput(inputTime, resultTime);
        assertEquals("Low hours row should light red lamp every hour left after 5 hours", "RROO", getRow(2, resultTime));
    }

    @Test
    public void theFirstMinutesRowHas11Lamps(){
        String inputTime = getRandomTimeString();
        String resultTime = timeConverter.convertTime(inputTime);
        logInputOutput(inputTime, resultTime);
        assertEquals("Top minutes row should have 11 lamps", 11, getRow(3, resultTime).length());
    }

    @Test
    public void theSecondMinutesRowHas4Lamps(){
        String inputTime = getRandomTimeString();
        String resultTime = timeConverter.convertTime(inputTime);
        logInputOutput(inputTime, resultTime);
        assertEquals("Low minutes row should have 4 lamps", 4, getRow(4, resultTime).length());
    }

    @Test
    public void topMinutesRowLightALampEvery5Minutes() {
        String inputTime = "17:11:22";
        String resultTime = timeConverter.convertTime(inputTime);
        logInputOutput(inputTime, resultTime);
        assertEquals("Top minutes row should light lamp every 5 minutes", "YYOOOOOOOOO", getRow(3, resultTime));
    }

    @Test
    public void lowsMinuteRowLightYellowLampEveryMinuteLeftAfter5() {
        String inputTime = "13:14:15";
        String resultTime = timeConverter.convertTime(inputTime);
        logInputOutput(inputTime, resultTime);
        assertEquals("Low minutes row should light a lamp every minute left after 5 minutes", "YYYY",
                getRow(4, resultTime));
    }

    @Test
    public void inTheFirstMinuteRowThe3rdThe6thAndThe9thLampsAreRed(){
        String inputTime = "17:55:22";
        String resultTime = timeConverter.convertTime(inputTime);
        logInputOutput(inputTime, resultTime);
        assertEquals("In the first minute row the 3rd, the 6th and the 9th lamps should be red(R)", "YYRYYRYYRYY",
                getRow(3, resultTime));
    }

    @Test
    public void berlinClockConverterShouldResultProperTime(){
        String inputTime = "16:35:48";
        String resultTime = timeConverter.convertTime(inputTime);
        StringBuilder expectedTime = new StringBuilder()
                .append("Y").append(BerlinClockTimeConverter.NEW_LINE)
                .append("RRRO").append(BerlinClockTimeConverter.NEW_LINE)
                .append("ROOO").append(BerlinClockTimeConverter.NEW_LINE)
                .append("YYRYYRYOOOO").append(BerlinClockTimeConverter.NEW_LINE)
                .append("OOOO");
        logInputOutput(inputTime, resultTime);
        assertEquals("Berlin Clock converter should result proper time", expectedTime.toString(), resultTime);
    }

    @Test
    public void allLampsAreOffAtMidnightAnd1second(){
        String inputTime = "00:00:01";
        String resultTime = timeConverter.convertTime(inputTime);
        StringBuilder expectedTime = new StringBuilder()
                .append("O").append(BerlinClockTimeConverter.NEW_LINE)
                .append("OOOO").append(BerlinClockTimeConverter.NEW_LINE)
                .append("OOOO").append(BerlinClockTimeConverter.NEW_LINE)
                .append("OOOOOOOOOOO").append(BerlinClockTimeConverter.NEW_LINE)
                .append("OOOO");
        logInputOutput(inputTime, resultTime);
        assertEquals("Berlin Clock converter should result proper time", expectedTime.toString(), resultTime);
    }

    @Test
    public void allLampsExceptHourOneAreOn2SecondsBeforeMidnight(){
        String inputTime = "23:59:58";
        String resultTime = timeConverter.convertTime(inputTime);
        StringBuilder expectedTime = new StringBuilder()
                .append("Y").append(BerlinClockTimeConverter.NEW_LINE)
                .append("RRRR").append(BerlinClockTimeConverter.NEW_LINE)
                .append("RRRO").append(BerlinClockTimeConverter.NEW_LINE)
                .append("YYRYYRYYRYY").append(BerlinClockTimeConverter.NEW_LINE)
                .append("YYYY");
        logInputOutput(inputTime, resultTime);
        assertEquals("Berlin Clock converter should result proper time", expectedTime.toString(), resultTime);
    }

    private String getRandomTimeString() {
        return (int)(Math.random() * 23) + ":" + (int)(Math.random() * 59) + ":" +(int) (Math.random() * 59);
    }

    private String getHoursRows(String timeString){
        return get2RowsStartingAt(1, timeString);
    }

    private String get2RowsStartingAt(int startRow, String str) {
        String[] timeStringSplitted = str.split(BerlinClockTimeConverter.NEW_LINE);
        return timeStringSplitted[startRow] + timeStringSplitted[startRow + 1];
    }

    private String getRow(int row, String str){
        return str.split(BerlinClockTimeConverter.NEW_LINE)[row];
    }

    private void logInputOutput(String inputStr, String outputStr){
        System.out.println("Input: " + inputStr);
        System.out.println("Result: " + outputStr);
    }
}