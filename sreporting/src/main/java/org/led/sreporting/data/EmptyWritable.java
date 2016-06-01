package org.led.sreporting.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class EmptyWritable implements WritableComparable<EmptyWritable>, Serializable {
	static { WritableComparator.define(EmptyWritable.class, new Comparator()); }
	
    private static final EmptyWritable THIS = new EmptyWritable();

    private EmptyWritable() {}

    public static EmptyWritable get() { return THIS; }

    @Override
    public String toString() { return "(null)"; }

    @Override
    public int hashCode() { return 0; }

    public int compareTo(EmptyWritable other) { return 0; }

    @Override
    public boolean equals(Object other) { return other instanceof EmptyWritable; }

    public void readFields(DataInput in) throws IOException {}

    public void write(DataOutput out) throws IOException {}

    public static class Comparator extends WritableComparator { 
        public Comparator() { super(EmptyWritable.class); }

        @Override
        public int compare(byte[] b1, int s1, int l1,
                           byte[] b2, int s2, int l2) {
            assert 0 == l1;
            assert 0 == l2;
            return 0;
        }
    }    
}
