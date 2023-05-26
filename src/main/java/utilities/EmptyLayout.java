/*
    This file is part of "Java TBL Editor".
    Copyright (C) 2023 Karim Elsheikh

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

/**
 * User: Karim Elsheikh
 * Date: 2023-May-26
 * Email: karim.esheikh@gmail.com
 * The following source file is based on "src/java.desktop/share/classes/java/awt/BorderLayout.java" from
 * the OpenJDK source released under GNU General Public License, version 2, with the Classpath Exception.
 */

/*
 * Copyright (c) 1995, 2021, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package utilities;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.io.Serial;

public class EmptyLayout implements LayoutManager2, java.io.Serializable {
	@Serial
	private static final long serialVersionUID = 1450459950442991138L;
	
	Dimension minimumLayoutSize_dim;
	Dimension preferredLayoutSize_dim;
	Dimension maximumLayoutSize_dim;
	public Dimension getMinimumLayoutSize_dim() {
		return minimumLayoutSize_dim;
	}
	
	public void setMinimumLayoutSize_dim(int minimumLayout_width, int minimumLayout_height) {
		minimumLayoutSize_dim = new Dimension(minimumLayout_width, minimumLayout_height);
	}
	
	public void setPreferredLayoutSize_dim(int preferredLayout_width, int preferredLayout_height) {
		preferredLayoutSize_dim = new Dimension(preferredLayout_width, preferredLayout_height);
	}
	
	public void setMaximumLayoutSize_dim(int maximumLayout_width, int maximumLayout_height) {
		maximumLayoutSize_dim = new Dimension(maximumLayout_width, maximumLayout_height);
	}

	public void setMinimumLayoutSize_dim(Dimension minimumLayoutSize_dim) {
		this.minimumLayoutSize_dim = minimumLayoutSize_dim;
	}

	public Dimension getPreferredLayoutSize_dim() {
		return preferredLayoutSize_dim;
	}

	public void setPreferredLayoutSize_dim(Dimension preferredLayoutSize_dim) {
		this.preferredLayoutSize_dim = preferredLayoutSize_dim;
	}

	public Dimension getMaximumLayoutSize_dim() {
		return maximumLayoutSize_dim;
	}

	public void setMaximumLayoutSize_dim(Dimension maximumLayoutSize_dim) {
		this.maximumLayoutSize_dim = maximumLayoutSize_dim;
	}
	
	public EmptyLayout() {
		this(null, null);
	}
	
	public EmptyLayout(Dimension minimumLayoutSize_dim, Dimension preferredLayoutSize_dim, Dimension maximumLayouSize_dim) {
		this.minimumLayoutSize_dim = minimumLayoutSize_dim;
		this.preferredLayoutSize_dim = preferredLayoutSize_dim;
		this.maximumLayoutSize_dim = maximumLayouSize_dim;
	}

	public EmptyLayout(Dimension minimumLayoutSize_dim, Dimension preferredLayoutSize_dim) {
		this.minimumLayoutSize_dim = minimumLayoutSize_dim;
		this.preferredLayoutSize_dim = preferredLayoutSize_dim;
		maximumLayoutSize_dim = new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}
	
	public EmptyLayout(Dimension minimumAndPreferredLayoutSize_dim) {
		minimumLayoutSize_dim = minimumAndPreferredLayoutSize_dim;
		preferredLayoutSize_dim = minimumAndPreferredLayoutSize_dim;
		maximumLayoutSize_dim = new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}
	
	public EmptyLayout(int minimumAndPreferredLayout_width, int minimumAndPreferredLayout_height) {
		this.minimumLayoutSize_dim = new Dimension(minimumAndPreferredLayout_width, minimumAndPreferredLayout_height);
		this.preferredLayoutSize_dim = new Dimension(minimumAndPreferredLayout_width, minimumAndPreferredLayout_height);
		maximumLayoutSize_dim = new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}
	
	public EmptyLayout(int minimumLayout_width, int minimumLayout_height, int preferredLayout_width, int preferredLayout_height) {
		this.minimumLayoutSize_dim = new Dimension(minimumLayout_width, minimumLayout_height);
		this.preferredLayoutSize_dim = new Dimension(preferredLayout_width, preferredLayout_height);
		maximumLayoutSize_dim = new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}
	
	public void addLayoutComponent(Component comp, Object constraints) {
		synchronized (comp.getTreeLock()) {
    	  addLayoutComponent((String)constraints, comp);
		}
	}
	
	@Deprecated
	public void addLayoutComponent(String name, Component comp) {
		synchronized (comp.getTreeLock()) {
		}
	}
	
	public void removeLayoutComponent(Component comp) {
	}
	
	public Component getLayoutComponent(Object constraints) {
		return null;
	}
	
	public Component getLayoutComponent(Container target, Object constraints) {
		return null;
	}
	
	public Object getConstraints(Component comp) {
		return null;
	}
	
	public Dimension minimumLayoutSize(Container target) {
		synchronized (target.getTreeLock()) {
			Dimension dim = new Dimension(0, 0);
			dim.width += minimumLayoutSize_dim.width;
			dim.height += minimumLayoutSize_dim.height;
			
//			Insets insets = target.getInsets();
//			dim.width += insets.left + insets.right;
//			dim.height += insets.top + insets.bottom;
	
			return dim;
		}
	}
	
	public Dimension preferredLayoutSize(Container target) {
		synchronized (target.getTreeLock()) {
			Dimension dim = new Dimension(0, 0);
			dim.width += preferredLayoutSize_dim.width;
			dim.height += preferredLayoutSize_dim.height;
			
//			Insets insets = target.getInsets();
//			dim.width += insets.left + insets.right;
//			dim.height += insets.top + insets.bottom;
	
			return dim;
		}
	}
	
	public Dimension maximumLayoutSize(Container target) {
//		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
		return maximumLayoutSize_dim;
	}
	
	public float getLayoutAlignmentX(Container parent) {
		return 0.5f;
	}
	
	public float getLayoutAlignmentY(Container parent) {
		return 0.5f;
	}
	
	public void invalidateLayout(Container target) {
	}
	
	public void layoutContainer(Container target) {
		synchronized (target.getTreeLock()) {}
	}
	
	public String toString() {
		return getClass().getName() + "[]";
	}
}
