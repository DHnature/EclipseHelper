package dayton;

public class ServerStatusUpdaterFactory {
	static ServerStatusUpdater singleton;
	public static ServerStatusUpdater getOrCreateSingleton() {
		if (singleton == null){
			singleton = new AServerStatusUpdater();
		}
		return singleton;
	}
	

}
