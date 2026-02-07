package it.dosti.justit.view.cli;

import it.dosti.justit.model.TimeSlot;

import java.time.LocalDate;
import java.util.List;

@SuppressWarnings("java:S106") // uso di System.out accettabile nella CLI
public class CBookingPageUserView extends BaseCliView {

    @Override
    public void render() {
        System.out.println("===== BOOKING PAGE (no home assistance CLI MODE)=====");
    }

    public String askDate() {
        System.out.print("Date (YYYY-MM-DD): ");
        return scanner.nextLine();
    }

    public void showAvailableSlots(LocalDate date, List<TimeSlot> slots) {
        System.out.println("Available slots for " + date + ":");
        for (TimeSlot slot : slots) {
            System.out.println("- " + slot);
        }
    }

    public String askTimeSlot() {
        System.out.print("Time slot (MORNING/AFTERNOON/EVENING): ");
        return scanner.nextLine();
    }

    public String askDescription() {
        System.out.print("Describe the problem: ");
        return scanner.nextLine();
    }

    public void showInvalidDate() {
        System.out.println("Invalid date, try again.");
    }

    public void showNoAvailableSlots(LocalDate date) {
        System.out.println("No available slots for " + date + ".");
    }

    public void showInvalidTimeSlot() {
        System.out.println("Invalid time slot, try again.");
    }
}
