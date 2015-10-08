package edu.missouri.cf.pdcapp.ui;

import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;
import com.vaadin.data.util.sqlcontainer.query.OrderBy;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;
import com.vaadin.data.util.sqlcontainer.query.generator.OracleGenerator;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import edu.missouri.cf.pdcapp.dbconnect.Pools;
import edu.missouri.cf.pdcapp.ui.converter.OracleCurrencyToStringConverter;
import edu.missouri.cf.pdcapp.ui.converter.OracleTimestampToDateConverter;
import edu.missouri.cf.pdcapp.ui.converter.OracleTimestampToStringConverter;

public class MainView extends CustomComponent implements View{
	final static String NAME = "MainView";
	private Label welcomeLabel;
	private Grid DesignBidBuild;
	private Grid DesignAndBuildProposal;
	private Grid JOC;
	private Grid CMAtRisk;
	private Grid OpenedBids;
	private Grid RecentlyAwarded;
	private VerticalLayout rootLayout;
	private String advertisementID;
	private Button logoutButton;
	private Button backButton;
    private Button DBBViewButton;
    private Button DBPViewButton;
    private Button CMViewButton;
    private Button ROViewButton;
    private Button RAViewButton;

	public MainView() {
		// TODO Auto-generated method stub
		welcomeLabel = new Label() {
			
		};
		
		backButton = new Button("back",new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
            	getUI().getPage().setLocation("");
            	getUI().getSession().close();
            }
        });
		
		
		logoutButton = new Button("logout",new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
            	getUI().getPage().setLocation("");
            	getUI().getSession().close();
            }
        });
		DesignBidBuild = new Grid(){
			{
				setImmediate(true);
				setCaption("<h1>Design/Bid/Build</h1>");
				setCaptionAsHtml(true);
				setSelectionMode(SelectionMode.SINGLE);
				addSelectionListener(new SelectionListener() {

					@Override
					public void select(SelectionEvent event) {
						// TODO Auto-generated method stub
//						Item item = DesignBidBuild.getContainerDataSource().getItem(DesignBidBuild.getSelectedRow());
//						if(item != null) {
//							advertisementID = item.getItemProperty("ID").getValue().toString();
//							getUI().getNavigator().navigateTo(DetailView.NAME+"/"+advertisementID);
//						}
					}
					
				});
			}
		};

		DesignAndBuildProposal = new Grid(){
			{
				setImmediate(true);
				setCaption("<h1>Design/Build Proposals</h1>");
				setCaptionAsHtml(true);
				addSelectionListener(new SelectionListener() {

					@Override
					public void select(SelectionEvent event) {
						// TODO Auto-generated method stub
//						Item item = DesignAndBuildProposal.getContainerDataSource().getItem(DesignAndBuildProposal.getSelectedRow());
//						if(item != null) {
//							advertisementID = item.getItemProperty("ID").getValue().toString();
//							getUI().getNavigator().navigateTo(DetailView.NAME+"/"+advertisementID);
//						}
					}
					
				});
			}
		};
		
//		JOC = new Grid() {
//			{
//				setCaption("<h1>JOC (Job Order Contracting) Projects</h1>");
//				setCaptionAsHtml(true);
//			}
//		};
		
		CMAtRisk = new Grid() {
			{
				setImmediate(true);
				setCaption("<h1>Construction Manager at Risk</h1>");
				setCaptionAsHtml(true);
				addSelectionListener(new SelectionListener() {

					@Override
					public void select(SelectionEvent event) {
						// TODO Auto-generated method stub
//						Item item = CMAtRisk.getContainerDataSource().getItem(CMAtRisk.getSelectedRow());
//						if(item != null) {
//							advertisementID = item.getItemProperty("ID").getValue().toString();
//							getUI().getNavigator().navigateTo(DetailView.NAME+"/"+advertisementID);
//						}
					}
					
				});
			}
		};
		OpenedBids = new Grid() {
			{
				setImmediate(true);
				setCaption("<h1>Recently Opened Bids</h1>");
				setCaptionAsHtml(true);
				addSelectionListener(new SelectionListener() {

					@Override
					public void select(SelectionEvent event) {
						// TODO Auto-generated method stub
//						Item item = OpenedBids.getContainerDataSource().getItem(OpenedBids.getSelectedRow());
//						if(item != null) {
//							advertisementID = item.getItemProperty("ID").getValue().toString();
//							getUI().getNavigator().navigateTo(DetailView.NAME+"/"+advertisementID);
//						}
					}
					
				});
			}
		};
		RecentlyAwarded = new Grid() {
			{
				setImmediate(true);
				setCaption("<h1>Recently Awarded Contracts</h1>");
				setCaptionAsHtml(true);
				addSelectionListener(new SelectionListener() {

					@Override
					public void select(SelectionEvent event) {
						// TODO Auto-generated method stub
//						Item item = RecentlyAwarded.getContainerDataSource().getItem(RecentlyAwarded.getSelectedRow());
//						if(item != null) {
//							advertisementID = item.getItemProperty("ID").getValue().toString();
//							getUI().getNavigator().navigateTo(DetailView.NAME+"/"+advertisementID);
//						}
					}
					
				});
			}
		};
				
		//hold
//		String JOCqueryString = "";
//		FreeformQuery JOCquery = new FreeformQuery(JOCqueryString, Pools.getConnectionPool(Pools.Names.PROJEX));
//		try {
//			SQLContainer container = new SQLContainer(JOCquery);
//			DesignAndBuildProposal.setContainerDataSource(container);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	    DBBViewButton = new Button("View Detail",new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
				Item item = DesignBidBuild.getContainerDataSource().getItem(DesignBidBuild.getSelectedRow());
				if(item != null) {
					advertisementID = item.getItemProperty("ID").getValue().toString();
					getUI().getNavigator().navigateTo(DetailView.NAME+"/"+advertisementID);
				} else {
					Notification.show("Please select advertisement");
				}
            }
        });
	    DBPViewButton = new Button("View Detail",new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
				Item item = DesignAndBuildProposal.getContainerDataSource().getItem(DesignAndBuildProposal.getSelectedRow());
				if(item != null) {
					advertisementID = item.getItemProperty("ID").getValue().toString();
					getUI().getNavigator().navigateTo(DetailView.NAME+"/"+advertisementID);
				} else {
					Notification.show("Please select advertisement");
				}
            }
        });
	    CMViewButton= new Button("View Detail",new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
				Item item = CMAtRisk.getContainerDataSource().getItem(CMAtRisk.getSelectedRow());
				if(item != null) {
					advertisementID = item.getItemProperty("ID").getValue().toString();
					getUI().getNavigator().navigateTo(DetailView.NAME+"/"+advertisementID);
				} else {
					Notification.show("Please select advertisement");
				}         }
        });
	    ROViewButton= new Button("View Detail",new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
				Item item = OpenedBids.getContainerDataSource().getItem(OpenedBids.getSelectedRow());
				if(item != null) {
					advertisementID = item.getItemProperty("ID").getValue().toString();
					getUI().getNavigator().navigateTo(DetailView.NAME+"/"+advertisementID);
				} else {
					Notification.show("Please select advertisement");
				}
            }
        });
	    RAViewButton= new Button("View Detail",new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
				Item item = RecentlyAwarded.getContainerDataSource().getItem(RecentlyAwarded.getSelectedRow());
				if(item != null) {
					advertisementID = item.getItemProperty("ID").getValue().toString();
					getUI().getNavigator().navigateTo(DetailView.NAME+"/"+advertisementID);
				} else {
					Notification.show("Please select advertisement");
				}
            }
        });


		
		layout();
	}
	
	private void layout() {
		 rootLayout = new VerticalLayout(){
			{
				//setSizeFull();
				setMargin(true);
				setSpacing(true);
				setWidth("100%");
				addComponent(new HorizontalLayout(){
					{
//						setMargin(true);
						setSpacing(true);
						setWidth("100%");
						addComponent(welcomeLabel);
						HorizontalLayout buttonLayout = new HorizontalLayout(){
							{
								setSpacing(true);
								setMargin(true);
								addComponent(backButton);
								addComponent(logoutButton);
							}
						};
						addComponent(buttonLayout);
						setComponentAlignment(welcomeLabel, Alignment.TOP_LEFT);
						setComponentAlignment(buttonLayout, Alignment.TOP_RIGHT);
					}		
				});
				addComponent(new VerticalLayout(){
					{
						setMargin(true);
						setSpacing(true);
						addComponent(DesignBidBuild);
						DesignBidBuild.setWidth("1350px");
						setComponentAlignment(DesignBidBuild, Alignment.MIDDLE_CENTER);
						setExpandRatio(DesignBidBuild, 1.0f);
						addComponent(DBBViewButton);
						setComponentAlignment(DBBViewButton, Alignment.BOTTOM_RIGHT);
					}
				});
				
				addComponent(new VerticalLayout(){
					{
						setMargin(true);
						setSpacing(true);
						
						addComponent(DesignAndBuildProposal);
						DesignAndBuildProposal.setWidth("1350px");
						setComponentAlignment(DesignAndBuildProposal, Alignment.MIDDLE_CENTER);
						setExpandRatio(DesignAndBuildProposal, 1.0f);
						addComponent(DBPViewButton);
						setComponentAlignment(DBPViewButton, Alignment.BOTTOM_RIGHT);
					}
				});
				
//				addComponent(new VerticalLayout(){
//					{
//						setMargin(true);
//						setSpacing(true);
//						addComponent(JOC);
//						JOC.setWidth("1200px");
//						setComponentAlignment(JOC, Alignment.MIDDLE_CENTER);
//						setExpandRatio(JOC, 1.0f);
//					}
//				});
				
				addComponent(new VerticalLayout(){
					{
						setMargin(true);
						setSpacing(true);
						addComponent(CMAtRisk);
						CMAtRisk.setWidth("1350px");
						setComponentAlignment(CMAtRisk, Alignment.MIDDLE_CENTER);
						setExpandRatio(CMAtRisk, 1.0f);
						addComponent(CMViewButton);
						setComponentAlignment(CMViewButton, Alignment.BOTTOM_RIGHT);
					}
				});
				
				addComponent(new VerticalLayout(){
					{
						setMargin(true);
						setSpacing(true);
						addComponent(OpenedBids);
						OpenedBids.setWidth("1350px");
						setComponentAlignment(OpenedBids, Alignment.MIDDLE_CENTER);
						setExpandRatio(OpenedBids, 1.0f);
						addComponent(ROViewButton);
						setComponentAlignment(ROViewButton, Alignment.BOTTOM_RIGHT);
					}
				});
				
				addComponent(new VerticalLayout(){
					{
						setMargin(true);
						setSpacing(true);
						addComponent(RecentlyAwarded);
						RecentlyAwarded.setWidth("1350px");
						setComponentAlignment(RecentlyAwarded, Alignment.MIDDLE_CENTER);
						setExpandRatio(RecentlyAwarded, 1.0f);
						addComponent(RAViewButton);
						setComponentAlignment(RAViewButton, Alignment.BOTTOM_RIGHT);
					}
				});
				
			}
		};
		setCompositionRoot(rootLayout);
	}
	
	public void setScreenData() {
//		String DBBqueryString = "SELECT " 
//								+"P.PROJECTNUMBER||' - '||P.COMBINEDTITLE AS TITLE, " 
//								+"AD.PRESUBMISSIONMEETING AS PRE_BID_D, " 
//								+"AD.SUBMISSIONDEADLINE AS BID_D, "
//								+"AD.ID "
//								+"FROM ADVERTISEMENTS AD " 
//								+"INNER JOIN ADVERTISEMENTBREAKDOWNS A ON A.REFOBJECTID = AD.ID " 
//								+"INNER JOIN PROJECTDETAILSV1 P ON P.ID = A.PCSPROJECTID "
//								+"WHERE P.PROJECTMETHOD = 'DESIGN/BID/BUILD' ";
								//+"AND AD.MADEPUBLIC <= SYSDATE AND AD.SUBMISSIONDEADLINE > SYSDATE-30";
		String DBBqueryString = "SELECT DISTINCT " 
								+"P.PROJECTNUMBER||' - '||P.COMBINEDTITLE AS TITLE, " 
								+"AD.PRESUBMISSIONMEETING AS PRE_BID_D, " 
								+"AD.SUBMISSIONDEADLINE AS BID_D, "
								+"AD.ID "
								+"FROM ADVERTISEMENTS AD " 
								+"INNER JOIN ADVERTISEMENTBREAKDOWNS A ON A.REFOBJECTID = AD.ID " 
								+"INNER JOIN PROJECTDETAILSV1 P ON P.ID = A.PCSPROJECTID ";
//								+"WHERE AD.ID = '2004542'";
		new DBBDataLoader(DBBqueryString, DesignBidBuild).start();
		
		String DBPqueryString = "SELECT " 
								+"P.PROJECTNUMBER||' - '||P.COMBINEDTITLE AS TITLE, " 
								+"AD.PRESUBMISSIONMEETING AS PRE_PROP_D, " 
								+"AD.SUBMISSIONDEADLINE AS SUB_DEADLINE, "
								+"AD.ID "
								+"FROM ADVERTISEMENTS AD " 
								+"INNER JOIN ADVERTISEMENTBREAKDOWNS A ON A.REFOBJECTID = AD.ID " 
								+"INNER JOIN PROJECTDETAILSV1 P ON P.ID = A.PCSPROJECTID "
								+"WHERE P.PROJECTMETHOD = 'DESIGN/BUILD' "
								+"AND AD.MADEPUBLIC <= SYSDATE AND AD.SUBMISSIONDEADLINE > SYSDATE-30";
		
		new DBPDataLoader(DBPqueryString, DesignAndBuildProposal).start();

		
		String CMRiskqueryString ="SELECT " 
								+"P.PROJECTNUMBER||' - '||P.COMBINEDTITLE AS TITLE, " 
								+"AD.PRESUBMISSIONMEETING AS PRE_BID_D, " 
								+"AD.SUBMISSIONDEADLINE AS BID_D, "
								+"AD.ID "
								+"FROM ADVERTISEMENTS AD " 
								+"INNER JOIN ADVERTISEMENTBREAKDOWNS A ON A.REFOBJECTID = AD.ID " 
								+"INNER JOIN PROJECTDETAILSV1 P ON P.ID = A.PCSPROJECTID "
								+"WHERE P.PROJECTMETHOD = 'CMATRISK' "
								+"AND AD.MADEPUBLIC <= SYSDATE AND AD.SUBMISSIONDEADLINE > SYSDATE-30";

		new CMRDataLoader(CMRiskqueryString, CMAtRisk).start();


		
		String openedBidQueryString =  "SELECT "
										+"P.PROJECTNUMBER||' - '||P.COMBINEDTITLE AS TITLE, "
										+"AD.SUBMISSIONDEADLINE AS SUBMISSIONDATE, AD.ID " 
										+"FROM ADVERTISEMENTS AD " 
										+"INNER JOIN ADVERTISEMENTBREAKDOWNS A ON A.REFOBJECTID = AD.ID " 
										+"INNER JOIN PROJECTDETAILSV1 P ON A.PCSPROJECTID = P.ID "
										+"WHERE AD.MADEPUBLIC <= SYSDATE " 
										+"AND AD.SUBMISSIONDEADLINE >= SYSDATE-30 "
										+"AND AD.SUBMISSIONDEADLINE < SYSDATE ";
		
		new OBDataLoader(openedBidQueryString, OpenedBids).start();


		
		String recentlyAwardedQueryString = "SELECT DISTINCT "
											+"P.PROJECTNUMBER||' - '||P.COMBINEDTITLE AS TITLE, "
											+"C.CREATED AS AWARDED, "
											+"C.FIRMNAME, A.AMOUNT, AD.ID FROM CONTRACTDETAILS C " 
											+"INNER JOIN ADVERTISEMENTS AD ON C.ADVERTISEMENTID = AD.ID " 
											+"INNER JOIN PROJECTDETAILSV1 P ON C.REFOBJECTID = P.ID " 
											+"INNER JOIN PCSPROJECTROW PCS ON PCS.PCSPROJECTID = P.ID " 
											+"INNER JOIN ADVERTISEMENTBREAKDOWNS A ON A.PCSPROJECTID = PCS.PCSPROJECTID " 
											+"WHERE C.LASTDATETOAWARD > SYSDATE AND AD.MADEPUBLIC < SYSDATE";

		new RADataLoader(recentlyAwardedQueryString, RecentlyAwarded).start();


	}
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		setScreenData();
		welcomeLabel.setValue("Welcome, "+getSession().getAttribute("user").toString());
	}
	
	
	class DBBDataLoader extends Thread {

		String queryString;
		Grid grid;

		DBBDataLoader(String queryString, Grid grid) {
			this.queryString = queryString;
			this.grid = grid;
		}

		@Override
		public void run() {
			try {
				System.out.println(this.queryString);
				FreeformQuery query = new FreeformQuery(this.queryString, Pools.getConnectionPool(Pools.Names.PROJEX));
				final SQLContainer sqlContainer = new SQLContainer(query);
				NavigationUI.get().access(new Runnable() {

					@Override
					public void run() {
						grid.setContainerDataSource(sqlContainer);
						grid.getColumn("ID").setHidden(true);
						grid.getColumn("TITLE").setHeaderCaption("Project Name (Project#)");
						grid.getColumn("PRE_BID_D").setHeaderCaption("Prebid Meeting Date");
						grid.getColumn("BID_D").setHeaderCaption("Bid Open Date");
						grid.getColumn("PRE_BID_D").setConverter(new OracleTimestampToStringConverter());
						grid.getColumn("BID_D").setConverter(new OracleTimestampToStringConverter());
					}
				});

			} catch (SQLException e){
				e.printStackTrace();
				System.out.println("Could not set grid data");
			}
		}

	}
	
	class DBPDataLoader extends Thread {

		String queryString;
		Grid grid;

		DBPDataLoader(String queryString, Grid grid) {
			this.queryString = queryString;
			this.grid = grid;
		}

		@Override
		public void run() {
			try {
				System.out.println(this.queryString);
				FreeformQuery query = new FreeformQuery(this.queryString, Pools.getConnectionPool(Pools.Names.PROJEX));
				final SQLContainer sqlContainer = new SQLContainer(query);
				NavigationUI.get().access(new Runnable() {

					@Override
					public void run() {
						grid.setContainerDataSource(sqlContainer);
						grid.getColumn("ID").setHidden(true);
						grid.getColumn("TITLE").setHeaderCaption("Project Name (Project#)");
						grid.getColumn("PRE_PROP_D").setHeaderCaption("Mandatory Pre-proposal Conference");
						grid.getColumn("SUB_DEADLINE").setHeaderCaption("Submission Deadline");
						grid.getColumn("PRE_PROP_D").setConverter(new OracleTimestampToStringConverter());
						grid.getColumn("SUB_DEADLINE").setConverter(new OracleTimestampToStringConverter());
					}
				});

			} catch (SQLException e){
				e.printStackTrace();
				System.out.println("Could not set grid data");
			}
		}

	}
	
	class CMRDataLoader extends Thread {

		String queryString;
		Grid grid;

		CMRDataLoader(String queryString, Grid grid) {
			this.queryString = queryString;
			this.grid = grid;
		}

		@Override
		public void run() {
			try {
				System.out.println(this.queryString);
				FreeformQuery query = new FreeformQuery(this.queryString, Pools.getConnectionPool(Pools.Names.PROJEX));
				final SQLContainer sqlContainer = new SQLContainer(query);
				NavigationUI.get().access(new Runnable() {

					@Override
					public void run() {
						grid.setContainerDataSource(sqlContainer);
						grid.getColumn("ID").setHidden(true);
						grid.getColumn("TITLE").setHeaderCaption("Project Name (Project#)");
						grid.getColumn("PRE_BID_D").setHeaderCaption("Pre-Bid Meeting Date");
						grid.getColumn("BID_D").setHeaderCaption("Submission Deadline");
						grid.getColumn("PRE_BID_D").setConverter(new OracleTimestampToStringConverter());
						grid.getColumn("BID_D").setConverter(new OracleTimestampToStringConverter());
					}
				});

			} catch (SQLException e){
				e.printStackTrace();
				System.out.println("Could not set grid data");
			}
		}

	}
	
	class RADataLoader extends Thread {

		String queryString;
		Grid grid;

		RADataLoader(String queryString, Grid grid) {
			this.queryString = queryString;
			this.grid = grid;
		}

		@Override
		public void run() {
			try {
				System.out.println(this.queryString);
				FreeformQuery query = new FreeformQuery(this.queryString, Pools.getConnectionPool(Pools.Names.PROJEX));
				final SQLContainer sqlContainer = new SQLContainer(query);
				NavigationUI.get().access(new Runnable() {

					@Override
					public void run() {
						grid.setContainerDataSource(sqlContainer);
						grid.getColumn("ID").setHidden(true);
						grid.getColumn("TITLE").setHeaderCaption("Project Name (Project#)");
						grid.getColumn("AWARDED").setHeaderCaption("Date Contract Awarded");
						grid.getColumn("FIRMNAME").setHeaderCaption("Contract Awarded To");
						grid.getColumn("AMOUNT").setHeaderCaption("Amount Awarded");
						grid.getColumn("AWARDED").setConverter(new OracleTimestampToStringConverter());
						grid.getColumn("AMOUNT").setConverter(new OracleCurrencyToStringConverter());
					}
				});

			} catch (SQLException e){
				e.printStackTrace();
				System.out.println("Could not set grid data");
			}
		}

	}
	
	class OBDataLoader extends Thread {

		String queryString;
		Grid grid;

		OBDataLoader(String queryString, Grid grid) {
			this.queryString = queryString;
			this.grid = grid;
		}

		@Override
		public void run() {
			try {
				System.out.println(this.queryString);
				FreeformQuery query = new FreeformQuery(this.queryString, Pools.getConnectionPool(Pools.Names.PROJEX));
				final SQLContainer sqlContainer = new SQLContainer(query);
				NavigationUI.get().access(new Runnable() {

					@Override
					public void run() {
						grid.setContainerDataSource(sqlContainer);
						grid.getColumn("ID").setHidden(true);
						grid.getColumn("TITLE").setHeaderCaption("Project Name (Project#)");
						grid.getColumn("SUBMISSIONDATE").setHeaderCaption("Date Bid Opened");
						grid.getColumn("SUBMISSIONDATE").setConverter(new OracleTimestampToStringConverter());
					}
				});

			} catch (SQLException e){
				e.printStackTrace();
				System.out.println("Could not set grid data");
			}
		}

	}
}
