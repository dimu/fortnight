package dwx.tech.res.flink.kafka.timewindow.repository;

import dwx.tech.res.flink.kafka.timewindow.entity.SinkEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author dwx
 */
@Repository
public interface SinkRepository extends JpaRepository<SinkEntity, Integer>, JpaSpecificationExecutor<SinkEntity> {
}
