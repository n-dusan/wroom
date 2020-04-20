package xwsagent.wroomagent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xwsagent.wroomagent.domain.Stub;

@Repository
public interface StubRepository extends JpaRepository<Stub, Long> {

    public Stub findOneById(Long id);
}
