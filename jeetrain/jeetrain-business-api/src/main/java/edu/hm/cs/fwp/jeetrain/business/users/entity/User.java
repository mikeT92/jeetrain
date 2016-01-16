package edu.hm.cs.fwp.jeetrain.business.users.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

/**
 * User entity representing a registered user of the {@code JEETrain}
 * application.
 * 
 * @author Michael Theis
 */
@Entity
@Table(name = "T_USER")
@NamedQueries({ @NamedQuery(name = User.QUERY_ALL, query = "SELECT u FROM User u ORDER BY u.fullName"),
		@NamedQuery(name = User.COUNT_ALL, query = "SELECT COUNT(u) FROM User u"),
		@NamedQuery(name = User.QUERY_BY_NAME, query = "SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.name = :userName"),
		@NamedQuery(name = User.QUERY_BY_ID, query = "SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.id = :userId") })
public class User implements Serializable {

	private static final String QUERY_NAME_PREFIX = "edu.hm.cs.fwp.jeetrain.business.users.";

	public static final String QUERY_ALL = QUERY_NAME_PREFIX + "QUERY_ALL";

	public static final String COUNT_ALL = QUERY_NAME_PREFIX + "COUNT_ALL";

	public static final String QUERY_BY_ID = QUERY_NAME_PREFIX + "QUERY_BY_ID";

	public static final String QUERY_BY_NAME = QUERY_NAME_PREFIX + "QUERY_BY_NAME";

	private static final long serialVersionUID = -4518047765217559890L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "User.id.generator")
	@TableGenerator(name = "User.id.generator", table = "T_SEQUENCE", pkColumnName = "SEQUENCE_NAME", pkColumnValue = "T_USER", valueColumnName = "NEXT_VAL")
	@Column(name = "USER_ID")
	private long id;

	@Column(name = "USER_NAME")
	@NotNull
	@Size(min = 5, max = 16)
	private String name;

	@Column(name = "PASSWORD")
	@NotNull
	@Size(min = 8, max = 64)
	private String password;

	@Transient
	@Size(min = 8, max = 64)
	private String confirmedPassword;

	@Column(name = "FIRST_NAME")
	@NotNull
	@Size(max = 64)
	private String firstName;

	@Column(name = "LAST_NAME")
	@NotNull
	@Size(max = 64)
	private String lastName;

	@Column(name = "FULL_NAME")
	@NotNull
	@Size(max = 128)
	private String fullName;

	@Column(name = "GENDER")
	@Enumerated(EnumType.ORDINAL)
	@NotNull
	private Gender gender;

	@Column(name = "DATE_OF_BIRTH")
	@Temporal(TemporalType.DATE)
	@NotNull
	@Past
	private Date dateOfBirth;

	@Column(name = "EMAIL")
	@NotNull
	@Size(max = 64)
	private String email;

	@Column(name = "PHONE")
	@Size(max = 16)
	private String phone;

	@Column(name = "MOBILE")
	@Size(max = 16)
	private String mobile;

	@Column(name = "VERSION")
	@Version
	private int version;

	/**
	 * Zugewiesene Rollen.
	 * <p>
	 * Rollen werden über die Join-Tabelle T_USER_ROLE_ASSIGNMENT mit den Usern
	 * verknüpft. Da das Zuweisen oder Entfernen einer Rolle nur Auswirkung auf
	 * die Join-Tabelle und nicht auf die Rollen-Tabelle haben darf, lassen wir
	 * beim Mapping explizit den CascadeType weg.
	 * </p>
	 */
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "T_USER_ROLE_ASSIGNMENT", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "ROLE_ID") })
	@NotNull
	@Size(min = 1)
	private Set<Role> roles = new HashSet<Role>();

	public long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmedPassword() {
		return confirmedPassword;
	}

	public void setConfirmedPassword(String confirmedPassword) {
		this.confirmedPassword = confirmedPassword;
	}

	public Collection<Role> getRoles() {
		return this.roles;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		User other = (User) obj;
		if (id != other.id) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(getClass().getSimpleName()).append(" {");
		result.append(" id:").append(getId());
		result.append(", name: \"").append(getId()).append("\"");
		result.append(" }");
		return result.toString();
	}
}
