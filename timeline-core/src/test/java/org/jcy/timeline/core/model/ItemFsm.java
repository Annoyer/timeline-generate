package org.jcy.timeline.core.model;

import nz.ac.waikato.modeljunit.*;
import org.jcy.timeline.core.CoreFsmTestRunner;
import org.junit.Assert;
import org.junit.Test;

public class ItemFsm implements FsmModel {

    private FakeItem itemA;

    private FakeItem itemB;

    private int A_MINUS_B;

    private EqualsTester<FakeItem> equalsTester;

    public ItemFsm() {
        itemA = new FakeItem("A", 1000);
        equalsTester = EqualsTester.newInstance(itemA);
    }

    @Override
    public Object getState() {
        if (A_MINUS_B > 0) {
            return "ItemA > ItemB";
        } else if (A_MINUS_B < 0) {
            return "ItemA < ItemB";
        } else if (itemB == null) {
            return "ItemB is NULL";
        } else if (itemA != itemB){
            return "ItemA == ItemB";
        } else {
            return "ItemA is ItemB";
        }
    }

    @Override
    public void reset(boolean testing) {
        itemB = null;
        A_MINUS_B = 0;
    }

    @Action
    public void sameObject() {
        itemB = itemA;
        doCompare();
        Assert.assertEquals(0, A_MINUS_B);
        equalsTester.assertEqual(itemA, itemB);
    }

    @Action
    public void idEqualsTimestampEquals() {
        itemB = new FakeItem(itemA.getId(), itemA.getTimeStamp());
        doCompare();
        Assert.assertEquals(0, A_MINUS_B);
        equalsTester.assertEqual(itemA, itemB);
    }
    

    @Action
    public void idGreaterTimestampEquals() {
        itemB = new FakeItem("B", itemA.getTimeStamp());
        doCompare();
        Assert.assertTrue(A_MINUS_B < 0);
        Assert.assertNotEquals(itemA, itemB);
    }

    @Action
    public void idSmallerTimestampEquals() {
        itemB = new FakeItem("0", itemA.getTimeStamp());
        doCompare();
        Assert.assertTrue( A_MINUS_B > 0);
        Assert.assertNotEquals(itemA, itemB);
    }

    @Action
    public void idEqualsTimestampSmaller() {
        itemB = new FakeItem(itemA.getId(), itemA.getTimeStamp()-1);
        doCompare();
        Assert.assertTrue(A_MINUS_B > 0);
        Assert.assertNotEquals(itemA, itemB);
    }

    @Action
    public void idGreaterTimestampSmaller() {
        itemB = new FakeItem("B", itemA.getTimeStamp()-1);
        doCompare();
        Assert.assertTrue(A_MINUS_B > 0);
        Assert.assertNotEquals(itemA, itemB);
    }

    @Action
    public void idSmallerTimestampSmaller() {
        itemB = new FakeItem("0", itemA.getTimeStamp()-1);
        doCompare();
        Assert.assertTrue( A_MINUS_B > 0);
        Assert.assertNotEquals(itemA, itemB);
    }

    @Action
    public void idEqualsTimestampGreater() {
        itemB = new FakeItem(itemA.getId(), itemA.getTimeStamp()+1);
        doCompare();
        Assert.assertTrue(A_MINUS_B < 0);
        Assert.assertNotEquals(itemA, itemB);
    }

    @Action
    public void idGreaterTimestampGreater() {
        itemB = new FakeItem("B", itemA.getTimeStamp()+1);
        doCompare();
        Assert.assertTrue(A_MINUS_B < 0);
        Assert.assertNotEquals(itemA, itemB);
    }

    @Action
    public void idSmallerTimestampGreater() {
        itemB = new FakeItem("0", itemA.getTimeStamp()+1);
        doCompare();
        Assert.assertTrue( A_MINUS_B < 0);
        Assert.assertNotEquals(itemA, itemB);
    }

    public boolean sameObjectGuard() {
        return !this.compareGuard();
    }

    public boolean idEqualsTimestampEqualsGuard() {
        return this.compareGuard();
    }
    
    public boolean idGreaterTimestampEqualsGuard() {
        return this.compareGuard();
    }

    public boolean idSmallerTimestampEqualsGuard() {
        return this.compareGuard();
    }

    public boolean idEqualsTimestampSmallerGuard() {
        return this.compareGuard();
    }
    
    public boolean idGreaterTimestampSmallerGuard() {
        return this.compareGuard();
    }

    public boolean idSmallerTimestampSmallerGuard() {
        return this.compareGuard();
    }

    public boolean idEqualsTimestampGreaterGuard() {
        return this.compareGuard();
    }

    public boolean idGreaterTimestampGreaterGuard() {
        return this.compareGuard();
    }
    
    public boolean idSmallerTimestampGreaterGuard() {
        return this.compareGuard();
    }

    private boolean compareGuard() {
        return this.getState() == "ItemB is NULL";
    }

    private void doCompare() {
        A_MINUS_B = itemA.compareTo(itemB);
    }

    @Test
    public void runTest() {
        CoreFsmTestRunner.runTest(this, "item-fsm.dot");
    }
}
