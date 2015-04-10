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
import javax.inject.Inject;
import javax.inject.Named;

import edu.hm.cs.fwp.jeetrain.business.users.boundary.UserRegistrationBean;
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
	private UserRegistrationBean boundary;

	/**
	 * Unique identifier of the user the editor currently works on. (view
	 * parameter)
	 */
	private long userId;

	/**
	 * Current user this editor is working on.
	 */
	private User user;

	/**
	 * Sets the value for view parameter userId passed to the associated view.
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getUserId() {
		return this.userId;
	}

	public User getUser() {
		return this.user;
	}

	public Roles[] getAvailableRoles() {
		return Roles.values();
	}

	public List<String> getRoles() {
		List<String> result = new ArrayList<String>();
		for (Role current : this.user.getRoles()) {
			result.add(current.getRoleName().toString());
		}
		return result;
	}

	public void setRoles(List<String> roles) {
		for (String roleName : roles) {
			this.user.getRoles().add(new Role(Roles.valueOf(roleName)));
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
		if (this.user == null) {
			if (this.userId == 0L) {
				this.user = new User();
				this.user.getRoles().add(new Role(Roles.JEETRAIN_USER));
			} else {
				this.user = this.boundary
						.retrieveUserById(this.userId);
			}
		}
	}

	public String register() {
		if (!this.boundary.isUserNameAvailable(this.user.getUserName())) {
			FacesContext.getCurrentInstance().addMessage(
					"userName",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username ["
							+ user.getUserName()
							+ "] is already used by another user.", null));
			return null;
		}
		if (!this.user.getPassword().equals(this.user.getConfirmedPassword())) {
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
		this.user = this.boundary.registerUser(this.user);
		return "confirmUser?faces-redirect=true";
	}

	public String confirm() {
		if (!this.conversation.isTransient()) {
			this.conversation.end();
		}
		return "login";
	}
}