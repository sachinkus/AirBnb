package com.bnb.service;


import com.bnb.entity.Property;
import com.bnb.payload.BookingDto;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.stream.Stream;

@Service
public class PDFService {

    public String genertePdf(BookingDto bookingDto) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        String mailText=this.generateBookingAndPropertyDetailsMessage(bookingDto);

        contentStream.setFont(PDType1Font.HELVETICA, 12);

        contentStream.beginText();

        contentStream.newLineAtOffset(100, 700);
        for(String str:mailText.split("\n")) {
            contentStream.showText(str);
            contentStream.newLineAtOffset(0, -20);
        }
        contentStream.endText();
        contentStream.close();

        document.save("D:\\Bnb_pdf_collection"+"\\Booking "+bookingDto.getId()+".pdf");
        document.close();
        return mailText;
    }


    public String generateBookingAndPropertyDetailsMessage(BookingDto booking) {
        // Extract booking details
        String guestName = booking.getGuestName();
        String checkInDate = booking.getCheckInDate().toString();
        String checkOutDate = booking.getCheckOutDate().toString();
        String typeOfRoom = booking.getTypeOfRoom();
        int noOfGuests = booking.getNoOfGuests();
        Double totalPrice = booking.getTotalPrice();
        String userEmail = booking.getEmail();

        // Extract property and city details
        Property property = booking.getProperty();
        String propertyName = property.getName();
        String cityName = property.getCity().getName();  // Assuming Property has a City associated with it
        String countryName = property.getCountry().getName();  // Assuming Property has a Country associated with it

        // Construct the combined confirmation message
        String confirmationMessage = String.format(
                "Dear %s,\n\n" +
                        "Thank you for your booking at %s.\n" +
                        "Your reservation details are as follows:\n\n" +
                        "Room Type: %s\n" +
                        "Number of Guests: %d\n" +
                        "Check-in Date: %s\n" +
                        "Check-out Date: %s\n" +
                        "Total Price: $%.2f\n\n" +
                        "Property Details:\n" +
                        "Property Name: %s\n" +
                        "City: %s\n" +
                        "Country: %s\n\n" +
                        "A confirmation email has been sent to: %s.\n" +
                        "We look forward to welcoming you at our property!\n\n" +
                        "Best Regards,\n" +
                        "The Hotel Team",
                guestName, propertyName, typeOfRoom, noOfGuests, checkInDate, checkOutDate, totalPrice,
                propertyName, cityName, countryName, userEmail
        );

        return confirmationMessage;
    }

//    public void generatePdf(BookingDto bookingDto) throws FileNotFoundException, DocumentException {
//        Document document = new Document();
//        PdfWriter.getInstance(document, new FileOutputStream("D:\\Bnb_pdf_collection"+"\\Booking "+bookingDto.getId()+".pdf"));
//
//        document.open();
//
//        PdfPTable table = new PdfPTable(3);
//        addTableHeader(table);
//        addRows(table,bookingDto);
//        document.add(table);
//        document.close();
//
//    }
//
//    private void addTableHeader(PdfPTable table) {
//        Stream.of("Email Id ", "Check In Date ", "City")
//                .forEach(columnTitle -> {
//                    PdfPCell header = new PdfPCell();
//                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
//                    header.setBorderWidth(2);
//                    header.setPhrase(new Phrase(columnTitle));
//                    table.addCell(header);
//                });
//    }
//
//    private void addRows(PdfPTable table,BookingDto bookingDto) {
//        table.addCell(bookingDto.getEmail());
//        table.addCell(bookingDto.getCheckInDate().toString());
//        table.addCell(bookingDto.getProperty().getCity().getName());
//    }



}
