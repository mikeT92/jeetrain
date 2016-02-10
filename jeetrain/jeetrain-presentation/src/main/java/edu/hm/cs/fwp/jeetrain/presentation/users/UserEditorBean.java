/* UserEditorBean.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.presentation.users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import edu.hm.cs.fwp.jeetrain.business.users.boundary.UserRegistration;
import edu.hm.cs.fwp.jeetrain.business.users.entity.Role;
import edu.hm.cs.fwp.jeetrain.business.users.entity.Roles;
import edu.hm.cs.fwp.jeetrain.business.users.entity.User;

/**
 * Managed bean handling all user interactions related to user registration.
 * 
 * @author P534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 09.01.2011 21:18:48
 */
@Named("userEditor")
@ConversationScoped
public class UserEditorBean implements Serializable {

	private static final long serialVersionUID = -7705879532390689681L;

	@Inject
	private Conversation conversation;

	/**
	 * Boundary {@code UserRegistration} that handles the user
	 * registration process.
	 */
	@Inject
	private UserRegistration boundary;

	/**
	 * Unique identifier of the user the editor currently works on. (view
	 * parameter)
	 */
	private String userId;

	/**
	 * Current user this editor is working on.
	 */
	private User user;

	/**
	 * Kennwort.
	 */
	@NotNull
	@Size(min=8, max=16)
	private String password;
	
	/**
	 * Kennwort zur Bestätigung.
	 */
	@NotNull
	@Size(min=8, max=16)
	private String confirmedPassword;
	
	/**
	 * Alle verfügbaren Rollen, die Benutzer gewiesen werden können.
	 */
	private List<Role> availableRoles;

	/**
	 * Model für die Checkbox-Liste der zugewiesenen Rollen.
	 */
	private List<SelectItem> availableRolesModel;
	
	/**
	 * Sets the value for view parameter userId passed to the associated view.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return this.userId;
	}

	public User getUser() {
		return this.user;
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
	
	public List<Role> getAvailableRoles() {
		return this.availableRoles;
	}

	public List<Role> getAssignedRoles() {
		List<Role> result = new ArrayList<>(this.user.getRoles());
		return result;
	}

	public void setAssignedRoles(List<Role> roles) {
		for (Role current : roles) {
			this.user.getRoles().add(current);
		}
	}

	/**
	 * Starts a conversation if necessary when this managed bean has been
	 * constructed.
	 */
	@PostConstruct
	public void onPostConstruct() {
		System.out.println("userEditor.onPostConstruct");
		if (this.conversation.isTransient()) {
			this.conversation.begin();
		}
	}

	/**
	 * Handles PreRenderView component system events by either creating a new
	 * user or loading an existing user depending on the view parameter userId.
	 */
	public void onPreRenderView() {
		System.out.println("userEditor.onPreRenderView");
		if (this.availableRoles == null) {
			this.availableRoles = this.boundary.retrieveAllRoles();
		}
		if (this.user == null) {
			if (this.userId == null) {
				this.user = new User();
				this.user.getRoles().add(findRoleByName(Roles.JEETRAIN_USER.name()));
			} else {
				this.user = this.boundary
						.retrieveUserById(this.userId);
			}
		}
	}

	public String register() {
		if (!this.boundary.isUserIdAvailable(this.user.getId())) {
			FacesContext.getCurrentInstance().addMessage(
					"userName",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username ["
							+ user.getId()
							+ "] is already used by another user.", null));
			return null;
		}
		if (!this.password.equals(this.confirmedPassword)) {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							"password",
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Password and confirmed password must match.",
									null));
			return null;
		}
		if (this.user.getRoles().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(
					"roles",
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Please select at least one role.", null));
			return null;
		}
		this.boundary.registerUser(this.user, this.password, this.confirmedPassword);
		return "confirmUser?faces-redirect=true";
	}

	public String confirm() {
		if (!this.conversation.isTransient()) {
			this.conversation.end();
		}
		return "login";
	}
	
	public Role findRoleByName(String roleName) {
		Role result = null;
		for (Role current : this.availableRoles) {
			if (current.getName().equals(roleName)) {
				result = current;
				break;
			}
		}
		return result;
	}
}
