import java.util.Vector;

public class Log {
	private static boolean locked = false;
	private static int threads = 1;
	private static Vector<String> rv = new Vector<String>();
	public static void logFieldAcc(final Object o, String name, final boolean isStatic, final boolean isWrite)
	{
		if((!locked && threads>1 && name != "lock" && name != "out" && name != "SECONDS")|| false) {
			System.out.println("datarace detected:"+"Thread "+Thread.currentThread().getName()+(isWrite?" wrote":" read")+ " static field "+name);
			rv.add(name);
			return;
		}
		if(rv.contains(name)) {
			System.out.println("datarace detected:"+"Thread "+Thread.currentThread().getName()+(isWrite?" wrote":" read")+ " static field "+name);
			return;
		}
		if(isStatic)
			System.out.println("Thread "+Thread.currentThread().getName()+(isWrite?" wrote":" read")+ " static field "+name);
		else
			System.out.println("Thread "+Thread.currentThread().getName()+(isWrite?" wrote":" read")+ " instance field "+name+" of object "+o);
	}

	public static void logFieldAcc(final Object o, String name, final boolean isStatic, final boolean isWrite, final Object v)
	{
		if(!locked && threads>1 && name != "lock" && name != "out") {
			System.out.println("datarace detected:"+"Thread "+Thread.currentThread().getName()+(isWrite?" wrote":" read")+ " static field "+name);
			return;
		}
		if(rv.contains(name)) {
			System.out.println("Thread "+Thread.currentThread().getName()+(isWrite?" wrote":" read")+ " static field "+name+" with value "+v);
			return;
		}
		if(isStatic)
			System.out.println("Thread "+Thread.currentThread().getName()+(isWrite?" wrote":" read")+ " static field "+name+" with value "+v);
		else
			System.out.println("Thread "+Thread.currentThread().getName()+(isWrite?" wrote":" read")+ " instance field "+name+" of object "+o+" with value "+v);
	}
	  public static  void logArrayAcc(final Object a, int index, final boolean isWrite) {
		  	if(!locked && threads>1) {
				System.out.println("datarace detected:"+"Thread "+Thread.currentThread().getName()+(isWrite?" wrote":" read")+ " array [index] "+a+" ["+index+"]");
				return;
			}
			System.out.println("Thread "+Thread.currentThread().getName()+(isWrite?" wrote":" read")+ " array [index] "+a+" ["+index+"]");
	  }
	  public static  void logArrayAcc(final Object a, int index, final boolean isWrite, final Object v) {
		  if(!locked && threads>1) {
			  	System.out.println("datarace detected:"+"Thread "+Thread.currentThread().getName()+(isWrite?" wrote":" read")+ " array [index] "+a+" ["+index+"]");
				return;
			}
			System.out.println("Thread "+Thread.currentThread().getName()+(isWrite?" wrote":" read")+ " array [index] "+a+" ["+index+"] with value "+v);
	  }
	  public static  void logStart(final Thread t) {
		  threads++;
		  String name = Thread.currentThread().getName();
		    String name_t = t.getName();
		    System.out.println("Thread "+name+" start new Thread "+name_t);
	  }
	  public static  void logJoin(final Thread t) {
		  threads--;
		  String name = Thread.currentThread().getName();
		    String name_t = t.getName();
		    System.out.println("Thread "+name+" join Thread "+name_t);
	  }
	  public static  void logLock(final Object o) {
		  locked = true;
		  String name = Thread.currentThread().getName();
		  System.out.println("Thread "+name+" lock object "+System.identityHashCode(o));
	  }

	  public static  void logUnlock(final Object o) {
		  locked = false;
		  String name = Thread.currentThread().getName();
		  System.out.println("Thread "+name+" unlock object "+System.identityHashCode(o));
	  }

	  public static  void logWait(final Object o) {
		  String name = Thread.currentThread().getName();
		  System.out.println("Thread "+name+" wait signal on object "+System.identityHashCode(o));
	  }
	  public static  void logNotify(final Object o) {
		  String name = Thread.currentThread().getName();
		  System.out.println("Thread "+name+" notify signal on object "+System.identityHashCode(o));
	  }
	  public static  void logNotifyAll(final Object o) {
		  String name = Thread.currentThread().getName();
		  System.out.println("Thread "+name+" notifyAll signal "+System.identityHashCode(o));
	  }
}