package movieMagnet.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import movieMagnet.model.Privilege;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
	public Privilege findByName(String name);

	@Override
	void delete(Privilege privilege);
}
