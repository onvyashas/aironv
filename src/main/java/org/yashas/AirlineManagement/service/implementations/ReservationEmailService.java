package org.yashas.AirlineManagement.service.implementations;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.yashas.AirlineManagement.payload.email.EmailDetails;
import org.yashas.AirlineManagement.payload.flight.FlightResponseDTO;
import org.yashas.AirlineManagement.payload.reservation.ReservationResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationEmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final FlightServiceImpl flightService;

    @Value("${spring.mail.username}")
    private String sender;

    /**
     * Sends a confirmation email to the passenger.
     * This operation is performed asynchronously.
     *
     * @param reservationDTO the reservation details
     */
    @Async
    public void sendConfirmationEmail(ReservationResponseDTO reservationDTO) {
        try {
            Context context = prepareConfirmationContext(reservationDTO);
            sendEmailWithTemplate(
                reservationDTO.getPassengerEmail(),
                "Reservation Confirmation",
                "email/reservation-confirmation",
                context
            );
        } catch (Exception e) {
            log.error("Failed to send confirmation email for reservation: {}", reservationDTO.getReservationCode(), e);
        }
    }

    /**
     * Sends a flight change notification email to the passenger.
     * This operation is performed asynchronously.
     *
     * @param reservationDTO the reservation details
     * @param newFlightId the ID of the new flight
     */
    @Async
    public void sendFlightChangeNotificationEmail(ReservationResponseDTO reservationDTO, Long newFlightId) {
        try {
            FlightResponseDTO newFlight = flightService.getFlightById(newFlightId);
            Context context = new Context();
            context.setVariable("passengerName", reservationDTO.getPassengerName());
            context.setVariable("reservationCode", reservationDTO.getReservationCode());
            context.setVariable("newFlightNumber", newFlight.getFlightNumber());
            context.setVariable("departureAirport", newFlight.getDeparture());
            context.setVariable("arrivalAirport", newFlight.getDestination());
            context.setVariable("departureDate", newFlight.getDepartureTime());

            sendEmailWithTemplate(
                reservationDTO.getPassengerEmail(),
                "Important: Flight Change Notification",
                "email/flight-change-notification",
                context
            );
        } catch (Exception e) {
            log.error("Failed to send flight change email for reservation: {}", reservationDTO.getReservationCode(), e);
        }
    }

    /**
     * Prepares the context for the confirmation email template.
     *
     * @param reservationDTO the reservation details
     * @return the context containing the variables for the email template
     */
    private Context prepareConfirmationContext(ReservationResponseDTO reservationDTO) {
        FlightResponseDTO flight = flightService.getFlightById(reservationDTO.getFlightId());
        Context context = new Context();
        context.setVariable("passengerName", reservationDTO.getPassengerName());
        context.setVariable("reservationCode", reservationDTO.getReservationCode());
        context.setVariable("flightNumber", flight.getFlightNumber());
        context.setVariable("departureAirport", flight.getDeparture());
        context.setVariable("arrivalAirport", flight.getDestination());
        context.setVariable("departureDate", flight.getDepartureTime());
        return context;
    }

    /**
     * Sends an email using the provided template and context.
     *
     * @param recipient the recipient's email address
     * @param subject the subject of the email
     * @param templateName the name of the email template
     * @param context the context containing variables for the email template
     */
    private void sendEmailWithTemplate(String recipient, String subject, String templateName, Context context) {
        try {
            String messageBody = templateEngine.process(templateName, context);
            EmailDetails emailDetails = new EmailDetails(recipient, messageBody, subject);
            sendHtmlEmail(emailDetails);
        } catch (Exception e) {
            log.error("Failed to process email template for recipient: {}", recipient, e);
        }
    }

    /**
     * Sends an HTML email to the recipient.
     *
     * @param emailDetails the email details (recipient, message body, subject)
     */
    public void sendHtmlEmail(EmailDetails emailDetails) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(sender);
            helper.setTo(emailDetails.getRecipient());
            helper.setSubject(emailDetails.getSubject());
            helper.setText(emailDetails.getMessageBody(), true);

            javaMailSender.send(mimeMessage);
            log.info("Email successfully sent to {}", emailDetails.getRecipient());
        } catch (MessagingException e) {
            log.error("Messaging error while sending email to {}: {}", emailDetails.getRecipient(), e.getMessage(), e);
        } catch (Exception e) {
            log.error("Unexpected error while sending email to {}: {}", emailDetails.getRecipient(), e.getMessage(), e);
        }
    }
}