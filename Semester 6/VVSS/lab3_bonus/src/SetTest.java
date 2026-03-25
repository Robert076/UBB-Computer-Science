import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SetTest {

    // Black-Box: Normal addition
    @Test
    public void testAddSingleValue() {
        Set s = new Set(5);
        assertTrue(s.AddAValue(3), "Should return true on successful addition");
        assertEquals(1, s.nVS, "Set size should be 1");
    }

    // Black-Box & White-Box: Duplicate prevention (Covers the 'if' branch in
    // AddAValue)
    @Test
    public void testAddDuplicateValue() {
        Set s = new Set(5);
        s.AddAValue(3);
        assertFalse(s.AddAValue(3), "Should return false when adding a duplicate");
        assertEquals(1, s.nVS, "Set size should remain 1");
    }

    // White-Box: Checking value existence (Covers the while loop in IsInTheSet)
    @Test
    public void testIsInTheSet() {
        Set s = new Set(5);
        s.AddAValue(10);
        assertTrue(s.IsInTheSet(10), "Should return true for an existing element");
        assertFalse(s.IsInTheSet(99), "Should return false for a non-existing element");
    }

    // Black-Box: Boundary testing for capacity limits
    @Test
    public void testCapacityLimit() {
        Set s = new Set(2);
        assertTrue(s.AddAValue(1));
        assertTrue(s.AddAValue(2));
        // Version A will throw an ArrayIndexOutOfBoundsException here instead of
        // handling it gracefully.
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            s.AddAValue(3);
        }, "Original code does not check array bounds before adding.");
    }
}