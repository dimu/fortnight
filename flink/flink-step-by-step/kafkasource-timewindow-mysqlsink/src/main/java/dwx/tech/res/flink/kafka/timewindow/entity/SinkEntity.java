package dwx.tech.res.flink.kafka.timewindow.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author dwx
 */
@Entity
@Table(name = "count")
public class SinkEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public long id;

	@Column(name = "category")
	public String category;

	@Column(name = "item")
	public String item;

	@Column(name = "endTime")
	@
	public Date endTime;

	@Column(name = "total")
	public int total;

}
