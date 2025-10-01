import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class Driver {
    private String name; 
    private String days;
    private int turns;
    private ArrayList<LocalDate> turnsList;
    
    public Driver() {
        name = "";
        days = "";
        turns = 0;
        turnsList = new ArrayList<LocalDate>();
    } //Default constructor
    
    public String getName() {
        return name;
    } //Gets the name
    
    public void setName(String name) {
        this.name = name; 
    } //Sets the name
    
    public String getDays() {
        return days;
    } //Gets the preferred days
    
    public void setDays(String days) {
        this.days = days;
    } //Sets the preferred days
    
    public int getTurns() {
        return turns;
    } //Gets the number of turns
    
    public void addTurn() {
        turns++;
    } //Increments the number of turns by one
    
    public void printTurnsList(DateTimeFormatter formatter) {
        for(int i=0; i<turnsList.size(); i++) {
            System.out.println(turnsList.get(i).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + ", " + formatter.format(turnsList.get(i)));
        }
    } //Prints out the driver's turn list and each day's day of the week
    
    public void addTurnsList(LocalDate day) {
        turnsList.add(day);
    } //Adds a day to the turns list
}