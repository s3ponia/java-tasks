package ru.mail.polis.homework.collections;


import java.util.*;


/**
 * Написать структуру данных, реализующую интерфейс мапы + набор дополнительных методов.
 * 2 дополнительных метода должны вовзращать самый популярный ключ и его популярность.
 * Популярность - это количество раз, который этот ключ учавствовал в других методах мапы, такие как
 * containsKey, get, put, remove.
 * Считаем, что null я вам не передю ни в качестве ключа, ни в качестве значения
 * <p>
 * Важный момент, вам не надо реализовывать мапу, вы должны использовать композицию.
 * Вы можете использовать любые коллекции, которые есть в java.
 * <p>
 * Помните, что по мапе тоже можно итерироваться
 * <p>
 * for (Map.Entry<K, V> entry : map.entrySet()) {
 * entry.getKey();
 * entry.getValue();
 * }
 * <p>
 * <p>
 * Дополнительное задание описано будет ниже
 *
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;
    private final Map<K, Integer> popularityKey = new HashMap<>();
    private final Map<V, Integer> popularityValue = new HashMap<>();

    private void countValues(V value) {
        if (value == null)
            return;
        Integer entry = popularityValue.remove(value);
        if (entry == null) {
            popularityValue.put(value, 1);
        } else {
            popularityValue.put(value, entry + 1);
        }
    }

    private void countKeys(K key) {
        if (key == null)
            return;
        Integer entry = popularityKey.remove(key);
        if (entry == null) {
            popularityKey.put(key, 1);
        } else {
            popularityKey.put(key, entry + 1);
        }
    }

    public PopularMap() {
        this.map = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        if (key == null)
            return false;
        countKeys((K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        if (value == null)
            return false;
        countValues((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        if (key == null)
            return null;
        countKeys((K) key);
        V value = map.get(key);
        countValues(value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        countValues(value);
        countKeys(key);
        V ret = map.put(key, value);
        countValues(ret);
        return ret;
    }

    @Override
    public V remove(Object key) {
        if (key == null)
            return null;
        countKeys((K) key);
        V value = map.remove(key);
        countValues(value);
        return value;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = map.keySet();
        for (K key :
                keySet) {
            countKeys(key);
        }
        return keySet;
    }

    @Override
    public Collection<V> values() {
        Collection<V> values = map.values();
        for (V value :
                values) {
            countValues(value);
        }
        return values;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entrySet = map.entrySet();
        for (Entry<K, V> entry :
                entrySet) {
            countValues(entry.getValue());
            countKeys(entry.getKey());
        }
        return entrySet;
    }

    /**
     * Возвращает самый популярный, на данный момент, ключ
     */
    public K getPopularKey() {
        K maxKey = null;
        Integer maxKeyValue = 0;
        for (Map.Entry<K, Integer> entry :
                popularityKey.entrySet()) {
            if (entry.getValue().compareTo(maxKeyValue) > 0) {
                maxKeyValue = entry.getValue();
                maxKey = entry.getKey();
            }
        }
        return maxKey;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        Integer ret = popularityKey.get(key);
        return ret == null ? 0 : ret;
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        V maxVal = null;
        Integer maxValValue = 0;
        for (Map.Entry<V, Integer> entry :
                popularityValue.entrySet()) {
            if (entry.getValue().compareTo(maxValValue) > 0) {
                maxValValue = entry.getValue();
                maxVal = entry.getKey();
            }
        }
        return maxVal;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        Integer ret = popularityValue.get(value);
        return ret == null ? 0 : ret;
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        return popularityValue.entrySet().stream()
                .sorted(Comparator.comparingInt(Entry::getValue))
                .map(Entry::getKey)
                .iterator();
    }
}
