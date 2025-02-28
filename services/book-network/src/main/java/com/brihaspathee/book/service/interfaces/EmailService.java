package com.brihaspathee.book.service.interfaces;

import com.brihaspathee.book.constants.EmailTemplateName;
import jakarta.mail.MessagingException;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 2/22/25
 * Time: 10:13 AM
 * Project: book-social-network
 * Package Name: com.brihaspathee.book.service.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface EmailService {
    void sendEmail(String to,
                   String username,
                   EmailTemplateName emailTemplate,
                   String confirmationUrl,
                   String activationCode,
                   String subject) throws MessagingException;
}
