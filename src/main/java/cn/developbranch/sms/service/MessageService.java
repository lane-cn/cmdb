package cn.developbranch.sms.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.developbranch.sms.domain.Message;
import cn.developbranch.sms.util.StringUtils;

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
	
	public String putMessage(Message message) {
		String time = FORMAT.get().format(message.getTimestamp());
		String id = String.format("%s#%s#%s", message.getFrom(), time, message.getSentTo());
		id = StringUtils.md5sum(id);
		
		String sql = "REPLACE INTO `sms` (`message_id`,`device_id`,`sent_to`,`from`,`secret`,`message`,`sent_timestamp`) VALUES (?,?,?,?,?,?,?)";
		int i = jdbcTemplate.update(sql, 
				id, 
				message.getDeviceId(), 
				message.getSentTo(), 
				message.getFrom(), 
				message.getSecret(), 
				message.getMessage(), 
				time);
		LOG.debug("insert data: {}", i);
		return id;
	}
	
}
