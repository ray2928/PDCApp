package com.cf.ui;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;

import com.cf.dbconnect.Pools;
import com.cf.document.AdvancedFileDownloader;
import com.cf.document.AdvancedFileDownloader.AdvancedDownloaderListener;
import com.cf.document.AdvancedFileDownloader.DownloaderEvent;
import com.ui.converter.BigDecimalToBooleanConverter;
import com.ui.converter.OracleCurrencyToStringConverter;
import com.ui.converter.OracleTimestampToDateConverter;
import com.ui.converter.OracleTimestampToStringConverter;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;
import com.vaadin.data.util.sqlcontainer.query.generator.OracleGenerator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class DetailView extends CustomComponent implements View{
	static final String NAME = "Detailview";
	private String advertisementID;
	private Item advertisementItem;
	private FieldGroup binder;
	private SQLContainer advInfoContainer;
	private SQLContainer advBreakdownContainer;
	private SQLContainer approvalProcessContainer;
	@PropertyId("REQUESTTYPE")
	private TextField advertisementType;
	@PropertyId("ADVERTISEMENT")
	private TextArea publicAdvText;
//	@PropertyId("DOCUMENTSDUE")
//	private PopupDateField documentsDue ;
	@PropertyId("SUBMISSIONDEADLINE")
	private PopupDateField submissionDeadline;
	@PropertyId("SUBMISSIONLOCATION")
	private TextField submissionLocation;
//	@PropertyId("MAXBUDGET")
//	private TextField estMaxBudget;
	@PropertyId("CONTACTNAME")
	private TextField generalContact;
	@PropertyId("CONTACTWORKPHONE")
	private TextField generalContactPhone;
	@PropertyId("CONTACTEMAILADDRESS")
	private TextField generalContactEmail;
	@PropertyId("SUBMISSIONINSTRUCTIONS")
	private TextArea submissionInstruction;
//	@PropertyId("HARDCOPYONLY")
//	private CheckBox hardCopyOnly;
	@PropertyId("DIVERSITYGOALSTATEMENT")
	private TextField supplierDiversityGoalStatement;
	@PropertyId("PRESUBMISSIONMEETING")
	private PopupDateField preSubmissionMeeting;
	@PropertyId("PRESUBMISSIONMEETINGLOCATION")
	private TextField preSubmissionMeetingLocation;
	@PropertyId("PRESUBMISSIONMEETINGREQUIRED")
	private CheckBox preSubmissionMeetingRequired;
	@PropertyId("IDCHECKREQUIRED")
	private CheckBox idCheckRequired;
	@PropertyId("PRESUBMISSIONINSTRUCTIONS")
	private TextField preSubmissionInstructions;
	@PropertyId("WALKTHROUGH")
	private PopupDateField walkThrough;
	@PropertyId("WALKTHROUGHLOCATION")
	private TextField walkThroughLocation;
	@PropertyId("WALKTHROUGHCONTACTNAME")
	private TextField walkThroughContact;
	@PropertyId("WALKTHROUGHWORKPHONE")
	private TextField walkThroughContactPhone;
	@PropertyId("WALKTHROUGHEMAILADDRESS")
	private TextField walkThroughContactEmail;
	@PropertyId("WALKTHROUGHINSTRUCTIONS")
	private TextArea walkThroughInstructions;
	private Label onlyhardcopy;
	private TabSheet tabs;
	private VerticalLayout viewLayout;
	private Grid advBreakDownGrid;
	// plan holder form
	private TextField email;
	@PropertyId("FIRMNAME")
	private TextField firmName;
	@PropertyId("USERLOGIN")
	private TextField name;
	@PropertyId("ADDRESS")
	private TextField address;
	@PropertyId("CITY")
	private TextField city;
	@PropertyId("STATE")
	private TextField state;
	@PropertyId("COUNTY")
	private TextField county;
	@PropertyId("COUNTRYCODE")
	private TextField country;
	@PropertyId("POSTALCODE")
	private TextField zip;
	@PropertyId("PHONENUM")
	private TextField phone;
//	private TextField fax;
//	private CheckBox generalContractor;
//	private CheckBox MBE;
//	private CheckBox WBE;
	private FormLayout planHolderForm;
	private Button reset;
	private Button submit;
	private HorizontalLayout buttonLayout;
	private Grid planHolders;
	private SQLContainer planHoldersContainer;
	private FieldGroup userInfoBinder;
	private SQLContainer userInfoContainer;
	private Item userInfoItem;
	String userID = "";
	private Label welcomeLabel;
	private Button logoutButton;
	//provided documents
	private Grid plansAndSpecifications;
	private Grid addenda;
	private Grid referenceDocuments;
	private Grid sealedDocuments;
	private SQLContainer providedDocumentsContainer;
	private SQLContainer plansAndSpecificationsContainer;
	private SQLContainer addendaContainer;
	private SQLContainer referenceDocumentsContainer;
	private SQLContainer sealedDocumentsContainer;
    private Button providedDocumentDownloadButton;
    private Button plansDownloadButton;
    private Button addendaDownloadButton;
    private Button referenceDocumentDownloadButton;
    private Button sealedDocumentsDownloadButton;
    private String downloadPath = "";
    
	public DetailView() {
		welcomeLabel = new Label() {
			
		};
		onlyhardcopy = new Label() {
			{
				setValue("ATTENTION: This project does not have a refundable deposit for"
						+ " plans and specs. The files below may be downloaded and printed if desired. "
						+ "All printing costs are the responsibility of the user.");
			}
		};
		logoutButton = new Button("logout",new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
            	getUI().getPage().setLocation("");
            	getUI().getSession().close();
            }
        });
		
		advertisementType = new TextField("Advertisement Type"){
			{
				setNullRepresentation("");
			}
		};
		publicAdvText = new TextArea("Public Advertisement Text"){
			{
				setNullRepresentation("");
				setWidth("100%");
			}
		};
//		documentsDue = new PopupDateField("Document Due"){
//			{
//				setConverter(new OracleTimestampToDateConverter());
//				setResolution(Resolution.SECOND);
//			}
//		};
		submissionDeadline = new PopupDateField("Submission Deadline"){
			{
				setConverter(new OracleTimestampToDateConverter());
				setResolution(Resolution.SECOND);
			}
		};
		submissionLocation = new TextField("Submission Location"){
			{
				setWidth("100%");
				setNullRepresentation("");

			}
		};
//		estMaxBudget = new TextField("Estimated Maximum Budget"){
//			{
//				setNullRepresentation("$0.00");
//				setConverter(new OracleCurrencyToStringConverter());
//			}
//		};
		generalContact = new TextField("General Contacts"){
			{
				setNullRepresentation("");
			}
		};
		generalContactPhone = new TextField("General Contacts Phone"){
			{
				setNullRepresentation("");
			}
		};
		generalContactEmail = new TextField("General Contacts Email"){
			{
				setNullRepresentation("");
			}
		};
		submissionInstruction = new TextArea("Submission Instructions") {
			{
				setNullRepresentation("");
				setWidth("100%");
			}
		};
//		hardCopyOnly = new CheckBox("Hard Copy Only?") {
//			{
//				setImmediate(true);
//    			setConverter(new BigDecimalToBooleanConverter());
//			}
//		};
		supplierDiversityGoalStatement = new TextField("Supplier Diversity Goal Statement"){
			{
				setWidth("100%");
				setNullRepresentation("");

			}
		};
		preSubmissionMeeting = new PopupDateField("Presubmission Meeting") {
			{
				setConverter(new OracleTimestampToDateConverter());
				setResolution(Resolution.SECOND);
			}
		};
		preSubmissionMeetingLocation = new TextField("Presubmission Meeting Location"){
			{
				setNullRepresentation("");
				setWidth("100%");
			}
		};
		
		preSubmissionMeetingRequired = new CheckBox("Presubmission Meeting Required") {
			{
				setImmediate(true);
    			setConverter(new BigDecimalToBooleanConverter());
			}
		};
		idCheckRequired = new CheckBox("ID Check Required"){
			{
				setImmediate(true);
    			setConverter(new BigDecimalToBooleanConverter());
			}
		};
		preSubmissionInstructions = new TextField("Presubmission Instructionss") {
			{
				setWidth("100%");
				setNullRepresentation("");

			}
		};
		walkThrough = new PopupDateField("Walk Through") {
			{
				setConverter(new OracleTimestampToDateConverter());
				setResolution(Resolution.SECOND);
			}
		};
		walkThroughLocation = new TextField("Walk Through Location") {
			{
				setNullRepresentation("");
				setWidth("100%");
			}
		};
		walkThroughContact = new TextField("Walk Through Contact") {
			{
				setNullRepresentation("");

			}
		};
		walkThroughContactPhone = new TextField("Walk Through Contact Phone") {
			{
				setNullRepresentation("");

			}
		};
		walkThroughContactEmail = new TextField("Walk Through Contact Email") {
			{
				setNullRepresentation("");

			}
		};
		walkThroughInstructions = new TextArea("Walk Through Instructions") {
			{
				setWidth("100%");
				setNullRepresentation("");
			}
		};	
		advBreakDownGrid = new Grid() {
			{
				setCaption("Advertisement Breakdown");
				setWidth("100%");
			}
		};
		
		email = new TextField("E-mail:") {
			{
        		setWidth("300px");
        		setRequired(true);
        		addValidator(new EmailValidator("must be an email address"));
			}
		};
		firmName = new TextField("Name of Firm:") {
			{
        		setWidth("300px");
			}
		};
		name = new TextField("Name:") {
			{
        		setWidth("300px");
			}
		};
		address = new TextField("Address:") {
			{
        		setWidth("300px");
			}
		};
		city = new TextField("City:") {
			{
        		setWidth("300px");
			}
		};
		state = new TextField("State:") {
			{
        		setWidth("300px");
			}
		};
		state = new TextField("State:") {
			{
        		setWidth("300px");
			}
		};
		county = new TextField("County:") {
			{
        		setWidth("300px");
			}
		};
		country = new TextField("Country:") {
			{
        		setWidth("300px");
			}
		};
		zip = new TextField("Zip:") {
			{
        		setWidth("300px");
			}
		};
		phone = new TextField("Phone:") {
			{
        		setWidth("300px");
			}
		};
//		fax = new TextField("Fax:") {
//			{
//				setWidth("250px");
//			}
//		};
//		generalContractor = new CheckBox("General Contractor") {
//			{
//				setWidth("250px");
//			}
//		};
//		MBE = new CheckBox("MBE") {
//			
//		};
//		WBE = new CheckBox("WBE") {
//			
//		};
		
		planHolders = new Grid() {
			{
				setCaption("<h3>Current Plan Holders:</h3>");
				setCaptionAsHtml(true);
				setWidth("100%");
			}
		};
		
		plansDownloadButton = new Button("Download selected file", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Item item = plansAndSpecifications.getContainerDataSource().getItem(plansAndSpecifications.getSelectedRow());
				if(item != null) {
					//downloadPath = "/home/projex4/documents/"+item.getItemProperty("LOCATION").getValue().toString();
					downloadPath = "/home/rxpkd/projex4/documents/"+item.getItemProperty("LOCATION").getValue().toString();
				} else {
					Notification.show("Please select file");
				}
			}
		});
		addendaDownloadButton = new Button("Download selected file", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Item item = addenda.getContainerDataSource().getItem(addenda.getSelectedRow());
				if(item != null) {
					downloadPath = "/home/rxpkd/projex4/documents/"+item.getItemProperty("LOCATION").getValue().toString();
				} else {
					Notification.show("Please select file");
				}
			}
		});
		referenceDocumentDownloadButton = new Button("Download selected file", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Item item = referenceDocuments.getContainerDataSource().getItem(referenceDocuments.getSelectedRow());
				if(item != null) {
					downloadPath = "/home/rxpkd/projex4/documents/"+item.getItemProperty("LOCATION").getValue().toString();
				} else {
					Notification.show("Please select file");
				}
			}
		});
		sealedDocumentsDownloadButton = new Button("Download selected file", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Item item = sealedDocuments.getContainerDataSource().getItem(sealedDocuments.getSelectedRow());
				if(item != null) {
					downloadPath = "/home/rxpkd/projex4/documents/"+item.getItemProperty("LOCATION").getValue().toString();
				} else {
					Notification.show("Please select file");
				}
			}
		});
		setDownLoader(plansDownloadButton);
		setDownLoader(addendaDownloadButton);
		setDownLoader(referenceDocumentDownloadButton);
		setDownLoader(sealedDocumentsDownloadButton);

		layout();
	}
	
	public void layout() {
		viewLayout = new VerticalLayout() {
			{
				setWidth("100%");
				setMargin(true);
				setSpacing(true);
				addComponent(new HorizontalLayout(){
					{
						setSpacing(true);
						setWidth("100%");
						addComponent(welcomeLabel);
						HorizontalLayout buttonLayout = new HorizontalLayout(){
							{
								setSpacing(true);
								setMargin(true);
								//addComponent(viewButton);
								addComponent(logoutButton);
							}
						};
						addComponent(buttonLayout);
						setComponentAlignment(welcomeLabel, Alignment.TOP_LEFT);
						setComponentAlignment(buttonLayout, Alignment.TOP_RIGHT);
					}		
				});
				tabs = new TabSheet() {
					{
						setMargin(true);
						setSpacing(true);
						addTab(new VerticalLayout() {
							{
								setCaption("Advertisement");
								setMargin(true);
								setSpacing(true);
								setSizeFull();
								addComponent(new HorizontalLayout() {
									{
										setSpacing(true);
										addComponent(advertisementType);
//										addComponent(documentsDue);
										addComponent(submissionDeadline);
										addComponent(generalContact);
										addComponent(generalContactPhone);
										addComponent(generalContactEmail);
//										addComponent(hardCopyOnly);
									}
								});
								addComponent(new HorizontalLayout() {
									{
										setWidth("100%");
										setSpacing(true);
										addComponent(publicAdvText);
										setExpandRatio(publicAdvText, 1.0f);
									}
								});
								addComponent(new HorizontalLayout() {
									{
										setWidth("100%");
										setSpacing(true);
										addComponent(submissionLocation);
//										addComponent(estMaxBudget);
										setExpandRatio(submissionLocation, 1.0f);
									}
								});
								addComponent(submissionInstruction);
								addComponent(new HorizontalLayout() {
									{
										setWidth("100%");
										setSpacing(true);
										addComponent(supplierDiversityGoalStatement);
										setExpandRatio(supplierDiversityGoalStatement, 1.0f);
									}
								});
								addComponent(new HorizontalLayout() {
									{
										setSpacing(true);
										addComponent(preSubmissionMeeting);
										addComponent(preSubmissionMeetingRequired);
										addComponent(idCheckRequired);
									}
								});
								addComponent(preSubmissionMeetingLocation);
								addComponent(preSubmissionInstructions);
								addComponent(new HorizontalLayout() {
									{
										setSpacing(true);
										addComponent(walkThrough);
										addComponent(walkThroughContact);
										addComponent(walkThroughContactPhone);
										addComponent(walkThroughContactEmail);
									}
								});
								addComponent(walkThroughLocation);
								addComponent(walkThroughInstructions);
								addComponent(advBreakDownGrid);
							}
						});
						
						addTab(new VerticalLayout(){
							{
								setCaption("Provided Documents");
								setMargin(true);
								setSpacing(true);
								setSizeFull();
								plansAndSpecifications = new Grid("plansAndSpecifications") {
									{
										setCaption("<h3>Plans And Specifications:</h3>");
										setCaptionAsHtml(true);
										setWidth("100%");
									}
								};
								addenda = new Grid() {
									{
										setCaption("<h3>Addenda:</h3>");
										setCaptionAsHtml(true);
										setWidth("100%");
									}
								};
								referenceDocuments = new Grid() {
									{
										setCaption("<h3>Reference Documents:</h3>");
										setCaptionAsHtml(true);
										setWidth("100%");
									}
								};
								sealedDocuments = new Grid() {
									{
										setCaption("<h3>Sealed Documents</h3>");
										setCaptionAsHtml(true);
										setWidth("100%");
									}
								};
								addComponent(onlyhardcopy);
								addComponent(plansAndSpecifications);
								addComponent(plansDownloadButton);
								addComponent(addenda);
								addComponent(addendaDownloadButton);
								addComponent(referenceDocuments);
								addComponent(referenceDocumentDownloadButton);
								addComponent(sealedDocuments);
								addComponent(sealedDocumentsDownloadButton);
								setComponentAlignment(plansDownloadButton, Alignment.MIDDLE_RIGHT);
								setComponentAlignment(addendaDownloadButton, Alignment.MIDDLE_RIGHT);
								setComponentAlignment(referenceDocumentDownloadButton, Alignment.MIDDLE_RIGHT);
								setComponentAlignment(sealedDocumentsDownloadButton, Alignment.MIDDLE_RIGHT);
							}
						});			
						
						addTab(new VerticalLayout() {
							{
								setCaption("Plan Holders");
								setMargin(true);
								setSpacing(true);
								setSizeFull();
								planHolderForm = new FormLayout() {
									{
										setSpacing(true);
										setWidth("400px");
										setCaption("<h3>Plan Holder list form:</h3>");
										setCaptionAsHtml(true);
										addComponent(email);
										addComponent(firmName);
										addComponent(name);
										addComponent(address);
										addComponent(city);
										addComponent(county);
										addComponent(state);
										addComponent(country);
										addComponent(zip);
										addComponent(phone);
//										addComponent(fax);
//										addComponent(generalContractor);
//										addComponent(MBE);
//										addComponent(WBE);
									}
								};
								buttonLayout = new HorizontalLayout(){
									{
										setSpacing(true);
										setWidth("300px");
										reset = new Button("reset", new Button.ClickListener() {
											
											@Override
											public void buttonClick(ClickEvent event) {
												// TODO Auto-generated method stub
												//reset all input data do we need this?
						    	    			email.setValue("");
											}
										});
										addComponent(reset);
								        submit = new Button("submit", new Button.ClickListener() {
											
											@Override
											public void buttonClick(ClickEvent event) {
												// TODO Auto-generated method stub
												//submit request, the plan holders table should update at the same time. 
												TableQuery q1 = new TableQuery("PLANHOLDERS", Pools.getConnectionPool(Pools.Names.PROJEX), new OracleGenerator());
			    	    	    				SQLContainer pContainer;
												try {
													pContainer = new SQLContainer(q1);
													String lastID = String.valueOf(pContainer.getItemIds().size()+1);
				    	    	    				Object add_planholder = pContainer.addItem();
				    	    	    				pContainer.getContainerProperty(add_planholder, "ID").setValue(lastID);
				    	    	    				pContainer.getContainerProperty(add_planholder, "ROWSTAMP").setValue(lastID);
				    	    	    				pContainer.getContainerProperty(add_planholder, "OBJECTCLASSID").setValue(lastID);
				    	    	    				pContainer.getContainerProperty(add_planholder, "REFOBJECTID").setValue(advertisementID);
				    	    	    				pContainer.getContainerProperty(add_planholder, "UUID").setValue(lastID);
				    	    	    				pContainer.getContainerProperty(add_planholder, "EMAILADDRESS").setValue(email.getConvertedValue());
				    	    	    				pContainer.getContainerProperty(add_planholder, "FIRMNAME").setValue(firmName.getConvertedValue());
				    	    	    				pContainer.getContainerProperty(add_planholder, "ADDRESS").setValue(address.getConvertedValue());
				    	    	    				pContainer.getContainerProperty(add_planholder, "CITY").setValue(city.getConvertedValue());
				    	    	    				pContainer.getContainerProperty(add_planholder, "COUNTY").setValue(county.getConvertedValue());
				    	    	    				pContainer.getContainerProperty(add_planholder, "STATE").setValue(state.getConvertedValue());
				    	    	    				pContainer.getContainerProperty(add_planholder, "COUNTRYCODE").setValue(country.getConvertedValue());
				    	    	    				pContainer.getContainerProperty(add_planholder, "POSTALCODE").setValue(zip.getConvertedValue());
				    	    	    				pContainer.getContainerProperty(add_planholder, "PHONENUM").setValue(phone.getConvertedValue());
				    	    	    				pContainer.getContainerProperty(add_planholder, "CREATED").setValue(new oracle.sql.TIMESTAMP(new java.sql.Date(System.currentTimeMillis())));
				    	    	    				pContainer.getContainerProperty(add_planholder, "CREATEDBY").setValue(userID);
				    	    	    				pContainer.getContainerProperty(add_planholder, "MODIFIED").setValue(new oracle.sql.TIMESTAMP(new java.sql.Date(System.currentTimeMillis())));
				    	    	    				pContainer.getContainerProperty(add_planholder, "MODIFIEDBY").setValue(userID);
				    	    	    				pContainer.getContainerProperty(add_planholder, "STATUSED").setValue(new oracle.sql.TIMESTAMP(new java.sql.Date(System.currentTimeMillis())));
				    	    	    				pContainer.getContainerProperty(add_planholder, "STATUSEDBY").setValue(userID);
				    	    	    				pContainer.getContainerProperty(add_planholder, "STATUSID").setValue("1");
				    	    	    				pContainer.getContainerProperty(add_planholder, "DISCUSSIONSACTIVE").setValue(BigDecimal.ONE);
				    	    	    				pContainer.getContainerProperty(add_planholder, "INWORKFLOW").setValue(BigDecimal.ZERO);
				    	    	    				pContainer.commit();
				    	    	    				Notification.show("Plan Submitted");
				    	    	    				//planHoldersContainer.refresh();
				    	    	    				planHolders.clearSortOrder();
												} catch (SQLException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
					    	    	    			Notification.show("Submission failed, please try again.");
												}
											}
										});
										addComponent(submit);
										setComponentAlignment(reset, Alignment.TOP_LEFT);
										setComponentAlignment(submit, Alignment.TOP_RIGHT);
									}
								};
								addComponent(planHolderForm);
								addComponent(buttonLayout);
								addComponent(planHolders);
								setComponentAlignment(planHolderForm, Alignment.MIDDLE_CENTER);
								setComponentAlignment(buttonLayout, Alignment.MIDDLE_CENTER);
								setComponentAlignment(planHolders, Alignment.MIDDLE_CENTER);
							}
						}); 	
					}
				};
				addComponent(tabs);
				setExpandRatio(tabs, 1.0f);				
				setComponentAlignment(tabs, Alignment.MIDDLE_CENTER);
			}
		};
		setCompositionRoot(viewLayout);
	}
	
	public void setScreenData() {
		String AdvertisementQueryString = "select * from advertisementdetails ad where ad.id = '"+advertisementID+"'";
		FreeformQuery AdvertisemenQuery = new FreeformQuery(AdvertisementQueryString, Pools.getConnectionPool(Pools.Names.PROJEX));
		try {
			advInfoContainer = new SQLContainer(AdvertisemenQuery);
			advertisementItem = advInfoContainer.getItem(advInfoContainer.firstItemId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		binder = new FieldGroup(advertisementItem);
		binder.bindMemberFields(this);
		binder.setReadOnly(true);
		
		String advBreakDownQueryString = "select p.CAMPUSNAME as campus, "
										+"p.PROJECTNUMBER||' - '||p.TITLELOCATION||' - '||p.TITLE as title, "
										+"p.PUBLICDESCRIPTION as description "
										+"from ADVERTISEMENTBREAKDOWNDETAILS a "
										+"inner join ADVERTISEMENTDETAILS ad on ad.id = a.refobjectid "
										+"inner join PROJECTDETAILSV1 p on a.PCSPROJECTID = p.id "
										+"where ad.id = '"+advertisementID+"'";
//		System.out.println(advBreakDownQueryString);
//		FreeformQuery advBreakDownQuery = new FreeformQuery(advBreakDownQueryString, Pools.getConnectionPool(Pools.Names.PROJEX));
//		try {
//			advBreakdownContainer = new SQLContainer(advBreakDownQuery);
//			advBreakDownGrid.setContainerDataSource(advBreakdownContainer);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		new GridDataLoader(advBreakDownQueryString, advBreakDownGrid, advBreakdownContainer).start();;
		
		String planHolderQueryString = "select * from planholders p where p.refobjectid = '"+advertisementID+"'";
//		FreeformQuery planHolderQuery = new FreeformQuery(planHolderQueryString, Pools.getConnectionPool(Pools.Names.PROJEX));
//		try {
//			planHoldersContainer = new SQLContainer(planHolderQuery);
//			planHolders.setContainerDataSource(planHoldersContainer);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		new GridDataLoader(planHolderQueryString, planHolders, planHoldersContainer).start();;

		if(!userID.equals("0")) {
			String userInfoQueryString = "select * from advertisementusers a where a.id = '"+userID+"'";
			System.out.println(userInfoQueryString);
			FreeformQuery userInfoQuery = new FreeformQuery(userInfoQueryString, Pools.getConnectionPool(Pools.Names.PROJEX));
			try {
				userInfoContainer = new SQLContainer(userInfoQuery);
				userInfoItem = userInfoContainer.getItem(userInfoContainer.firstItemId());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			userInfoBinder = new FieldGroup(userInfoItem);
			userInfoBinder.bindMemberFields(this);
			userInfoBinder.setReadOnly(true);
		}	
		
		if(advertisementItem.getItemProperty("HARDCOPYONLY").getValue().toString().equals("0")) {
			String addendaQueryString = "SELECT "+
								"d.originalfilename, "+
								"d.FILESIZE, "+
								"d.CREATED, "+
								"d.LOCATION "+
						        "FROM advertisements a "+
						        "INNER JOIN folders f1 "+
						        "ON f1.refobjectid = a.id AND f1.foldername = 'Provided Documents' "+
						        "INNER JOIN folders f2 ON f2.refobjectid = f1.id "+
								"INNER JOIN DOCUMENTSTRUCTURE ds on ds.CHILDID = f2.ID "+
						        "INNER JOIN documents d ON d.refobjectid = ds.id "+
						        "WHERE a.id = '"+advertisementID+"' and f2.FOLDERNAME = 'Addenda'";
			new DocumentGridDataLoader(addendaQueryString, addenda, addendaContainer, "").start();
			String plansAndSpecificationsQueryString = "SELECT "+
								"d.originalfilename, "+
								"d.FILESIZE, "+
								"d.CREATED, "+
								"d.LOCATION "+
						        "FROM advertisements a "+
						        "INNER JOIN folders f1 "+
						        "ON f1.refobjectid = a.id AND f1.foldername = 'Provided Documents' "+
						        "INNER JOIN folders f2 ON f2.refobjectid = f1.id "+
								"INNER JOIN DOCUMENTSTRUCTURE ds on ds.CHILDID = f2.ID "+
						        "INNER JOIN documents d ON d.refobjectid = ds.id "+
						        "WHERE a.id = '"+advertisementID+"' and f2.FOLDERNAME = 'Plans and Specifications'";
			new DocumentGridDataLoader(plansAndSpecificationsQueryString, plansAndSpecifications, plansAndSpecificationsContainer, "").start();
			String referenceDocumentsQueryString = "SELECT "+
								"d.originalfilename, "+
								"d.FILESIZE, "+
								"d.CREATED, "+
								"d.LOCATION "+
						        "FROM advertisements a "+
						        "INNER JOIN folders f1 "+
						        "ON f1.refobjectid = a.id AND f1.foldername = 'Provided Documents' "+
						        "INNER JOIN folders f2 ON f2.refobjectid = f1.id "+
								"INNER JOIN DOCUMENTSTRUCTURE ds on ds.CHILDID = f2.ID "+
						        "INNER JOIN documents d ON d.refobjectid = ds.id "+
						        "WHERE a.id = '"+advertisementID+"' and f2.FOLDERNAME = 'Reference Documents'";
			new DocumentGridDataLoader(referenceDocumentsQueryString, referenceDocuments, referenceDocumentsContainer, "").start();
			String sealedDocumentsQueryString = "SELECT "+
								"d.originalfilename, "+
								"d.FILESIZE, "+
								"d.CREATED, "+
								"d.LOCATION "+
						        "FROM advertisements a "+
						        "INNER JOIN folders f1 "+
						        "ON f1.refobjectid = a.id AND f1.foldername = 'Provided Documents' "+
						        "INNER JOIN folders f2 ON f2.refobjectid = f1.id "+
								"INNER JOIN DOCUMENTSTRUCTURE ds on ds.CHILDID = f2.ID "+
						        "INNER JOIN documents d ON d.refobjectid = ds.id "+
						        "WHERE a.id = '"+advertisementID+"' and f2.FOLDERNAME = 'Sealed Documents'";
			new DocumentGridDataLoader(sealedDocumentsQueryString, sealedDocuments, sealedDocumentsContainer, "").start();
		} else {
		    plansDownloadButton.setEnabled(false);
		    addendaDownloadButton.setEnabled(false);
		    referenceDocumentDownloadButton.setEnabled(false);
		    sealedDocumentsDownloadButton.setEnabled(false);
		}

	}
	
	public void setNoneEditableComponent() {
		email.setReadOnly(true);
		firmName.setReadOnly(true);
		name.setReadOnly(true);
		address.setReadOnly(true);
		city.setReadOnly(true);
		state.setReadOnly(true);
		county.setReadOnly(true);
		country.setReadOnly(true);
		zip.setReadOnly(true);
		phone.setReadOnly(true);
		submit.setEnabled(false);
		reset.setEnabled(false);
	}
	
	class GridDataLoader extends Thread {

		String queryString;
		Grid grid;
		SQLContainer container;

		GridDataLoader(String queryString, Grid grid, SQLContainer container) {
			this.queryString = queryString;
			this.grid = grid;
			this.container = container;
		}

		@Override
		public void run() {
			try {
				System.out.println(this.queryString);
				FreeformQuery query = new FreeformQuery(this.queryString, Pools.getConnectionPool(Pools.Names.PROJEX));
				this.container = new SQLContainer(query);
				NavigationUI.get().access(new Runnable() {

					@Override
					public void run() {
						grid.setContainerDataSource(container);
					}
				});

			} catch (SQLException e){
				e.printStackTrace();
				System.out.println("Could not set grid data");
			}
		}

	}
	
	class DocumentGridDataLoader extends Thread {

		String queryString;
		Grid grid;
		SQLContainer container;
		String filter;

		DocumentGridDataLoader(String queryString, Grid grid, SQLContainer container, String filter) {
			this.queryString = queryString;
			this.grid = grid;
			this.container = container;
			this.filter = filter;
		}

		@Override
		public void run() {
			try {
				System.out.println(this.queryString);
				FreeformQuery query = new FreeformQuery(this.queryString, Pools.getConnectionPool(Pools.Names.PROJEX));
				this.container = new SQLContainer(query);
				NavigationUI.get().access(new Runnable() {
					@Override
					public void run() {
						grid.setContainerDataSource(container);
						grid.getColumn("LOCATION").setHidden(true);
						grid.getColumn("ORIGINALFILENAME").setHeaderCaption("File Name");
						grid.getColumn("FILESIZE").setHeaderCaption("File Size");
						grid.getColumn("CREATED").setHeaderCaption("Submission Date");
					}
				});

			} catch (SQLException e){
				e.printStackTrace();
				System.out.println("Could not set grid data");
			}
		}

	}
	
	public void setDownLoader(Button button) {
		final AdvancedFileDownloader downloader = new AdvancedFileDownloader();
		downloader.addAdvancedDownloaderListener(new AdvancedDownloaderListener() {
                    /**
                     * This method will be invoked just before the download
                     * starts. Thus, a new file path can be set.
                     * 
                     * @param downloadEvent
                     */
                    @Override
                    public void beforeDownload(DownloaderEvent downloadEvent) {

                        //String filePath = inputFilepathField.getValue();
                    	//String filePath = "/home/rxpkd/Advertisement/test.txt";	
                    	// get selected row(LOCATION)
                        downloader.setFilePath(downloadPath);
                        System.out.println(downloadPath);
//                        System.out.println("Starting downlad by button "
//                                + providedDocumentDownLoadPath.substring(providedDocumentDownLoadPath.lastIndexOf("/")));
                    }

         });
	    downloader.extend(button);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		advertisementID = event.getParameters();
		userID = getSession().getAttribute("userID").toString();
		System.out.println(userID);
		setScreenData();
		if(userID.equals("0")) {
			setNoneEditableComponent();
		}
		welcomeLabel.setValue("Welcome, "+getSession().getAttribute("user").toString());
	}

}
