package edu.missouri.cf.pdcapp.ui;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;


@Theme("pdcapp")
@Push
public class NavigationUI extends UI{
    Navigator  navigator;
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = NavigationUI.class)
	public static class Servlet extends VaadinServlet {
	}
	@Override
	protected void init(VaadinRequest request) {
		// TODO Auto-generated method stub
        getPage().setTitle("PDC Application");
        // Create a navigator to control the views
        navigator = new Navigator(this, this);
        
        // Create and register the views
        navigator.addView("", new LoginView());
        navigator.addView(MainView.NAME, new MainView());
        navigator.addView(DetailView.NAME, new DetailView());
        
        getNavigator().addViewChangeListener(new ViewChangeListener() {

			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {
				// TODO Auto-generated method stub
				boolean isLoggedIn = getSession().getAttribute("user") != null;
				boolean isLoginView = event.getNewView() instanceof LoginView;
				if(!isLoggedIn && !isLoginView) {
				    // Redirect to login view always if a user has not yet
                    // logged in
                    getNavigator().navigateTo(LoginView.NAME);
                    return false;

                } else if (isLoggedIn && isLoginView) {
                    // If someone tries to access to login view while logged in,
                    // then cancel
                    return false;
                }
				return true;
			}

			@Override
			public void afterViewChange(ViewChangeEvent event) {
				// TODO Auto-generated method stub
				
			}
        	
        });
	}
	public static NavigationUI get() {
		UI ui = UI.getCurrent();
		if (ui instanceof NavigationUI) {
			return (NavigationUI) ui;
		}
		return null;
	}
}
