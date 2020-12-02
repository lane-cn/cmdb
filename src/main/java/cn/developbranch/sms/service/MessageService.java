package cn.developbranch.sms.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.developbranch.sms.domain.Message;

@Service
public class MessageService {
	private static final Logger LOG = LoggerFactory.getLogger(MessageService.class);
	private static final ThreadLocal<DateFormat> FORMAT = new ThreadLocal<DateFormat>() {
		@Override
		public DateFormat get() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void putMessage(Message message) {
		String sql = "REPLACE INTO `sms` (`message_id`,`device_id`,`sent_to`,`from`,`secret`,`message`,`sent_timestamp`) VALUES (?,?,?,?,?,?,?)";
		int i = jdbcTemplate.update(sql, 
				message.getId(), 
				message.getDeviceId(), 
				message.getSentTo(), 
				message.getFrom(), 
				message.getSecret(), 
				message.getMessage(), 
				FORMAT.get().format(message.getTimestamp()));
		LOG.debug("insert data: {}", i);
	}
}
