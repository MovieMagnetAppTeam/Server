package movieMagnet.config;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import movieMagnet.dao.PrivilegeRepository;
import movieMagnet.dao.RoleRepository;
import movieMagnet.dao.UserRepository;
import movieMagnet.model.Privilege;
import movieMagnet.model.Role;
import movieMagnet.model.User;

@Component
public class SetupConfig implements ApplicationListener<ContextRefreshedEvent> {

	private boolean alreadySetup = false;

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private PrivilegeRepository privRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		if (alreadySetup) {
			return;
		}

		Privilege readPriv = createPriviliegeIfNotFound("READ_PRIV");
		List<Privilege> adminPrivs = Arrays.asList(readPriv);
		List<Privilege> userPrivs = Arrays.asList(readPriv);
		createRoleIfNotFound("ROLE_ADMIN", adminPrivs);
		createRoleIfNotFound("ROLE_USER", userPrivs);

		Role adminRole = roleRepo.findByName("ROLE_ADMIN");
		final User user = new User();
		user.setName("adm");
		user.setLastname("adm");
		user.setPassword(passwordEncoder.encode("admin"));
		user.setEmail("admin@admin.com");
		user.setRoles(Arrays.asList(adminRole));
		user.setEnabled(true);
		userRepo.save(user);

		alreadySetup = true;
	}

	@Transactional
	private Role createRoleIfNotFound(String name, List<Privilege> userPrivs) {
		Role role = roleRepo.findByName(name);
		if (role == null) {
			role = new Role(name);
			role.setPrivileges(userPrivs);
			roleRepo.save(role);
		}
		return role;
	}

	private Privilege createPriviliegeIfNotFound(String name) {
		Privilege priv = privRepo.findByName(name);
		if (priv == null) {
			priv = new Privilege();
			priv.setName(name);
			privRepo.save(priv);
		}
		return priv;
	}

}
