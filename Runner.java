import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;


public class Runner
{
    public static void main(String[] args) throws FileNotFoundException
    {
        Scanner kb = new Scanner(System.in);
        File data = new File("daysOff.txt");
        Scanner sc = new Scanner (data);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        
        Carpool carpool1 = new Carpool();
        LocalDate firstDay = carpool1.scanDay("First", kb, dateFormat);
        LocalDate lastDay = carpool1.scanDay("Last", kb, dateFormat);
        ArrayList<LocalDate> daysOff = carpool1.createDateList(data, sc, dateFormat);
        int totalDays = carpool1.calcTotalDays(firstDay, lastDay);
        carpool1.setSchedule(totalDays);
        carpool1.setDrivers(kb);
        carpool1.fillDrivers(kb);
        carpool1.fillSchedule(firstDay, lastDay, daysOff);
        carpool1.printSchedule();
        carpool1.printTotalTurns();
        carpool1.printIndividualSchedules(dateFormat);
        
        /*DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        File data = new File("daysOff.txt");
        Scanner sc = new Scanner (data);
        ArrayList<LocalDate> daysOff = new ArrayList<LocalDate>();
        while(sc.hasNextLine()) {
            LocalDate dayOff = LocalDate.parse(sc.nextLine(), format);
            daysOff.add(dayOff);
        }

        Scanner kb = new Scanner(System.in);
        System.out.print("First day in mm/dd/yyyy format: ");
        LocalDate firstDay = LocalDate.parse(kb.nextLine(), format);
        System.out.print("Last day in mm/dd/yyyy format: ");
        LocalDate lastDay = LocalDate.parse(kb.nextLine(), format);
        int totalDays = (int)ChronoUnit.DAYS.between(firstDay, lastDay) + 1;
        String[][] schedule = new String[totalDays/7+1][7];

        System.out.print("\nNumber of drivers in your carpool: ");
        Driver[] drivers = new Driver[kb.nextInt()];
        kb.nextLine();
        for(int i=1; i<=drivers.length; i++) {
            drivers[i-1] = new Driver();
            System.out.print("\nDriver "+i+" name: ");
            drivers[i-1].setName(kb.nextLine());
            System.out.print("Days "+drivers[i-1].getName()+ " can drive (only hit ENTER when finished): ");
            drivers[i-1].setDays(kb.nextLine().toUpperCase());
        }
        
        LocalDate currentDay = firstDay;
        int currentWeek = 0;
        int maxDays = totalDays/drivers.length+1;
        while(!currentDay.equals(lastDay.plusDays(1))) {
            if(daysOff.size()!=0&&currentDay.equals(daysOff.get(0))) {
                daysOff.remove(0);
                schedule[currentWeek][currentDay.getDayOfWeek().getValue()-1] = "------";
            }
            else if(currentDay.getDayOfWeek().getValue()==6||currentDay.getDayOfWeek().getValue()==7) {
                schedule[currentWeek][currentDay.getDayOfWeek().getValue()-1] = "XXXXX";
                if(currentDay.getDayOfWeek().getValue()==7) currentWeek++;
            }
            else {
                int index = -1;
                for(int i=0; i<drivers.length; i++) {
                    if(drivers[i].getDays().indexOf(currentDay.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toUpperCase())>-1) {
                        if(index==-1||drivers[i].getTurns()<drivers[index].getTurns()) {
                            index = i;
                        }
                    }
                }
                if(index==-1) {
                    schedule[currentWeek][currentDay.getDayOfWeek().getValue()-1] = "NO DRIVER";
                }
                else {
                    drivers[index].addTurn();
                    schedule[currentWeek][currentDay.getDayOfWeek().getValue()-1] = drivers[index].getName();
                }
            }
            currentDay = currentDay.plusDays(1);
        }
        
        for(String[] week:schedule) {
            for(String name:week) {
                System.out.printf("%-20s", name);
            }
            System.out.println();
        }
        
        System.out.println();
        for(int i=0; i<drivers.length; i++) { 
            System.out.println(drivers[i].getName() + ": " + drivers[i].getTurns());
        }
        */
    }
}