package context.saros;

public class SarosAccessorFactory {
	static SarosAccessor sarosAccessor;
	public static void createSingleton() {
//		boundsOutputter = new ADisplayBoundsPiper();
		sarosAccessor = ASarosAccessor.getInstance();

	}
	public static SarosAccessor getSingleton() {
		return sarosAccessor;
	}

}
