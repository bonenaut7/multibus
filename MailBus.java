package by.fxg.multibus;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.Mod.PreInit;

public class MailBus {
	private static List<Object> listeners = new ArrayList<Object>();
	public static void register(Object obj) {
		if (!listeners.contains(obj)) {
			listeners.add(obj);
		}
	}
	
	public static boolean unregister(Object obj) {
		if (listeners.contains(obj)) {
			return listeners.remove(obj);
		}
		return false;
	}
	
	public static <T extends BusMail> void post(T event) {
		for (Object listener : listeners) {
			for (Method m : listener.getClass().getDeclaredMethods()) {
				Post post = annotatedByPost(m);
				if (post != null) {
					if (m.getParameterTypes().length == 1) {
						if (parameterAssignableFrom(m, event.getClass())) {
							if (post.mail() != BusMail.class && parameterAssignableFrom(m, post.mail()) || post.mail() == BusMail.class) {
								try {
									m.invoke(listener, event);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					} else {
						System.err.println("Method annotated with @EventHandler must have only one parameter <? extends Event>");
					}
				}
			}
		}
	}
	
	public static Post annotatedByPost(Method m) {
		if (m.getAnnotations().length > 0) {
			for (Annotation ann : m.getAnnotations()) {
				if (ann.annotationType().isAssignableFrom(Post.class) || ann instanceof Post) {
					return (Post)ann;
				}
			}
		}
		return null;
	}
	
	public static boolean parameterAssignableFrom(Method m, Class<?> paramClass) {
		if (m.getParameterTypes().length > 0) {
			for (Class<?> tp : m.getParameterTypes()) {
				if (paramClass.isAssignableFrom(tp)) {
					return true;
				}
			}
		}
		return false;
	}
}
