package movieMagnet.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import movieMagnet.model.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
	public Privilege findByName(String name);

	@Override
	void delete(Privilege privilege);
}
