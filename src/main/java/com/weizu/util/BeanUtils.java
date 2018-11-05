package com.weizu.util;

import java.lang.reflect.Field;
import java.util.*;





/**
 * 对象处理UTILS工具类
 * <p>
 * 20130810 对象拷贝，按属性拷贝（安全风险，仅限内部使用）
 * 
 * @author modi
 * 
 */
public final class BeanUtils {
	private BeanUtils() {
	}

	//
	public static BeanUtils getInstance() {
		return new BeanUtils();
	}

	private static final Map<Class<?>, Field[]> map = new HashMap<Class<?>, Field[]>();

	public static Field[] getAllFields_Cache(Class<?> c) {
		synchronized (map) {
			if (!map.containsKey(c)) {
				map.put(c, getAllFields(c));
			}
		}
		return map.get(c);
	}

	public static Field[] getAllFields(Class<?> c) {
		List<Field> list = new ArrayList<Field>();
		while (c != Object.class) {
			list.addAll(Arrays.asList(c.getDeclaredFields()));
			c = c.getSuperclass();
		}
		return list.toArray(new Field[list.size()]);
	}
	//
	public interface ToFilter {
		/**
		 * 字段转换拦截处理器
		 * 
		 * @param from
		 * @param to
		 * @return 处理成功返回true（跳入下一次处理），处理失败返回false（继续本次处理）
		 * @throws Throwable
		 *             异常表示处理失败
		 */
		boolean filter(Field from, Field to) throws Throwable;
	}

	private final List<ToFilter> filters = new ArrayList<ToFilter>();

	/**
	 * add filter
	 * 
	 * @param filter
	 */
	public BeanUtils addFilter(ToFilter filter) {
		filters.add(filter);
		return this;
	}

	/**
	 * remove filter
	 * 
	 * @param filter
	 */
	public void removeFilter(ToFilter filter) {
		filters.remove(filter);
	}

	/**
	 * clear filter
	 */
	public void clear() {
		filters.clear();
	}

	/**
	 * 对象拷贝
	 * 
	 * @param from
	 * @param to
	 */
	public void to(Object from, Object to) {
		Field[] fs = getAllFields(from.getClass());
		Field[] ts = getAllFields(to.getClass());
		L1: for (Field t : ts) {
			for (Field f : fs) {
				for (ToFilter filter : filters) {
					try {
						if (filter.filter(f, t)) {
							continue L1;
						}
					} catch (Throwable e) {
					}
				}
				if (t.getName().equals(f.getName())
						&& t.getType().equals(f.getType())) {
					t.setAccessible(true);
					f.setAccessible(true);
					try {
						t.set(to, f.get(from));
					} catch (Exception e) {
						e.printStackTrace();
						continue L1;
					}
				}
			}
		}
	}


}
