/**
 * 
 */
package me.j360.bootnative.ssn;

import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author acogoluegnes
 *
 */
public class JdbcContactItemWriter implements ItemWriter<Contact> {
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcContactItemWriter(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemWriter#write(java.util.List)
	 */
	@Override
	public void write(List<? extends Contact> items) throws Exception {
		for(Contact item : items) {
			jdbcTemplate.update(
				"insert into contact (id,firstname,lastname,birth,ssn) values (?,?,?,?,?)",
				item.getId(),item.getFirstname(),item.getLastname(),item.getBirth(),item.getSsn()
			);
		}
	}

}
