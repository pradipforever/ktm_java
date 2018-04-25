package com.ktm.utils.mail.controller;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktm.utils.mail.model.EmailSubscriber;
import com.ktm.utils.mail.repository.EmailSubscriberRepository;
import com.ktm.utils.mail.service.EmailConfirmationService;

@RestController
@RequestMapping("/verify_email")
public class EmailConfirmationController {
	@Autowired
	private EmailConfirmationService emailConfirmationService;
	@Autowired
	private EmailSubscriberRepository emailSubscriberRepository;

	@GetMapping("/{token}")
	@CrossOrigin(origins = "http://localhost:4200")
	public EmailConfirmationStatus confirmRegistration(@PathVariable("token") String token) {
		if (token == null || token.isEmpty())
			return EmailConfirmationStatus.INVALID_TOKEN;

		Optional<EmailSubscriber> emailSubscriber = this.emailSubscriberRepository.findByVerifyToken(token);

		if (!emailSubscriber.isPresent()) {
			// it's already confirmed, show page showing the email is already confirmed
			return EmailConfirmationStatus.ALREADY_CONFIRMED;
		}
		if (emailSubscriber.isPresent()) {
			EmailSubscriber email = emailSubscriber.get();
			if ((email.getExpirationDate().getTime() - Calendar.getInstance().getTime().getTime()) <= 0) {
				// email verification time is expired, extend exp time, re-issue token
				// show page showing the email needs to be confirmed with new link
				this.emailConfirmationService.extendExpTimeAndIssueNewToken(email);
				// send new email verification to confirm
				this.emailConfirmationService.sendReConfirmationEmail(email.getEmailAddress());
				return EmailConfirmationStatus.CONFRIMATION_DATE_EXPIRED;
			}
			// looks good, set to confirmed and null out token and expiry date, send
			// confirmation email
			// show page showing the email is confirmed
			this.emailConfirmationService.setEmailSubscriberStatusToConfirmed(email);
			this.emailConfirmationService.sendEmailStatingEmailIsVerified(email.getEmailAddress());
		}
		return EmailConfirmationStatus.CONFIRMED;
	}
}
