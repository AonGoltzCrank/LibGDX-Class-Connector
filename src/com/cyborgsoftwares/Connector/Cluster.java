package com.cyborgsoftwares.Connector;

import java.util.ArrayList;
import java.util.List;

/**
 * A class similar to Map but oriented towards duplicate keys with different
 * values.
 * 
 * @author Alon
 * 
 * @param <K>
 *            - the key, usually a {@link Screen} or extends
 *            {@link SilentHorrorScreen}.
 * @param <V>
 *            - the value, usually a {@link TextButton}.
 */
public class Cluster<K, V> {

	private List<K> keys;
	private List<V> values;

	public Cluster() {
		keys = new ArrayList<K>();
		values = new ArrayList<V>();
	}

	/**
	 * A method to add a key and value to the cluster.
	 * 
	 * @param key
	 *            - the new key.
	 * @param value
	 *            - the new value.
	 */
	public void add(K key, V value) {
		if (key == null || value == null) {
			throw new IllegalArgumentException(
					"One of the values is null, please check cluster.");
		}
		keys.add(key);
		values.add(value);
	}

	/**
	 * A method to get all the values with a given key.
	 * 
	 * @param key
	 *            - the key that is supposed to be checked.
	 * @param values
	 *            - an array of values, can be of length one or hundred, it
	 *            doesn't matter.
	 * @return the size of the array values that was given in the arguments.
	 */
	@SuppressWarnings("unchecked")
	public int getValuesForKey(K key, V[] values) {
		List<V> valuesList = new ArrayList<V>();
		for (int i = 0; i < keys.size(); i++) {
			if (keys.get(i).equals(key)) {
				valuesList.add(this.values.get(i));
			}
		}
		try {
			values = (V[]) valuesList.toArray();
		} catch (ClassCastException e) {
			e.printStackTrace();
		}
		return values.length;
	}

	public V getValuesForKey(K key) {
		for (int i = 0; i < keys.size(); i++) {
			if (keys.get(i).equals(key)) {
				return values.get(i);
			}
		}
		return null;
	}

	/**
	 * Get a value of V according to it's position.
	 * 
	 * @param index
	 *            - the position of the element.
	 * @return
	 */
	public V get(int index) {
		return values.get(index);
	}

	/**
	 * A method used to clear the cluster after each time a screen changes.
	 */
	public void cleanCluster() {
		keys = new ArrayList<K>();
		values = new ArrayList<V>();
	}
}
