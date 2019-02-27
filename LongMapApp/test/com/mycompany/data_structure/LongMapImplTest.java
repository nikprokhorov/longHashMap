package com.mycompany.data_structure;

import longhashmap.structure.LongMapImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LongMapImplTest {

    private LongMapImpl<String> map;

    public LongMapImplTest() {
    }

    @Before
    public void setUp() {
        map = new LongMapImpl<>();
        for (long i = 0; i < 1000000; i++) {
            map.put(i, ("test" + i));
        }
        map.put(10000111, null);
    }

    @After
    public void tearDown() {
       map.clear();
    }

    @Test
    public void testGet() {
        assertEquals(map.get(1), "test1");
    }

    @Test
    public void testGetByUnsavedKey() {
        assertEquals(null, map.get(1000001));
    }

    @Test
    public void testGetNullableValue() {
        assertNull(map.get(10000111));
    }

    @Test
    public void testPutLongValue() {
        long key = 1000001;
        String value = "testValueEight";
        map.put(key, value);
        assertEquals(value, map.get(key));
    }

    @Test
    public void testPutByPuttingTenEntries() {
        long sizeBeforePuting = map.size();
        for (long key = 1000001; key <= 1000010; key++) {
            String value = "testValue " + key;
            map.put(key, value);
        }
        long sizeAfterPuting = map.size();
        assertTrue((sizeAfterPuting - sizeBeforePuting) == 10);
    }

    @Test
    public void testRemoveByLongKey() {
        String actualResult = map.remove(2);
        assertEquals("test2", actualResult);
    }



    @Test
    public void testIsEmptyByNotEmptyMap() {
        assertFalse(map.isEmpty());
    }

    @Test
    public void testIsEmptyAfterClearing() {
        map.clear();
        assertTrue(map.isEmpty());
        assertTrue(map.size() == 0);
    }

    @Test
    public void testContainsValue() {
        assertTrue( map.containsValue("test2"));
    }

    @Test
    public void testKeys() {
        long keys[] = map.keys();
        assertTrue(keys.length != 0);
    }

    @Test
    public void testSize() {
        assertTrue(map.size() != 0);
    }

    @Test
    public void testSizeAfterClearing() {
        map.clear();
    }

    @Test
    public void testSizeAfterAddingNewEntry() {
        long sizeBeforePuting = map.size();
        map.put(1000001, "test1000001");
        long sizeAfterPuting = map.size();
        assertTrue((sizeAfterPuting - sizeBeforePuting) == 1);
    }

    @Test
    public void testClearByNonEmptyMap() {
        map.clear();
        assertTrue(map.keys().length == 0 ); 
    }

}
