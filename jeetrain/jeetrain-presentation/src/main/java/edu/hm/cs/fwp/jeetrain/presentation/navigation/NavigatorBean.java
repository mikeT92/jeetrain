package edu.hm.cs.fwp.jeetrain.presentation.navigation;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * {@code ManagedBean} backing views page01 and page02.
 * 
 * @author theism
 */
@Named("navigator")
@RequestScoped
public class NavigatorBean implements Serializable {

	private Data data = new Data();

	public Data getData() {
		return data;
	}

	/**
	 * Event handler called before the associated view is rendered.
	 */
	public void onPreRenderView() {
		this.data = (Data) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().remove("data");
		if (this.data == null) {
			this.data = new Data();
		}
	}

	public String apply() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("data", this.data);
		return "page02?faces-redirect=true";
	}
}
