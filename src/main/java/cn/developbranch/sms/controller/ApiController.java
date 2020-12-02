package cn.developbranch.sms.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.developbranch.sms.domain.Message;
import cn.developbranch.sms.service.MessageService;

@RestController
@RequestMapping("/api")
public class ApiController {
	private static final Logger LOG = LoggerFactory.getLogger(ApiController.class);
	
	@Autowired
	private MessageService messageService;
	
	@PostMapping("/sms")
	public Object postMessage(@RequestParam("device_id") String deviceId, 
			@RequestParam("sent_to") String sentTo, 
			@RequestParam("from") String from, 
			@RequestParam("message_id") String messageId, 
			@RequestParam("secret") String secret, 
			@RequestParam("message") String message, 
			@RequestParam("sent_timestamp") Long sentTimestamp) {
		
		LOG.info("post sms, deviceId: {}, sentTo: {}, from: {}, messageId: {}, secret: {}, message: {}, sentTimestamp: {}", 
				deviceId, sentTo, from, messageId, secret, message, sentTimestamp);

		Message msg = new Message();
		msg.setDeviceId(deviceId);
		msg.setFrom(from);
		msg.setId(messageId);
		msg.setMessage(message);
		msg.setSecret(secret);
		msg.setSentTo(sentTo);
		msg.setTimestamp(new Date(sentTimestamp));
		
		messageService.putMessage(msg);
		return messageId;
	}
}
