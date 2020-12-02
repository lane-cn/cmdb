package cn.developbranch.sms.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
	public String putMessage(Message message) {
		//按照消息内容生成ID
		String time = FORMAT.get().format(message.getTimestamp());
		String id = String.format("%s#%s#%s", message.getFrom(), time, message.getSentTo());
		id = StringUtils.md5sum(id);
		message.setId(id);
		
		//排除重复的ID
		String sql = "SELECT `message_id` FROM `sms` WHERE `message_id`=?";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, message.getId());
		if (list.size() > 0) {
			LOG.info("exists id: {}", message.getId());
			return message.getId();
		}
		
		//再排除时间接近的重复项
		String secret = String.format("%s#%s#%s", message.getFrom(), message.getSentTo(), message.getMessage());
		secret = StringUtils.md5sum(secret);
		message.setSecret(secret);
		Date beginTime = new Date(message.getTimestamp().getTime() - 60000);
		Date endTime = new Date(message.getTimestamp().getTime() + 60000);
		String sql2 = "SELECT `message_id` FROM `sms` WHERE `sent_timestamp`>? AND `sent_timestamp`<? AND `secret`=?";
		List<Map<String, Object>> list2 = jdbcTemplate.queryForList(sql2, FORMAT.get().format(beginTime), FORMAT.get().format(endTime), message.getSecret());
		if (list2.size() > 0) {
			String s = list2.get(0).get("message_id").toString();
			LOG.info("exists message: {}", s);
			return s;
		}

		//没有重复的消息，入库
		String sql3 = "REPLACE INTO `sms` (`message_id`,`device_id`,`sent_to`,`from`,`secret`,`message`,`sent_timestamp`) VALUES (?,?,?,?,?,?,?)";
		int i = jdbcTemplate.update(sql3, 
				message.getId(), 
				message.getDeviceId(), 
				message.getSentTo(), 
				message.getFrom(), 
				message.getSecret(), 
				message.getMessage(), 
				time);
		LOG.info("insert data: {}", i);
		return id;
	}
	
}
