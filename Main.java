import kfa.model.*;
import kfa.exception.*;
import kfa.service.LibrarySystem;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== SECTION A & B DEMO ===");
        Book book1 = new Book("Clean Code", "Aayush", "9780132350884", 850.00);
        Magazine mag1 = new Magazine("Tech Monthly", "9780132350885", 300.00, 42);
        DVD dvd1 = new DVD("Java Fundamentals", "9780132350886", 600.00, 120);

        LibraryItem[] items = {book1, mag1, dvd1};

        for (LibraryItem item : items) {
            System.out.println(item + " | Lending Days: " + item.getLendingPeriodDays());
        }
        System.out.println("Total Books Count: " + Book.getTotalBooks());

        System.out.println("\n=== SECTION C DEMO ===");
        LibrarySystem system = new LibrarySystem();

        // Borrow book
        processBorrow(system, book1);

        // Try borrowing book again (Triggers Exception)
        processBorrow(system, book1);

        // Return late (Triggers Overdue Exception)
        processReturn(system, book1, 5);

        // Normal return
        processReturn(system, book1, 0);

        System.out.println("\n=== SECTION D DEMO ===");
        System.out.println("Member ID (Aarav Shrestha): " + LibrarySystem.generateMemberId("Aarav Shrestha"));
        System.out.println("Member ID (Single Name 'Bishal'): " + LibrarySystem.generateMemberId("Bishal"));

        System.out.println("ISBN Valid ('9780132350884'): " + LibrarySystem.isValidIsbn("9780132350884"));
        System.out.println("ISBN Invalid ('0780132350884'): " + LibrarySystem.isValidIsbn("0780132350884"));

        System.out.println("\n" + LibrarySystem.buildCatalogueReport(items, "Clean"));
    }

    private static void processBorrow(LibrarySystem system, LibraryItem item) {
        try {
            system.borrowItem(item);
        } catch (BookNotAvailableException e) {
            System.out.println("Exception Caught: " + e.getMessage());
        } finally {
            System.out.println("Transaction processed for: " + item.getTitle());
        }
    }

    private static void processReturn(LibrarySystem system, LibraryItem item, int daysLate) {
        try {
            system.returnItem(item, daysLate);
        } catch (ItemOverdueException e) {
            System.out.println("Exception Caught: " + e.getMessage());
            item.setAvailable(true);
        } finally {
            System.out.println("Transaction processed for: " + item.getTitle());
        }
    }
}