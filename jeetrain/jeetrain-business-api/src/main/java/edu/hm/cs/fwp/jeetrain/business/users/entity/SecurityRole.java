/* Role.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.business.users.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Role that can be assigned to a JEETRAIN user.
 * 
 * @author Michael Theis
 */
@Entity
@Table(name = "T_SECURITY_ROLE")
public class SecurityRole implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SecurityRole.id.generator")
	@TableGenerator(name = "SecurityRole.id.generator", table = "T_SEQUENCE", pkColumnName = "SEQUENCE_NAME", pkColumnValue = "T_SECURITY_ROLE", valueColumnName = "NEXT_VAL")
	@Column(name = "ROLE_ID")
	private long id;

	@Column(name = "ROLE_NAME")
	@NotNull
	@Size(max = 32)
	private String name;

	public SecurityRole() {
	}
	public SecurityRole(String roleName) {
		this.name = roleName;
	}

	public long getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
}