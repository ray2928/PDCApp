package edu.missouri.cf.pdcapp.ui;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.vaadin.data.Property;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;
import com.vaadin.data.util.sqlcontainer.query.generator.OracleGenerator;
import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import edu.missouri.cf.pdcapp.dbconnect.Pools;
import edu.missouri.cf.pdcapp.document.AdvancedFileDownloader;
import edu.missouri.cf.pdcapp.document.AdvancedFileDownloader.AdvancedDownloaderListener;
import edu.missouri.cf.pdcapp.document.AdvancedFileDownloader.DownloaderEvent;
import edu.missouri.cf.pdcapp.validator.PasswordValidator;

public class LoginView extends CustomComponent implements View{
    public static final String NAME = "";
    private TextField username;
    private PasswordField password;
    private Button loginButton;
    private Button newUserButton;
    private VerticalLayout viewLayout;
    private FormLayout fields;
    private FormLayout registerFields;
    private TextField newUsername;//should be email
    private PasswordField newPassword;
    private PasswordField confirmPassword;
	private TextField firmName;
	private ComboBox firm;
	private TextField address;
	private TextField city;
	private TextField county;
	private TextField state;
	private TextField country;
	private TextField zip;
	private TextField phone;
	private Button reset;
    private Button registerButton;
    private Button loginAsVisitorButton;
    private HorizontalLayout buttonLayout;
    private HorizontalLayout registerButtonLayout;
    @SuppressWarnings("serial")
	public LoginView() {
        setSizeFull();

        username = new TextField("Username"){
        	{
                setWidth("300px");
                setInputPrompt("Your email");
                addValidator(new EmailValidator("Username must be an email address"));
        	}
        };
        password = new PasswordField("password"){
        	{
        		setWidth("300px");
                //addValidator(new PasswordValidator());
            }
        };
        
        newUsername = new TextField("User Name:") {
    		{
    			setWidth("300px");
                setRequired(true);
                setInputPrompt("Your email");
                addValidator(new EmailValidator("Username must be an email address"));
    		}
        };//should be email
        newPassword = new PasswordField("Password:") {
        	{
                setRequired(true);
    			setWidth("300px");
                addValidator(new PasswordValidator());
        	}
        };
        confirmPassword = new PasswordField("Confirm Password:") {
        	{                
        		setRequired(true);
    			setWidth("300px");
//                addValidator(new PasswordValidator());
                addValueChangeListener(new Property.ValueChangeListener() {
					@Override
					public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
						// TODO Auto-generated method stub
                        String value = (String) event.getProperty().getValue();
                        if(!value.equals(newPassword.getValue())) {
                        	Notification.show("Password do not match");
                        	confirmPassword.focus();
                        }
					}
                });
               }
        };
		firmName = new TextField("Name of Firm:") {
			{
                setRequired(true);
        		setWidth("300px");
                setInvalidAllowed(false);
    			setImmediate(true);

			}
		};
		address = new TextField("Address:") {
			{
                setRequired(true);
        		setWidth("300px");
                setInvalidAllowed(false);
    			setImmediate(true);

			}
		};
		city = new TextField("City:") {
			{
                setRequired(true);
        		setWidth("300px");
                setInvalidAllowed(false);
    			setImmediate(true);

			}
		};
		county = new TextField("County:") {
			{
                setRequired(true);
        		setWidth("300px");
                setInvalidAllowed(false);
    			setImmediate(true);

			}
		};
		state = new TextField("State:") {
			{
                setRequired(true);
        		setWidth("300px");
                setInvalidAllowed(false);
    			setImmediate(true);

			}
		};
		country = new TextField("Country:") {
			{
                setRequired(true);
        		setWidth("300px");
                setInvalidAllowed(false);
    			setImmediate(true);

			}
		};
		zip = new TextField("Zip:") {
			{
                setRequired(true);
        		setWidth("300px");
            	setImmediate(true);
    			setInvalidAllowed(false);
			}
			
		};
		phone = new TextField("Phone:") {
			{
                setRequired(true);
        		setWidth("300px");
                setInvalidAllowed(false);
    			setImmediate(true);

			}
		};
        layout();

   }
    
    @SuppressWarnings("serial")
	public void layout() {
    	viewLayout = new VerticalLayout(){
    		{
    			setWidth("100%");
    			setSpacing(true);
    			setMargin(true);
    	    	fields = new FormLayout() {
    	    		{
    	    			setMargin(true);
    	    			setSpacing(true);
    	    			setWidth("400px");
    	    			addComponent(username);
    					addComponent(password);
    	    		}
    	    	};
				registerFields = new FormLayout() {
					{
						setCaption("<h3>Create New Account:</h3>");
						setCaptionAsHtml(true);						
						setMargin(true);
    	    			setSpacing(true);
    	    			setWidth("400px");
    	    			addComponent(newUsername);
    	    			addComponent(newPassword);
    	    			addComponent(confirmPassword);
						addComponent(firmName);
						addComponent(address);
						addComponent(city);
						addComponent(county);
						addComponent(state);
						addComponent(country);
						addComponent(zip);
						addComponent(phone);
    	    			setVisible(false);
					}
				};

				
    	    	buttonLayout = new HorizontalLayout(){
					{
						setSpacing(true);
    	    			setWidth("450px");
    	    			loginButton = new Button("Login", new Button.ClickListener() {
    	    	            @Override
    	    	            public void buttonClick(ClickEvent event) {
    	    	    			String userQueryString = "select * from advertisementusers u";
    	    	    			FreeformQuery userQuery = new FreeformQuery(userQueryString, Pools.getConnectionPool(Pools.Names.PROJEX));
    	    	    			boolean userExist = false;
    	    	    			String userID = "";
    	    	    			SQLContainer container = null;
    	    	    			try {
    	    	    				container = new SQLContainer(userQuery);
    	    	    				container.addContainerFilter(new Compare.Equal("USERLOGIN", username.getConvertedValue()));
    	    	    				if(container.getItemIds().size() != 0){
    	    	    					System.out.println(container.getItem(container.firstItemId()).getItemProperty("PASSWORD").getValue().toString()+"?");
    	    	    					if(container.getItem(container.firstItemId()).getItemProperty("PASSWORD").getValue().toString().equals(password.getConvertedValue())) {
    	    	    						userExist = true;
    	    	    					} else {
    	    	    						Notification.show("Invalid Password");
    	    	            				password.focus();
    	    	    					}
    	    	    				} else {
    	    	        				Notification.show("The account does not exist, please try again");
    	    	        				username.focus();
    	    	        			}
    	    	    			} catch (SQLException e) {
    	    	    				// TODO Auto-generated catch block
    	    	    				e.printStackTrace();
    	    	    			}
    	    	    			if(userExist){
    	    	    				userID = container.getItem(container.firstItemId()).getItemProperty("ID").getValue().toString();
    	    	    				getSession().setAttribute("userID", userID);
    	    	    				getSession().setAttribute("user", username.getValue());
    	    	    				getUI().getNavigator().navigateTo(MainView.NAME);
    	    	    			}
    	    	            }
    	    	        });   
						addComponent(loginButton);
				        loginAsVisitorButton =  new Button("Login as visitor", new Button.ClickListener() {

							@Override
							public void buttonClick(ClickEvent event) {
								// TODO Auto-generated method stub
								getSession().setAttribute("userID", "0");
								getSession().setAttribute("user", "visitor");
								getUI().getNavigator().navigateTo(MainView.NAME);
							}
				        	
				        });
						addComponent(loginAsVisitorButton);
				        newUserButton = new Button("New User", new Button.ClickListener() {
							
							@Override
							public void buttonClick(ClickEvent event) {
								// TODO Auto-generated method stub
								
								registerFields.setVisible(true);
								registerButtonLayout.setVisible(true);
							}
						});
						addComponent(newUserButton);
				       
				        //addComponent(download);
						setComponentAlignment(loginButton, Alignment.TOP_CENTER);
						setComponentAlignment(loginAsVisitorButton, Alignment.TOP_CENTER);
						setComponentAlignment(newUserButton, Alignment.TOP_CENTER);
					}
				};
				
				registerButtonLayout = new HorizontalLayout() {
    				{
    					setSpacing(true);
    					setWidth("400px");

    					reset = new Button("reset", new Button.ClickListener() {
							
							@Override
							public void buttonClick(ClickEvent event) {
								// TODO Auto-generated method stub
		    	    			newUsername.setValue("");
		    	    			newPassword.setValue("");
		    	    			confirmPassword.setValue("");
		    	    			firmName.setValue("");
		    	    			address.setValue("");
		    	    			city.setValue("");
		    	    			county.setValue("");
		    	    			state.setValue("");
		    	    			country.setValue("");
		    	    			zip.setValue("");
		    	    			phone.setValue("");
							}
						});
						
    	    	        registerButton = new Button("Create New Account", new Button.ClickListener() {
    	    	            @SuppressWarnings("unchecked")
							@Override
    	    	            public void buttonClick(ClickEvent event) {
    	    	            	if(newUsername.getValue().length() == 0) {
    	    	            		Notification.show("Please enter new username/email ");
    	    	            		return;
    	    	            	}
    	    	            	String userQueryString = "select * from advertisementusers a";
    	    	    			FreeformQuery userQuery = new FreeformQuery(userQueryString, Pools.getConnectionPool(Pools.Names.PROJEX));
    	    	    			boolean userExist = true;
    	    	    			try {
    	    	    				SQLContainer container = new SQLContainer(userQuery);
    	    	    				container.addContainerFilter(new Compare.Equal("USERLOGIN", newUsername.getConvertedValue()));
    	    	    				userExist = container.getItemIds().size() != 0;
    	    	    			} catch (SQLException e) {
    	    	    				// TODO Auto-generated catch block
    	    	    				e.printStackTrace();
    	    	    			}
    	    	    			if(!userExist) {
    	    	    				TableQuery q1 = new TableQuery("advertisementusers", Pools.getConnectionPool(Pools.Names.PROJEX), new OracleGenerator());
    	    	    				SQLContainer userContainer;
									try {
										userContainer = new SQLContainer(q1);
										String lastID = String.valueOf(userContainer.getItemIds().size()+1);
	    	    	    				Object add_user = userContainer.addItem();
	    	    	    				userContainer.getContainerProperty(add_user, "ID").setValue(lastID);
	    	    	    				userContainer.getContainerProperty(add_user, "ROWSTAMP").setValue(lastID);
	    	    	    				userContainer.getContainerProperty(add_user, "USERLOGIN").setValue(newUsername.getConvertedValue());
	    	    	    				userContainer.getContainerProperty(add_user, "PASSWORD").setValue(newPassword.getConvertedValue());
	    	    	    				userContainer.getContainerProperty(add_user, "PASSWORDEXPIRATION").setValue(new oracle.sql.TIMESTAMP(new java.sql.Date(System.currentTimeMillis())));
   	      	    				 	    userContainer.getContainerProperty(add_user, "FORCEEXPIRATION").setValue(BigDecimal.ONE);
	    	      	    				userContainer.getContainerProperty(add_user, "ISACTIVE").setValue(BigDecimal.ONE);
	    	      	    				userContainer.getContainerProperty(add_user, "FIRMNAME").setValue(firmName.getConvertedValue());
	    	      	    				userContainer.getContainerProperty(add_user, "ADDRESS").setValue(address.getConvertedValue());
	    	      	    				userContainer.getContainerProperty(add_user, "CITY").setValue(city.getConvertedValue());
	    	      	    				userContainer.getContainerProperty(add_user, "COUNTY").setValue(county.getConvertedValue());
	    	      	    				userContainer.getContainerProperty(add_user, "STATE").setValue(state.getConvertedValue());
	    	      	    				userContainer.getContainerProperty(add_user, "COUNTRYCODE").setValue(country.getConvertedValue());
	    	      	    				userContainer.getContainerProperty(add_user, "POSTALCODE").setValue(zip.getConvertedValue());
	    	      	    				userContainer.getContainerProperty(add_user, "PHONENUM").setValue(phone.getConvertedValue());
	    	    	    				userContainer.commit();
	    	    	    				Notification.show("New user created");
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
		    	    	    			Notification.show("User register failed, please try again.");
									}
    	    	    				registerFields.setVisible(false);
    	    	    				registerButtonLayout.setVisible(false);
        	    	    			return;
    	    	    			}
    	    	    			Notification.show("The user name already exists, try different one");
    	    	            }
    	    	        });
    					addComponent(reset);
    					addComponent(registerButton);
    					setComponentAlignment(reset, Alignment.MIDDLE_LEFT);
    					setComponentAlignment(registerButton, Alignment.MIDDLE_RIGHT);
    	    			setVisible(false);

    				}
    			};

				addComponent(fields);
				addComponent(buttonLayout);
				addComponent(registerFields);
				addComponent(registerButtonLayout);
    	    	setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
				setComponentAlignment(buttonLayout, Alignment.MIDDLE_CENTER);
				setComponentAlignment(registerFields, Alignment.MIDDLE_CENTER);
				setComponentAlignment(registerButtonLayout, Alignment.MIDDLE_CENTER);

    	    	//setStyleName(Reindeer.LAYOUT_BLUE);
    		}
    	};
    	setCompositionRoot(viewLayout);
    }
    
    @Override
    public void enter(ViewChangeEvent event) {
    }
    	
        
    
}
