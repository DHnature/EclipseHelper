package context.saros;

public class SarosAccessorFactory {
	static SarosAccessor sarosAccessor;
	public static void createSingleton() {
//		boundsOutputter = new ADisplayBoundsPiper();
		sarosAccessor = ASarosAccessor.getInstance();

	}
	public static SarosAccessor getSingleton() {
		if (sarosAccessor == null)
			createSingleton();
		return sarosAccessor;
	}

}
