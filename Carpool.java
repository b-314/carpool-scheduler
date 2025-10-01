import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class Carpool {
    private String[][] schedule;
    private Driver[] drivers;

    public Carpool() {
        schedule = null;
        drivers = null;
    } //default constuctor
    
    public static ArrayList<LocalDate> createDateList(File data, Scanner sc, DateTimeFormatter formatter) throws FileNotFoundException {
        ArrayList<LocalDate> days = new ArrayList<LocalDate>();
        while(sc.hasNextLine()) {
            LocalDate day = LocalDate.parse(sc.nextLine(), formatter);
            days.add(day);
        }
        return days;
    } //creates an ArrayList of LocalDates from a text file of Strings
    
    public static LocalDate scanDay(String dayName, Scanner kb, DateTimeFormatter formatter) {
        System.out.print(dayName + " day in mm/dd/yyyy format: ");
        return LocalDate.parse(kb.nextLine(), formatter);
    } //Prompts the user for a specific day and returns the scanned string as a LocalDate 

    public int calcTotalDays(LocalDate firstDay, LocalDate lastDay) {
        return (int)ChronoUnit.DAYS.between(firstDay, lastDay) + 1;
    } //Calculates the number of days between two dates inclusive
    
    public void setSchedule(int totalDays) {
        schedule = new String[totalDays/7+1][7];
    } //Creates a schedule with 7 columns and enough rows for the number of weeks within total days

    public void setDrivers(Scanner kb) {
        System.out.print("\nNumber of drivers in your carpool: ");
        drivers = new Driver[kb.nextInt()];
    } //Sets the drivers array based on the number of drivers
    
    public void fillDrivers (Scanner kb) {
        kb.nextLine();
        for(int i=0; i<drivers.length; i++) {
            drivers[i] = new Driver();
            System.out.print("\nDriver "+(i+1)+" name: ");
            drivers[i].setName(kb.nextLine());
            System.out.print("Days "+drivers[i].getName()+ " prefers to drive: ");
            drivers[i].setDays(kb.nextLine().toUpperCase());
        }
    } //New driver objects are created for each driver and their name and days are set based on user input. 
    
    public boolean dayIsOff (LocalDate day, ArrayList<LocalDate> daysOff) {
        if(daysOff.size()!=0&&day.equals(daysOff.get(0))) {
            daysOff.remove(0);
            return true;
        }
        return false;
    } //Checks whether the day is included on the list of daysOff
    
    public int bestDriver (LocalDate day) {
        int bestDriver=-1;
        for(int i=0; i<drivers.length; i++) {
            if(driverHasDay(i, day)) {
                if(bestDriver==-1||drivers[i].getTurns()<drivers[bestDriver].getTurns()) {
                    bestDriver = i;
                }
            }
        }
        return bestDriver;
    } //Finds the driver who can drive the day and has the least turns
    
    public boolean driverHasDay (int index, LocalDate day) {
        if(drivers[index].getDays().indexOf(day.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toUpperCase())>-1)
            return true;
        else
            return false;
    } //Finds whether the driver can drive the day's day of the week
    
    public void fillSchedule(LocalDate currentDay, LocalDate lastDay, ArrayList<LocalDate> daysOff) {
        int currentWeek = 0;
        while(!currentDay.equals(lastDay.plusDays(1))) {
            int currentDayVal = currentDay.getDayOfWeek().getValue();
            if(currentDayVal==6||currentDayVal==7) {
                schedule[currentWeek][currentDayVal-1] = "WEEKEND";
                if(currentDayVal==7) currentWeek++;
            }
            else if(dayIsOff(currentDay, daysOff)) {
                schedule[currentWeek][currentDayVal-1] = "NO CARPOOL";
            }
            else {
                int driverIndex = bestDriver(currentDay);
                if(driverIndex==-1) {
                    schedule[currentWeek][currentDayVal-1] = "NO DRIVER";
                }
                else {
                    drivers[driverIndex].addTurn();
                    schedule[currentWeek][currentDayVal-1] = drivers[driverIndex].getName();
                    drivers[driverIndex].addTurnsList(currentDay);
                }
            }
            currentDay = currentDay.plusDays(1);
        }
    } //Assigns the days that are not weekends or days off to a driver and adds a turn to that driver's turn count. 
        
    public void printSchedule() {
        for(int i=0; i<schedule.length; i++) {
            System.out.println();
            System.out.printf("Week: %-10s", (i+1));
            for(int j=0; j<schedule[0].length;j++) {
                System.out.printf("%-20s", schedule[i][j]);
            }
        }
    } //Prints the schedule with each week on a seperate line
    
    public void printTotalTurns() {
        System.out.print("\n\n");
        for(int i=0; i<drivers.length; i++) { 
            System.out.println(drivers[i].getName() + ": " + drivers[i].getTurns());
        }
    } //Prints the total turn counts for each driver
    
    public void printIndividualSchedules(DateTimeFormatter formatter) {
        for(int i=0; i<drivers.length; i++) {
            System.out.println("\n" + drivers[i].getName());
            drivers[i].printTurnsList(formatter);
        }
    } //Prints the days that the driver has to drive for each driver
    
}