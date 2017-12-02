package movieMagnet.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import movieMagnet.dao.PrivilegeRepository;
import movieMagnet.dao.RoleRepository;
import movieMagnet.dao.TagRepository;
import movieMagnet.dao.UserRepository;
import movieMagnet.model.Privilege;
import movieMagnet.model.Role;
import movieMagnet.model.Tag;
import movieMagnet.model.User;
import movieMagnet.services.UserService;
import movieMagnet.themoviedb.TmdbApiInterface;
import movieMagnet.themoviedb.model.Genres;

@Component
public class SetupConfig {

	private boolean alreadySetup = false;

	@Autowired
	public UserService userService;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private PrivilegeRepository privRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private TagRepository tagsRepo;
	@Autowired
	public TmdbApiInterface tmdb;

	@EventListener
	public void init(ApplicationReadyEvent event) {
		if (alreadySetup) {
			return;
		}
		initUser();
		initTags();
		initUserTags();
		alreadySetup = true;
	}

	@Transactional
	private void initUser() {
		Privilege readPriv = createPrivilegeIfNotFound("READ_PRIV");
		List<Privilege> adminPrivs = Arrays.asList(readPriv);
		List<Privilege> userPrivs = Arrays.asList(readPriv);
		createRoleIfNotFound("ROLE_ADMIN", adminPrivs);
		createRoleIfNotFound("ROLE_USER", userPrivs);

		Role adminRole = roleRepo.findByName("ROLE_ADMIN");
		final User user = new User();
		user.setName("adm");
		user.setLastname("adm");
		user.setPassword(passwordEncoder.encode("admin"));
		user.setSecret(passwordEncoder.encode("admin"));
		user.setEmail("admin@admin.com");
		user.setRoles(Arrays.asList(adminRole));
		user.setEnabled(true);
		userRepo.save(user);
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

	@Transactional
	private Privilege createPrivilegeIfNotFound(String name) {
		Privilege priv = privRepo.findByName(name);
		if (priv == null) {
			priv = new Privilege();
			priv.setName(name);
			privRepo.save(priv);
		}
		return priv;
	}

	@Transactional
	private void initUserTags() {
		User user = userService.getUserByEmail("admin@admin.com");
		List<Tag> tagslist = new ArrayList<Tag>();
		tagslist.add(tagsRepo.findByName("Adventure"));
		tagslist.add(tagsRepo.findByName("Animation"));
		tagslist.add(tagsRepo.findByName("Music"));
		userService.addTagsToUser(user, tagslist);
	}

	@Transactional
	private void initTags() {
		Genres genres = tmdb.getGenresList();
		System.out.println(genres.toString());
		for (movieMagnet.themoviedb.model.Tag tag : genres.getTags()) {
			Tag modelTag = new Tag();
			modelTag.setName(tag.getName());
			modelTag.setTagId(tag.getId());
			tagsRepo.save(modelTag);
		}
	}
}
