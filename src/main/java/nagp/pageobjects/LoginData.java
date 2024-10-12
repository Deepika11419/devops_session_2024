package nagp.pageobjects;

public class LoginData {
	private RedBusLandingPage redbuslandingpage;
	private MyTrips myTrips;
	private LogOutPage logOutPage;

	public LoginData(RedBusLandingPage redbuslandingpage, MyTrips myTrips, LogOutPage logOutPage) {
		this.redbuslandingpage = redbuslandingpage;
		this.myTrips = myTrips;
		this.logOutPage = logOutPage;
	}
	/* Methods to call various object feom Logindata */

	public RedBusLandingPage getRedBusLandingPage() {
		return redbuslandingpage;
	}

	public MyTrips getMyTrips() {
		return myTrips;
	}

	public LogOutPage getLogOutPage() {
		return logOutPage;
	}
}
