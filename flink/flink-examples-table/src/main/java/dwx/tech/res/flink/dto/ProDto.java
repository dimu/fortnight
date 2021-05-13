package dwx.tech.res.flink.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProDto {

	private String projectId;

	private String pid;

	private String uid;

	private String devName;

}
