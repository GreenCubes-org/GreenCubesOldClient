/*
 * Copyright 2015 Eiren 'Eirenliel' Rain and GreenCubes.org
 * authors
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall
 * be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.greencubes.util.collections;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.RandomAccess;

@SuppressWarnings("unchecked")
public class FastList<E> extends AbstractList<E> implements RandomAccess, Cloneable {
	
	public static final int MAX_ARRAY_SIZE = 2147483639;
	
	protected int size = 0;
	protected Object[] array;

	public FastList(int capacity) {
		array = new Object[capacity];
	}

	public FastList() {
		this(5);
	}

	public FastList(Collection<E> source) {
		this(source.size());
		addAll(source);
	}
	
	public FastList(E[] source) {
		this(source.length);
		addAll(source);
	}
	
	private FastList(Object[] arr, int size) {
		this(size);
		System.arraycopy(arr, 0, array, 0, size);
	}

	private void checkBounds(int index) {
		if(index < 0 || index >= size)
			throw new ArrayIndexOutOfBoundsException(new StringBuilder("Index: ").append(index).append(", size: ").append(size).toString());
	}

	public void ensureCapacity(int numToFit) {
		if(array.length < size + numToFit)
			grow(numToFit + size);
	}

	private void grow(int i) {
		int j = array.length;
		int k = j + (j >> 1);
		if(k - i < 0)
			k = i;
		if(k - 2147483639 > 0)
			k = hugeCapacity(i);
		array = Arrays.copyOf(array, k);
	}

	private static int hugeCapacity(int i) {
		if(i < 0)
			throw new OutOfMemoryError("Huge capacity negative: " + i);
		else
			return i <= MAX_ARRAY_SIZE ? MAX_ARRAY_SIZE : 2147483647;
	}
	
	public void copyInto(Object[] anArray) {
        System.arraycopy(array, 0, anArray, 0, size);
    }

	@Override
	public E get(int index) {
		checkBounds(index);
		return (E) array[index];
	}
	
	public E unsafeGet(int index) {
		return (E) array[index];
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public int indexOf(Object obj) {
		for(int j = 0; j < size; j++)
			if(obj == array[j])
				return j;
		return -1;
	}

	@Override
	public int lastIndexOf(Object obj) {
		for(int j = size - 1; j >= 0; j--)
			if(obj == array[j])
				return j;
		return -1;
	}

	@Override
	public boolean contains(Object obj) {
		return indexOf(obj) >= 0;
	}

	public void trimToSize() {
		int i = array.length;
		if(size < i)
			array = Arrays.copyOf(array, size);
	}

	@Override
	public Object[] toArray() {
		return Arrays.copyOf(array, size);
	}

	@Override
	public <T> T[] toArray(T aobj[]) {
		if(aobj.length < size)
			return (T[]) Arrays.copyOf(array, size, aobj.getClass());
		System.arraycopy(array, 0, aobj, 0, size);
		if(aobj.length > size)
			aobj[size] = null;
		return aobj;
	}

	@Override
	public boolean add(E e) {
		ensureCapacity(1);
		array[size++] = e;
		return true;
	}

	@Override
	public E remove(int i) {
		checkBounds(i);
		E obj = (E) array[i];
		fastRemove(i);
		return obj;
	}

	@Override
	public boolean remove(Object obj) {
		for(int j = 0; j < size; j++)
			if(obj == array[j]) {
				fastRemove(j);
				return true;
			}
		return false;
	}

	protected void fastRemove(int i) {
		int j = size - i - 1;
		if(j > 0)
			System.arraycopy(array, i + 1, array, i, j);
		array[--size] = null;
	}

	@Override
	public void clear() {
		for(int i = 0; i < size; i++)
			array[i] = null;
		size = 0;
	}
	
	@Override
	public boolean addAll(Collection<? extends E> collection) {
		return addAll(size, collection);
	}
	
	public void addAll(E[] arr) {
		addAllInternal(size, arr, arr.length);
	}
	
	public void addAll(E[] arr, int limit) {
		addAllInternal(size, arr, limit);
	}
	
	public void addAll(int index, E[] arr) {
		addAllInternal(index, arr, arr.length);
	}
	
	public void addAll(int index, E[] arr, int limit) {
		addAllInternal(index, arr, limit);
	}
	
	private void addAllInternal(int index, Object[] arr, int limit) {
		if(limit > arr.length)
			limit = arr.length;
		if(limit == 1) {
			add(index, (E) arr[0]);
		} else if(arr.length > 0) {
			if(index >= size) {
				ensureCapacity(size - index + limit);
				System.arraycopy(arr, 0, array, index, limit);
				size = index + limit;
			} else {
				if(array.length < size + limit) {
					Object[] newArray = new Object[size + limit];
					System.arraycopy(array, 0, newArray, 0, index - 1);
					System.arraycopy(arr, 0, newArray, index, limit);
					System.arraycopy(array, index, newArray, index + limit, size - index);
					array = newArray;
				} else {
					System.arraycopy(array, index, array, index + 1, size - index);
					System.arraycopy(arr, 0, array, index, limit);
				}
				size += limit;
			}
		}
	}
	
	@Override
	public boolean addAll(int index, Collection<? extends E> collection) {
		if(collection.size() > 0) {
			if(collection instanceof FastList) {
				addAll(index, (E[]) ((FastList<? extends E>) collection).array, collection.size());
			} else if(collection instanceof RandomAccess) {
				Object[] arr = collection.toArray(new Object[0]);
				addAllInternal(index, arr, arr.length);
			} else {
				if(index >= size) {
					ensureCapacity(size - index + collection.size());
					Iterator<? extends E> iterator = collection.iterator();
					int i = index;
					while(iterator.hasNext())
						array[i++] = iterator.next();
					size = index + collection.size();
				} else {
					if(array.length < size + collection.size()) {
						Object[] newArray = new Object[size + collection.size()];
						System.arraycopy(array, 0, newArray, 0, index - 1);
						Iterator<? extends E> iterator = collection.iterator();
						int i = index;
						while(iterator.hasNext())
							newArray[i++] = iterator.next();
						System.arraycopy(array, index, newArray, index + collection.size(), size - index);
						array = newArray;
					} else {
						System.arraycopy(array, index, array, index + 1, size - index);
						Iterator<? extends E> iterator = collection.iterator();
						while(iterator.hasNext())
							array[index++] = iterator.next();
					}
					size += collection.size();
				}
			}
			return true;
		}
		return false;
	}
	
	@Override
	public void add(int index, E element) {
		if(index >= size) {
			ensureCapacity(size - index + 1);
			size = index + 1;
			array[index] = element;
		} else {
			if(array.length < size + 1) {
				Object[] newArray = new Object[size + 1];
				System.arraycopy(array, 0, newArray, 0, index - 1);
				newArray[index] = element;
				System.arraycopy(array, index, newArray, index + 1, size - index);
				array = newArray;
			} else {
				System.arraycopy(array, index, array, index + 1, size - index);
				array[index] = element;
			}
			size++;
		}
	}

	@Override
	public E set(int index, E element) {
		checkBounds(index);
		E oldValue = (E) array[index];
		array[index] = element;
		return oldValue;
	}
	
	@Override
	public FastList<E> clone() {
		return new FastList<E>(array, size);
	}

}
