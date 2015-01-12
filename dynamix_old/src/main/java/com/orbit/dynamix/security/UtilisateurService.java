package com.orbit.dynamix.security;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService extends JdbcDaoImpl {

	public UtilisateurService() {
		super();
		setUsersByUsernameQuery("select u.username, u.user_id, u.password, u.active, u.contact_name, p.profile_name from user u, profile p where p.profile_id = u.profile_id and username=?");
		setAuthoritiesByUsernameQuery("select u.username, p.profile_name from user u, profile p where u.username =? and p.profile_id = u.profile_id ");

	}

	@Override
	public List<UserDetails> loadUsersByUsername(String username) {
		return getJdbcTemplate().query(getUsersByUsernameQuery(), new String[] { username }, new RowMapper<UserDetails>() {

			public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
				String login = rs.getString(1);
				int id = rs.getInt(2);
				String password = rs.getString(3);
				boolean enabled = rs.getBoolean(4);
				String nom = rs.getString(5);
				String profile = rs.getString(6);
				return new Utilisateur(id, login, password, enabled, nom, profile, true, true, true, AuthorityUtils.NO_AUTHORITIES);
			}
		});
	}

	@Autowired
	protected void setDataSrc(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	protected UserDetails createUserDetails(String username, UserDetails userFromUserQuery, List<GrantedAuthority> combinedAuthorities) {
		String returnUsername = userFromUserQuery.getUsername();
		Utilisateur userDetail = (Utilisateur) userFromUserQuery;

		if (!isUsernameBasedPrimaryKey()) {
			returnUsername = username;
		}

		return new Utilisateur(userDetail.getId(), returnUsername, userDetail.getPassword(), userDetail.isEnabled(), userDetail.getNom(), userDetail.getProfile(), true, true, true, combinedAuthorities);

	}
}
