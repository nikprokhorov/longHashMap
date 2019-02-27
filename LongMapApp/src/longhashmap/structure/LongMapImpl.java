package longhashmap.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LongMapImpl<V> implements LongMap<V> {

    private static int capacity = 16;
    private Entry<V>[] storage;
    private int size;
    private static final float maxLoad = .75f;

    private static class Entry<V> {

        private final long key;
        private V data;

        public Entry(long key, V data) {
            this.key = key;
            this.data = data;
        }

        public long getKey() {
            return key;
        }

        public V getData() {
            return data;
        }

        public void setData(V data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return key + "-> " + data;
        }

    }

    public LongMapImpl() {
        storage = new Entry[capacity];
    }

    @Override
    public V put(long key, V value) {
        int index = index(hash(key));
        int loading = (int) (storage.length * maxLoad);
        if (size >= loading) {
            increaseSize();
        }
        while (storage[index] != null) {
            if (storage[index].getKey() == key) {
                V temp = storage[index].getData();
                storage[index].setData(value);
                return temp;
            }
            ++index;
            index %= storage.length;
        }
        storage[index] = new Entry(key, value);
        ++size;
        return value;
    }

    @Override
    public V get(long key) {
        int index = index(hash(key));
        while (storage[index] != null) {
            if (storage[index].getKey() == key) 
                return storage[index].data;
            ++index;
            index %= storage.length;
        }
        return null;
    }

    @Override
    public V remove(long key) {
        int index = index(hash(key));
        while (storage[index] != null) {
            if (storage[index].getKey() == key) {
                Entry temp = storage[index];
                storage[index] = null;
                return (V) temp.getData();
            }
            ++index;
            index %= capacity;
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(long key) {
        return get(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        return Arrays.asList(storage)
                .stream()
                .filter(entry -> entry.getData().equals(value))
                .findFirst().isPresent();
    }

    @Override
    public long[] keys() {
        long keys[] = new long[size];
        for (int i = 0, j = 0; i < storage.length; i++) 
            if (storage[i] != null) 
                keys[j++] = storage[i].getKey();
        return keys;
    }

    @Override
    public V[] values() {
        List<V> values = new ArrayList();
        for (Long key : keys()) 
            values.add(get(key));
        return (V[]) values.toArray();
    }

    @Override
    public long size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, null);
        size = 0;
    }

    private long hash(long key) {
        return key;
    }

    private int index(long hash) {
        return (int) hash % storage.length;
    }

    private void increaseSize() {
        int newStorageSize = storage.length * 2;
        size = 0;
        Entry tempStorage[] = storage;
        storage = new Entry[newStorageSize];
        for (Entry entry : tempStorage) 
            if (entry != null) 
                put(entry.getKey(), (V) entry.getData());
    }

}
