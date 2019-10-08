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
package org.greencubes.entity;

public class HealthChange {
	
	public static final HealthChange[] map = new HealthChange[10];
	
	public static final HealthChange NORMAL_DAMAGE = new HealthChange(0, true);
	public static final HealthChange CRITICAL_DAMAGE = new HealthChange(1, true);
	public static final HealthChange POISON = new HealthChange(2, true);
	public static final HealthChange HEAL = new HealthChange(3, false);
	
	public final int id;
	public final boolean isNegative;
	
	private HealthChange(int id, boolean isNegative) {
		this.id = id;
		this.isNegative = isNegative;
		map[id] = this;
	}
	
	public static HealthChange getById(int id) {
		return map[id];
	}
}
