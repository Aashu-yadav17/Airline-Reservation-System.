import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/* Flight class */
class Flight {
    int id;
    String source, destination;
    int seats;

    Flight(int id, String s, String d, int seats) {
        this.id = id;
        this.source = s;
        this.destination = d;
        this.seats = seats;
    }
}

/* Passenger class */
class Passenger {
    String name;
    int age;

    Passenger(String n, int a) {
        name = n;
        age = a;
    }
}

/* Reservation class */
class Reservation {
    static int counter = 1;
    int bookingId;
    Passenger p;
    Flight f;

    Reservation(Passenger p, Flight f) {
        this.bookingId = counter++;
        this.p = p;
        this.f = f;
    }
}

/* MAIN CLASS */
public class AirlineReservationSystem extends JFrame {

    ArrayList<Flight> flights = new ArrayList<>();
    ArrayList<Reservation> reservations = new ArrayList<>();

    DefaultTableModel flightModel, bookingModel;
    JTable table;

    public AirlineReservationSystem() {

        setTitle("Airline Reservation System ✈️");
        setSize(750, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center window

        // AUTO-FIT BACKGROUND IMAGE
        JLabel background = new JLabel() {
            Image img = new ImageIcon("bg.PNG").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };

        background.setLayout(new BorderLayout());
        setContentPane(background);

        // Sample Flightsflights.add(new Flight(101, "Delhi", "Mumbai", 5));
     flights.add(new Flight(101, "Delhi", "Mumbai", 5));
     flights.add(new Flight(102, "Pune", "Bangalore", 4));
     flights.add(new Flight(103, "Chennai", "Kolkata", 6));
     flights.add(new Flight(104, "Hyderabad", "Delhi", 7));
     flights.add(new Flight(105, "Mumbai", "Chennai", 3));
     flights.add(new Flight(106, "Bangalore", "Kolkata", 5));
     flights.add(new Flight(107, "Delhi", "Goa", 6));
     flights.add(new Flight(108, "Pune", "Hyderabad", 4));
     flights.add(new Flight(109, "Chennai", "Bangalore", 5));
     flights.add(new Flight(110, "Kolkata", "Mumbai", 6));
     flights.add(new Flight(111, "Goa", "Delhi", 4));
     flights.add(new Flight(112, "Hyderabad", "Pune", 5));
        // Top Panel
        JPanel panel = new JPanel();
        panel.setOpaque(false);

        JButton viewBtn = new JButton("View Flights");
        JButton bookBtn = new JButton("Book Ticket");
        JButton cancelBtn = new JButton("Cancel Ticket");
        JButton viewBookingBtn = new JButton("View Bookings");

        panel.add(viewBtn);
        panel.add(bookBtn);
        panel.add(cancelBtn);
        panel.add(viewBookingBtn);

        add(panel, BorderLayout.NORTH);

        // Table
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        add(scrollPane, BorderLayout.CENTER);

        // 🔹 VIEW FLIGHTS
        viewBtn.addActionListener(e -> {
            String[] col = {"ID", "Source", "Destination", "Seats"};
            flightModel = new DefaultTableModel(col, 0);

            for (Flight f : flights) {
                flightModel.addRow(new Object[]{
                        f.id, f.source, f.destination, f.seats
                });
            }
            table.setModel(flightModel);
        });

        // 🔹 BOOK TICKET
        bookBtn.addActionListener(e -> {

            JTextField idField = new JTextField();
            JTextField nameField = new JTextField();
            JTextField ageField = new JTextField();

            Object[] msg = {
                    "Flight ID:", idField,
                    "Name:", nameField,
                    "Age:", ageField
            };

            int option = JOptionPane.showConfirmDialog(null, msg, "Book Ticket", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    String name = nameField.getText();
                    int age = Integer.parseInt(ageField.getText());

                    for (Flight f : flights) {
                        if (f.id == id && f.seats > 0) {

                            Passenger p = new Passenger(name, age);
                            Reservation r = new Reservation(p, f);

                            reservations.add(r);
                            f.seats--;

                            JOptionPane.showMessageDialog(null,
                                    "Booked Successfully!\nBooking ID: " + r.bookingId);
                            return;
                        }
                    }

                    JOptionPane.showMessageDialog(null, " No seats available! Flight is fully booked");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, " Invalid Input!");
                }
            }
        });

        // 🔹 CANCEL TICKET
        cancelBtn.addActionListener(e -> {

            String input = JOptionPane.showInputDialog("Enter Booking ID:");

            try {
                int id = Integer.parseInt(input);

                for (int i = 0; i < reservations.size(); i++) {
                    Reservation r = reservations.get(i);

                    if (r.bookingId == id) {
                        r.f.seats++;
                        reservations.remove(i);

                        JOptionPane.showMessageDialog(null, "Ticket Cancelled!");
                        return;
                    }
                }
                JOptionPane.showMessageDialog(null, "Booking ID not found!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, " Invalid Input!");
            }
        });

        // 🔹 VIEW BOOKINGS
        viewBookingBtn.addActionListener(e -> {

            String[] col = {"Booking ID", "Name", "Age", "Route"};
            bookingModel = new DefaultTableModel(col, 0);

            for (Reservation r : reservations) {
                bookingModel.addRow(new Object[]{
                        r.bookingId,
                        r.p.name,
                        r.p.age,
                        r.f.source + " -> " + r.f.destination
                });
            }
            table.setModel(bookingModel);
        });
    }
    public static void main(String[] args) {
        new AirlineReservationSystem().setVisible(true);
    }
}