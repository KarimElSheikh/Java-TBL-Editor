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
 */

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import utilities.EmptyLayout;

class CustomJTable extends JPanel {
	private static final long serialVersionUID = -1355759920215460651L;
	JTable table;
	JScrollPane scrollPane;
	JComponent parent;
	MyDataTableModel myDataTableModel;
	
	// UNUSED
	public CustomJTable() {
		super();
	}
	
	public CustomJTable(JTable table, JComponent parent, int width, int height) {
		super();
		this.parent = parent;
		this.scrollPane = new JScrollPane(table);
//		this.scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.scrollPane.setBorder(new EmptyBorder(0,0,0,0));
		this.table = table;
//		this.setLayout(null);
//		this.setBorder(new EmptyBorder(0,0,0,0));
		this.add(scrollPane);
		Insets insets = this.getInsets();
		this.scrollPane.setBounds(0 + insets.left, 0 + insets.top, width, height);
		insets = scrollPane.getInsets();
		this.table.setBounds(0 + insets.left, 0 + insets.top, width, height);
		insets = parent.getInsets();
		this.setBounds(0 + insets.left, 0 + insets.top, width, height);
	}
	
	public static CustomJTable createCustomJTable_withoutSettingsBounds(JTable table, int width, int height, JComponent parent) {
		CustomJTable cTable = new CustomJTable();
		cTable.parent = parent;
		cTable.scrollPane = new JScrollPane(table);
		cTable.scrollPane.setBorder(new EmptyBorder(0,0,0,0));
		cTable.table = table;
//		cTable.setLayout(null);
//		cTable.setBorder(new EmptyBorder(0,0,0,0));
		cTable.add(cTable.scrollPane);
		Insets insets = cTable.getInsets();
		cTable.scrollPane.setBounds(0 + insets.left, 0 + insets.top, width, height);
		insets = cTable.scrollPane.getInsets();
		cTable.table.setBounds(0 + insets.left, 0 + insets.top, width, height);
		return cTable;
	}
	
	// UNUSED
	public CustomJTable createAnotherReference() {
		CustomJTable customJTable = new CustomJTable();
		customJTable.table = this.table;
		customJTable.scrollPane = this.scrollPane;
		customJTable.parent = this.parent;
		customJTable.myDataTableModel = this.myDataTableModel;
		customJTable.setSize(this.getWidth(), this.getHeight());
		Component[] arr = this.getComponents();
		for (Component component : arr) {
			customJTable.add(component);
		}
		return customJTable;
	}
	
	public CustomJTable setBoundsOfChildren(int width, int height) {
		// POSSIBLE CODE
//		CustomJTable anotherReference = createCustomJTable_withoutSettingsBounds(table, this.getWidth(), this.getHeight(), parent);
		CustomJTable anotherReference = new CustomJTable(table, parent, this.getWidth(), this.getHeight());
		((JSplitPane) parent).setLeftComponent(anotherReference);
		anotherReference.setName("Strings table");
		return anotherReference;
	}
}

// UNUSED
class CustomJPanel extends JPanel {
	private static final long serialVersionUID = -4682183416882850466L;
	JPanel panel;
	JScrollPane scrollPane;
	JComponent parent;
	
	public CustomJPanel(JPanel panel, JComponent parent, int width, int height) {
		this(panel, parent, width, height, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}
	
	public CustomJPanel(JPanel panel, JComponent parent, int width, int height, int vsbPolicy, int hsbPolicy) {
		super();
		this.panel = panel;
		this.parent = parent;
		setLayout(null);
//		setLayout(new BorderLayout());
//		setBorder(new EmptyBorder(0,0,0,0));
		scrollPane = new JScrollPane(panel, vsbPolicy, hsbPolicy);
		scrollPane.setBorder(new EmptyBorder(0,0,0,0));
		add(scrollPane);
//		add(scrollPane, BorderLayout.CENTER);
		
		Insets insets = getInsets();
		scrollPane.setBounds(0 + insets.left, 0 + insets.top, width, height);
		insets = scrollPane.getInsets();
		panel.setBounds(0 + insets.left, 0 + insets.top, width, height);
//		insets = parent.getInsets();
//		setBounds(0 + insets.left, 0 + insets.top, width, height);
	}
	
	public CustomJPanel(JPanel panel, JComponent parent, int width, int height, boolean val) {
		super();
		this.panel = panel;
		this.parent = parent;
		if (!val) setLayout(null);
		else setLayout(new BorderLayout());
		setBorder(new EmptyBorder(0,0,0,0));
		scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBorder(new EmptyBorder(0,0,0,0));
		if (!val) add(scrollPane);
		else add(scrollPane, BorderLayout.CENTER);
		
		this.setSize(new Dimension(250, 0));
		Insets insets = getInsets();
		scrollPane.setBounds(0 + insets.left, 0 + insets.top, width, height);
	}
	
	public CustomJPanel setBounds2(JPanel panel, JComponent parent, int width, int height) {
		CustomJPanel anotherReference = new CustomJPanel(panel, parent, width, height, false);
		
		Insets insets = anotherReference.scrollPane.getInsets();
		anotherReference.panel.setBounds(0 + insets.left, 0 + insets.top, getWidth(), getHeight());
		
		((JSplitPane) parent).setRightComponent(anotherReference);
		return anotherReference;
	}
	
	public CustomJPanel setBoundsOfChildren(int width, int height) {
		CustomJPanel anotherReference = new CustomJPanel(panel, parent, this.getWidth(), this.getHeight());
		((JSplitPane) parent).setRightComponent(anotherReference);
		return anotherReference;
	}
}

class MyDataTable extends JTable {
	private static final long serialVersionUID = -5341596785911900040L;
	private int lastClickedRow;
	
	public MyDataTable(TableModel dm) {
		super(dm);
		lastClickedRow = -1;
	}
	
	MyDataTable(Object[][] data, String[] columnNames) {
		super(data, columnNames);
		lastClickedRow = -1;
	}
	
	public int getLastClickedRow() {
		return lastClickedRow;
	}
	
	public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
		super.changeSelection(rowIndex, columnIndex, toggle, extend);
		lastClickedRow = rowIndex;
	}
}

class MyDataTableModel implements TableModel {
	DefaultTableModel  originalModel;
	
	public MyDataTableModel() {
		originalModel = new DefaultTableModel();
	}
	
	public MyDataTableModel(int rowCount, int columnCount) {
		originalModel = new DefaultTableModel();
		originalModel.setRowCount(rowCount);
		originalModel.setColumnCount(columnCount);
	}
	
	public boolean isCellEditable(int row, int column){  
		return true;
	}
	
	public int getRowCount() {
		return originalModel.getRowCount();
	}
	
	public int getColumnCount() {
		return originalModel.getColumnCount();
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		return originalModel.getValueAt(rowIndex, columnIndex);
	}
	
	public void setDataVector(Vector<? extends Vector<?>> dataVector, Vector<?> columnIdentifiers) {
		originalModel.setDataVector(dataVector, columnIdentifiers);
	}
	
	public void setColumnIdentifiers(Vector<?> columnIdentifiers) {
		originalModel.setColumnIdentifiers(columnIdentifiers);
	}
	
	public String getColumnName(int column) {
		return originalModel.getColumnName(column);
	}
	
	public Class<?> getColumnClass(int columnIndex) {
		return originalModel.getColumnClass(columnIndex);
	}
	
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		originalModel.setValueAt(aValue, rowIndex, columnIndex);
	}
	
	public void addTableModelListener(TableModelListener l) {
		originalModel.addTableModelListener(l);
	}
	
	public void removeTableModelListener(TableModelListener l) {
		originalModel.removeTableModelListener(l);
		
	}
}

public class TBL_Editor extends JFrame implements ActionListener {
	private static final long serialVersionUID = 5704049276880668380L;
	
	private List<String> strings;
	private int nOfKeys;
	private MyDataTable dataTable;
	private MyDataTableModel dataTableModel;
	private Vector<String> dataColumnNames;
	private Vector<Vector<Object>> dataVec;
	private int lastPickedTableRow;
	private String file;
	
	private JTextField openFileBar;
	private JSplitPane middleArea;
	private CustomJTable table;
	private CustomJPanel eastPanel;
	private JPanel eastInnerPanel;
	private JLabel[] rightLabels;
	private JPanel[] rightPanels;
	private JCheckBox[] rightCheckBoxes;
	private JButton[] rightJButtons;
	private JPanel bottomArea;
	private JPanel bottomArea_1;
	private Button openFile, writeFile, close, fitColumnSizes;
	private JPanel bottomArea_2;
	private JPanel spaceJpanel1;
	private JPanel spaceJpanel2;
	private JPanel jTextAreaPanel;
	private JTextArea jTextArea;
	private JPanel bottomArea_3;

	private int clientAreaWidth;
	private int clientAreaHeight;
	
	private int desiredWindowWidth = 720;
	private int desiredWindowHeight = 540;
	@SuppressWarnings("unused") private int horizontalWindowInsetsAssumption = 16;
	@SuppressWarnings("unused") private int verticalWindowInsetsAssumption = 39;
	
	private int middleAreaDividerWidth = 14; // Default JSplitPlane divider width + 4 (2 pixel border on the left and the right).
	private int curRightColumnWidth = 250;
	private int curTableWidth = desiredWindowWidth - middleAreaDividerWidth - curRightColumnWidth; // 456
	
	private int openFileBarHeight = 18;
	private int bottomArea_1Height = 35;
	private int bottomArea_2Height = 18;
	private int bottomArea_3Height = 2;
	private int bottomAreaHeight = bottomArea_1Height + bottomArea_2Height + bottomArea_3Height; // 55
	private int curMiddleAreaHeight = desiredWindowHeight - openFileBarHeight - bottomAreaHeight; // 467
	
	private int maxRightColumnWidth = curRightColumnWidth; // 250
	private PropertyChangeListener dividerDragListener;
	private TBL_Editor this2 = this;
	
	public void createEastInnerPanel() {
		eastInnerPanel = new JPanel(null);
		eastInnerPanel.setFont(new Font("SansSerif", Font.BOLD, 14));
		int rowsInEastPanel1 = 4;
		rightLabels = new JLabel[rowsInEastPanel1];
		rightPanels = new JPanel[rowsInEastPanel1];
		rightCheckBoxes = new JCheckBox[rowsInEastPanel1];
		rightJButtons = new JButton[rowsInEastPanel1];
		for (int i = 0; i < rowsInEastPanel1; i++) {
			rightLabels[i] = new JLabel("TODO");
			rightCheckBoxes[i] = new JCheckBox("Check Box "  + (i + 1));
			int h = getHeightOfText("Check Box ", rightCheckBoxes[i].getFont());
			int w = getWidthOfText("Check Box ", rightCheckBoxes[i].getFont());
			rightJButtons[i] = new JButton("Button");
			rightPanels[i] = new JPanel(null);
			rightPanels[i].add(rightCheckBoxes[i]);
			rightPanels[i].add(rightJButtons[i]);
			rightPanels[i].setBorder(BorderFactory.createEtchedBorder());
			Insets insets = rightPanels[i].getInsets();
			rightCheckBoxes[i].setBounds(2 + insets.left, 4 + (22 - h), w + 34, h);
			rightJButtons[i].setBounds(102 + insets.left, 4 + insets.top, 82, 24);
			
			eastInnerPanel.add(rightLabels[i]);
			eastInnerPanel.add(rightPanels[i]);
			insets = eastInnerPanel.getInsets();
			rightLabels[i].setBounds(2 + insets.left, 2 + i * 50 + insets.top, 60, 36);
			rightPanels[i].setBounds(40 + insets.left, 2 + i * 50 + insets.top, 194, 36);
		}
	}
	
	public void addListeners() {
		dividerDragListener = new PropertyChangeListener() {
	        public void propertyChange(PropertyChangeEvent pce) {
	        	// DEBUGGING
//	        	System.out.println("Table width before moving the divider: " + table.getWidth() + "eastPanelWidth before moving the divider: " + eastPanel.getWidth());
        		int newDivLoc = (int) pce.getNewValue();
        		Insets insets = middleArea.getInsets();
        		BasicSplitPaneDivider divider = ((BasicSplitPaneUI) middleArea.getUI()).getDivider();
        		clientAreaWidth = this2.getWidth() - this2.getInsets().left - this2.getInsets().right;
        		clientAreaHeight = this2.getHeight() - this2.getInsets().top - this2.getInsets().bottom;
    			int newRightColumnWidth = clientAreaWidth - newDivLoc - middleAreaDividerWidth;
    			
        		if (newRightColumnWidth < maxRightColumnWidth) {
        			curTableWidth = newDivLoc;
					EmptyLayout l = (EmptyLayout) dataTable.getLayout();
					l.setMinimumLayoutSize_dim(curTableWidth, curMiddleAreaHeight);
        			l.setPreferredLayoutSize_dim(curTableWidth, curMiddleAreaHeight);
					table.setBounds(0 + insets.left, 0 + insets.top, curTableWidth, curMiddleAreaHeight);
        			divider.setBounds(curTableWidth + insets.left, 0 + insets.top, middleAreaDividerWidth, curMiddleAreaHeight);
        			curRightColumnWidth = newRightColumnWidth;
        			eastPanel.setBounds(newDivLoc + middleAreaDividerWidth + insets.left, 0 + insets.top, newRightColumnWidth, curMiddleAreaHeight);
        			insets = table.getInsets();
        			table.scrollPane.setBounds(0 + insets.left, 0 + insets.top, curTableWidth, curMiddleAreaHeight);
        			insets = eastPanel.getInsets();
        			eastPanel.scrollPane.setBounds(0 + insets.left, 0 + insets.top, newRightColumnWidth, curMiddleAreaHeight);
        			middleArea.setLeftComponent(table);
        			middleArea.setRightComponent(eastPanel);
        		} else {
        			curTableWidth = clientAreaWidth - maxRightColumnWidth - middleAreaDividerWidth;
        			EmptyLayout l = (EmptyLayout) dataTable.getLayout();
        			l.setMinimumLayoutSize_dim(curTableWidth, curMiddleAreaHeight);
        			l.setPreferredLayoutSize_dim(curTableWidth, curMiddleAreaHeight);
        			table.setBounds(0 + insets.left, 0 + insets.top, curTableWidth, curMiddleAreaHeight);
        			curRightColumnWidth = maxRightColumnWidth;
        			eastPanel.setBounds(curTableWidth + middleAreaDividerWidth + insets.left, 0 + insets.top, maxRightColumnWidth, curMiddleAreaHeight);
        			divider.setBounds(curTableWidth + insets.left, 0 + insets.top, middleAreaDividerWidth, curMiddleAreaHeight);
//        			middleArea.setDividerLocation(curTableWidth + insets.left);
        			insets = table.getInsets();
        			table.scrollPane.setBounds(0 + insets.left, 0 + insets.top, curTableWidth, curMiddleAreaHeight);
        			insets = eastPanel.getInsets();
        			eastPanel.scrollPane.setBounds(0 + insets.left, 0 + insets.top, maxRightColumnWidth, curMiddleAreaHeight);
        			middleArea.setLeftComponent(table);
        			middleArea.setRightComponent(eastPanel);
        		}
        		// DEBUGGING
//    			System.out.println("Table width after moving the divider: " + table.getWidth() + "eastPanelWidth after moving the divider: " + eastPanel.getWidth());
//        		printDebugGlobalVars(1);
//        		System.out.println();
	        }
	    };
	    
//	    this.addWindowStateListener(new java.awt.event.WindowStateListener() {
//	        public void windowStateChanged(java.awt.event.WindowEvent evt) {
//	        	table.setMinimumSize(new Dimension(curTableWidth, 0));
//	        }
//	    });
		
		middleArea.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, dividerDragListener);
		
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent componentEvent) {
//				System.out.println("OUCHYYY AWTSCH!!!!!");
				Insets insets = this2.getInsets();
				clientAreaWidth = this2.getWidth() - insets.left - insets.right;
				clientAreaHeight = this2.getHeight() - insets.top - insets.bottom;
//				System.out.println(clientAreaWidth);
//				System.out.println(clientAreaHeight);
				
				openFileBar.setBounds(0, 0, clientAreaWidth, openFileBarHeight);
				curMiddleAreaHeight = clientAreaHeight - openFileBarHeight - bottomAreaHeight;
				middleArea.setBounds(0, openFileBarHeight, clientAreaWidth, curMiddleAreaHeight);
				bottomArea.setBounds(0, openFileBarHeight + curMiddleAreaHeight, clientAreaWidth, bottomAreaHeight);
				insets = bottomArea.getInsets();
				bottomArea_1.setBounds(0 + insets.left, 0 + insets.top, clientAreaWidth, bottomArea_1Height);
				distributeButtonsEvenlyInBottomArea_1();
				bottomArea_2.setBounds(0 + insets.left, bottomArea_1Height + insets.top, clientAreaWidth, bottomArea_2Height);
				bottomArea_3.setBounds(0 + insets.left, bottomArea_1Height + bottomArea_2Height + insets.top, clientAreaWidth, bottomArea_1Height);
				insets = bottomArea_2.getInsets();
				jTextAreaPanel.setBounds(40 + insets.left, 0 + insets.top, clientAreaWidth - 40, bottomArea_2Height);
				insets = jTextAreaPanel.getInsets();
				jTextArea.setBounds(0 + insets.left, 0 + insets.top, clientAreaWidth - 40, bottomArea_2Height);
				
				int availableSpace = Math.max(0, clientAreaWidth - curRightColumnWidth - middleAreaDividerWidth);
//    			table.setBounds(0 + insets.left, 0 + insets.top, availableSpace, clientAreaHeight);
//				table = table.setBoundsOfChildren(availableSpace, clientAreaHeight);
    			curTableWidth = availableSpace;
				EmptyLayout l = (EmptyLayout) dataTable.getLayout();
				l.setMinimumLayoutSize_dim(curTableWidth, curMiddleAreaHeight);
    			l.setPreferredLayoutSize_dim(curTableWidth, curMiddleAreaHeight);
    			table.setMinimumSize(new Dimension(clientAreaWidth - maxRightColumnWidth - middleAreaDividerWidth, 0));
				insets = middleArea.getInsets();
				table.setBounds(0 + insets.left, 0 + insets.top, curTableWidth, curMiddleAreaHeight);
    			
				
//    			curRightColumnWidth = clientAreaWidth - availableSpace - middleAreaDividerWidth;
//    			eastPanel.setBounds(table.getWidth() + middleAreaDividerWidth + insets.left, 0 + insets.top, Math.min(eastPanel.getWidth(), curRightColumnWidth), clientAreaHeight);
//    			eastPanel = eastPanel.setBoundsOfChildren(eastPanel.getWidth(), clientAreaHeight);
//    			reinitializeMiddleArea();
    			BasicSplitPaneDivider divider = ((BasicSplitPaneUI) middleArea.getUI()).getDivider();
    			divider.setBounds(curTableWidth + insets.left, 0 + insets.top, middleAreaDividerWidth, curMiddleAreaHeight);
    			middleArea.setDividerLocation(curTableWidth + insets.left);
//    			eastPanel.setBounds(table.getWidth() + middleAreaDividerWidth + insets.left, 0 + insets.top, Math.min(eastPanel.getWidth(), curRightColumnWidth), clientAreaHeight);
    			eastPanel.setBounds(curTableWidth + middleAreaDividerWidth + insets.left, 0 + insets.top, curRightColumnWidth, clientAreaHeight);
    			insets = table.getInsets();
    			table.scrollPane.setBounds(0 + insets.left, 0 + insets.top, curTableWidth, curMiddleAreaHeight);
    			insets = eastPanel.getInsets();
    			eastPanel.scrollPane.setBounds(0 + insets.left, 0 + insets.top, curRightColumnWidth, curMiddleAreaHeight);
    			middleArea.setLeftComponent(table);
    			middleArea.setRightComponent(eastPanel);
			}
		});
	}
	
	/**
	* Constructor: create the GUI and the table.
	 * @throws InterruptedException 
	*/
	public TBL_Editor() throws InterruptedException {
		super("TBL editor");
		// POSSIBLE CODE
//		this.setExtendedState(this.getExtendedState() | Frame.MAXIMIZED_BOTH);
		this.pack();
		this.setSize(8 + curTableWidth + middleAreaDividerWidth + curRightColumnWidth + 8,
				31 + openFileBarHeight + curMiddleAreaHeight + bottomAreaHeight + 8);

		Insets insets = this.getInsets();
		clientAreaWidth = this.getWidth() - insets.left - insets.right;
		clientAreaHeight = this.getHeight() - insets.top - insets.bottom;
		
		// DEBUGGING
//		System.out.println(8 + tableWidth + middleAreaDividerWidth + curRightColumnWidth + 8);
//		System.out.println(31 + openFileBarHeight + curMiddleAreaHeight + bottomAreaHeight + 8);
//		System.out.println(this.getInsets());
//		System.out.println(clientAreaWidth);
//		System.out.println(clientAreaHeight);
//		Toolkit.getDefaultToolkit().setDynamicLayout(false);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				this2.dispose();
				System.exit(0);
			}
		});
		lastPickedTableRow = -1;
		
		dataColumnNames = new Vector<>(2);
		dataColumnNames.add("#");
		dataColumnNames.add("String");
		
		openFileBar = new JTextField();
		openFileBar.setFont(new Font("MonoSpaced", Font.PLAIN, 12));
		openFileBar.setEditable(false);
		openFileBar.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		openFileBar.setBorder(new MatteBorder(2,0,1,0, Color.decode("#B8CFE5")));
		
		strings = new ArrayList<>(); initFileTable();
		
		middleArea = new JSplitPane(); middleArea.setLayout(null);
		middleArea.setBorder(new MatteBorder(1,0,0,0, Color.decode("#B8CFE5")));
		middleArea.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		middleArea.setEnabled(true);
		BasicSplitPaneDivider divider = ((BasicSplitPaneUI) middleArea.getUI()).getDivider();
		
		curTableWidth = clientAreaWidth - middleAreaDividerWidth - curRightColumnWidth;
		table = new CustomJTable(dataTable, middleArea, curTableWidth, curMiddleAreaHeight);
		table.setLayout(null);
		table.setBorder(new EmptyBorder(0,0,0,0));
		middleArea.setLeftComponent(table);
		divider.setBorder(new MatteBorder(0,2,0,2, Color.decode("#B8CFE5")));
		middleArea.setRightComponent(eastPanel);
		
		eastInnerPanel = new JPanel(new EmptyLayout(maxRightColumnWidth, curMiddleAreaHeight));
		eastInnerPanel.setFont(new Font("SansSerif", Font.BOLD, 14));
		
		int rowsInEastPanel1 = 4;
		rightLabels = new JLabel[rowsInEastPanel1];
		rightPanels = new JPanel[rowsInEastPanel1];
		rightCheckBoxes = new JCheckBox[rowsInEastPanel1];
		rightJButtons = new JButton[rowsInEastPanel1];
		for (int i = 0; i < rowsInEastPanel1; i++) {
			rightLabels[i] = new JLabel("TODO");
			rightCheckBoxes[i] = new JCheckBox("Check Box "  + (i + 1));
			int h = getHeightOfText("Check Box ", rightCheckBoxes[i].getFont());
			int w = getWidthOfText("Check Box ", rightCheckBoxes[i].getFont());
			rightJButtons[i] = new JButton("Button");
			rightPanels[i] = new JPanel(null);
			rightPanels[i].add(rightCheckBoxes[i]);
			rightPanels[i].add(rightJButtons[i]);
			rightPanels[i].setBorder(BorderFactory.createEtchedBorder());
			insets = rightPanels[i].getInsets();
			rightCheckBoxes[i].setBounds(2 + insets.left, 4 + (22 - h), w + 34, h);
			rightJButtons[i].setBounds(102 + insets.left, 4 + insets.top, 82, 24);
			
			eastInnerPanel.add(rightLabels[i]);
			eastInnerPanel.add(rightPanels[i]);
			insets = eastInnerPanel.getInsets();
			rightLabels[i].setBounds(2 + insets.left, 2 + i * 50 + insets.top, 60, 36);
			rightPanels[i].setBounds(40 + insets.left, 2 + i * 50 + insets.top, 194, 36);
		}
		
		eastPanel = new CustomJPanel(eastInnerPanel, middleArea, curRightColumnWidth, curMiddleAreaHeight, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		eastPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		eastPanel.setFont(new Font("SansSerif", Font.BOLD, 14));
		table.setMinimumSize(new Dimension(curTableWidth, 0));
		
		insets = middleArea.getInsets();
		table.setBounds(0 + insets.left, 0 + insets.top, curTableWidth, curMiddleAreaHeight);
//		table = table.setBoundsOfChildren(tableWidth, clientAreaHeight);
		divider.setBounds(curTableWidth + insets.left, 0 + insets.top, 10, curMiddleAreaHeight);
		eastPanel.setBounds(curTableWidth + middleAreaDividerWidth + insets.left, 0 + insets.top, curRightColumnWidth, curMiddleAreaHeight);
		
//		eastPanel.add(eastInnerPanel);
//		insets = eastPanel.getInsets();
//		eastInnerPanel.setBounds(0 + insets.left, 0 + insets.top, maxRightColumnWidth, curMiddleAreaHeight);
		
		bottomArea = new JPanel(null);
		bottomArea.setBorder(new MatteBorder(2,0,0,0, Color.decode("#B8CFE5")));
		bottomArea.setFont(new Font("SansSerif", Font.BOLD, 14));
		bottomArea.setBackground(Color.ORANGE);
		
		spaceJpanel1 = new JPanel();
		spaceJpanel1.setBackground(Color.GREEN);
		spaceJpanel2 = new JPanel();
		spaceJpanel2.setBackground(Color.BLUE);
		
		bottomArea_2 = new JPanel(null);
		bottomArea_2.setBackground(Color.BLUE);
		bottomArea_2.setLayout(null);
		bottomArea_2.setBorder(new EmptyBorder(0, 0, 0, 0));
		bottomArea_2.add(spaceJpanel1);
		bottomArea_2.add(spaceJpanel2);
		jTextAreaPanel = new JPanel(null);
		jTextAreaPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		jTextAreaPanel.setBackground(Color.RED);
		jTextArea = new JTextArea();
		jTextArea.setBorder(new EmptyBorder(0, 2, 0, 0));
		jTextArea.setEditable(false);
		jTextArea.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		jTextAreaPanel.add(jTextArea);
		insets = jTextAreaPanel.getInsets();
		jTextArea.setBounds(0 + insets.left, 0 + insets.top, clientAreaWidth - 40, bottomArea_2Height);
		bottomArea_2.add(jTextAreaPanel);
		insets = bottomArea_2.getInsets();
		spaceJpanel1.setBounds(0 + insets.left, 0 + insets.top, 20, bottomArea_2Height);
		spaceJpanel2.setBounds(20 + insets.left, 0 + insets.top,  20, bottomArea_2Height);
		jTextAreaPanel.setBounds(40 + insets.left, 0 + insets.top, clientAreaWidth - 40, bottomArea_2Height);
		
		bottomArea_1 = new JPanel(null);
		bottomArea_1.setBorder(new MatteBorder(0,0,2,0, Color.decode("#B8CFE5")));
		bottomArea_1.setFont(new Font("SansSerif", Font.BOLD, 14));
		
		bottomArea_3 = new JPanel(null);
		bottomArea_3.setBackground(Color.decode("#B8CFE5"));
		
		bottomArea.add(bottomArea_1);
		bottomArea.add(bottomArea_2);
		bottomArea.add(bottomArea_3);
		insets = bottomArea.getInsets();
		bottomArea_1.setBounds(0 + insets.left, 0 + insets.top, clientAreaWidth, bottomArea_1Height);
		bottomArea_2.setBounds(0 + insets.left, bottomArea_1Height + insets.top, clientAreaWidth, bottomArea_2Height);
		bottomArea_3.setBounds(0 + insets.left, bottomArea_1Height + bottomArea_2Height + insets.top, clientAreaWidth, bottomArea_3Height);
		
		setUpButtons();
		distributeButtonsEvenlyInBottomArea_1();

		openFileBar.setName("CurOpen File Bar");
		middleArea.setName("Middle Area");
		bottomArea.setName("Bottom Area");
		table.setName("Strings table");
		
		this.setLayout(null);
		this.add(openFileBar);
		this.add(middleArea);
		this.add(bottomArea);
		openFileBar.setBounds(0, 0, clientAreaWidth, openFileBarHeight);
		middleArea.setBounds(0, openFileBarHeight, clientAreaWidth, curMiddleAreaHeight);
		bottomArea.setBounds(0, openFileBarHeight + curMiddleAreaHeight, clientAreaWidth, bottomAreaHeight);

		addListeners();
		// POSSIBLY EXTENDING
		this.addHierarchyListener(new HierarchyListener() {
			public void hierarchyChanged(HierarchyEvent e) {
				if (e.getChangeFlags()==HierarchyEvent.SHOWING_CHANGED) {
//					printDebugGlobalVars(1);
				}
			}
		});
	}
	
	public void printDebugGlobalVars(int dbgMsgNum) {
		if (dbgMsgNum == 1) {
			System.out.println("table.getWidth()="+table.getWidth()+", middleAreaDividerWidth="+middleAreaDividerWidth+", eastPanel.getWidth()="+eastPanel.getWidth());
		} else if (dbgMsgNum == 2) {
			
		}
	}
	
	public int getClientAreaWidth() {
		return clientAreaWidth;
	}

	public void setClientAreaWidth(int clientAreaWidth) {
		this.clientAreaWidth = clientAreaWidth;
	}
	
	public void handleSelectionEvent(ListSelectionEvent e) {
		boolean valueAdjusting = e.getValueIsAdjusting();
		if (valueAdjusting) {
			return;
		}
		
		lastPickedTableRow = dataTable.getLastClickedRow();
		if (lastPickedTableRow != -1 && lastPickedTableRow < strings.size()) jTextArea.setText((String) dataTable.getValueAt(lastPickedTableRow, 1));
	}
	
	public void initFileTable() {
		dataTable = new MyDataTable(new MyDataTableModel(0, 2));
		dataTable.setLayout(new EmptyLayout(curTableWidth, curMiddleAreaHeight));
		dataTableModel = (MyDataTableModel) dataTable.getModel();
		dataVec = new Vector<>(strings.size()); refreshFileTableData();
		dataTable.getTableHeader().setReorderingAllowed(false);
		dataTableModel.addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent tme) {
				if (tme.getType() == TableModelEvent.UPDATE) {
					if (tme.getColumn() == 1) {
						strings.set(tme.getFirstRow(), (String) dataTableModel.getValueAt(tme.getFirstRow(),
								tme.getColumn()));
					}
				}
			}
		});
		
		ListSelectionModel selectionModel = dataTable.getSelectionModel();
		selectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				handleSelectionEvent(e);
			}
		});
	}
	
	public void setUpButtons() {
		close = new Button("Close TBL file");
		openFile = new Button("Open TBL file");
		writeFile = new Button("Save As");
		fitColumnSizes = new Button("Auto-fit column sizes");
		close.addActionListener(this);
		openFile.addActionListener(this);
		writeFile.addActionListener(this);
		fitColumnSizes.addActionListener(this);
		bottomArea_1.add(openFile);
		bottomArea_1.add(writeFile);
		bottomArea_1.add(fitColumnSizes);
		bottomArea_1.add(close);
	}
	
	public void distributeButtonsEvenlyInBottomArea_1() {
		int nOfButtons = 4;
		Button[] buttons = new Button[nOfButtons];
		int[] widths = new int[nOfButtons];
		buttons[0] = openFile;
		buttons[1] = writeFile;
		buttons[2] = fitColumnSizes;
		buttons[3] = close;
		widths[0] = getWidthOfText(openFile.getLabel(), openFile.getFont()) + 11;
		widths[1] = getWidthOfText(writeFile.getLabel(), writeFile.getFont()) + 11;
		widths[2] = getWidthOfText(fitColumnSizes.getLabel(), fitColumnSizes.getFont()) + 11;
		widths[3] = getWidthOfText(close.getLabel(), close.getFont()) + 11;
		
		Insets insets = bottomArea_1.getInsets();
		int sumOfWidths = 0;
		for (int i = 0; i < nOfButtons; i++) sumOfWidths += widths[i];
		int availableSpace = Math.max(0, clientAreaWidth - sumOfWidths);
		int firstButtonOffset = 4, offsetFromTheTop = 6;
		int leftOffset = Math.min(firstButtonOffset, availableSpace);
		availableSpace -= leftOffset;
		for (int i = 0; i < nOfButtons; i++) {
			if (i != 0) {
				leftOffset += availableSpace / nOfButtons + (availableSpace % nOfButtons < i ? 1 : 0);
			}
			int height = getHeightOfText(buttons[i].getLabel(), buttons[i].getFont()) + 4;
			buttons[i].setBounds(leftOffset + insets.left, offsetFromTheTop + insets.top, widths[i], height);
			leftOffset += widths[i];
		}
	}
	
	public void refreshFileTableData() {
		dataVec.setSize(strings.size());
		for (int i = 0; i < strings.size(); i++) {
			if (dataVec.get(i) == null) {
				Vector<Object> v = new Vector<>(2);
				v.add(i+1);
				v.add(strings.get(i));
				dataVec.set(i, v);
			} else {
				dataVec.get(i).set(1, strings.get(i));
			}
		}
		dataTableModel.setDataVector(dataVec, dataColumnNames);
	}
	
	public void autoFitColumnSizes() {
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);     
		Font font = dataTable.getFont();
		
		int maxTextWidth = 0;
		for (int i = 0; i < dataVec.size(); i++) {
			int textwidth = (int)(font.getStringBounds((String) dataVec.get(i).get(1), frc).getWidth());
			maxTextWidth = Math.max(maxTextWidth, textwidth);
		}
		TableColumnModel columnModel = dataTable.getColumnModel();
		columnModel.getColumn(1).setPreferredWidth(maxTextWidth + 13);
	}
	
	// POSSIBLE CODE
//	public static int getWidthOfText(String text, Font font) {
//		AffineTransform affinetransform = new AffineTransform();     
//		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);     
//		return (int) (Math.round(font.getStringBounds(text, frc).getWidth()));
//	}
	
	public static int getWidthOfText(String text, Font font) {
		BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gc = image.createGraphics();
		Rectangle2D r = font.getStringBounds(text, gc.getFontRenderContext());
		return (int) (Math.round(r.getWidth()));
	}
	
	// POSSIBLE CODE
//	public static int getHeightOfText(String text, Font font) {
//		AffineTransform affinetransform = new AffineTransform();     
//		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);     
//		return (int) (Math.round(font.getStringBounds(text, frc).getHeight()));
//	}
	
	public static int getHeightOfText(String text, Font font) {
		BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gc = image.createGraphics();
		Rectangle2D r = font.getStringBounds(text, gc.getFontRenderContext());
		return (int) (Math.round(r.getHeight()));
	}
	
	public List<String> openFile(String file) throws IOException {
		return readStarcraftTBL(file);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == close) {
			strings = new ArrayList<>(); nOfKeys = 0;
			refreshFileTableData();
			lastPickedTableRow = -1;
			openFileBar.setText("");
			jTextArea.setText("");
		} else if (e.getSource() == openFile) {
			Display display = new Display();
			Shell shell = new Shell(display);
			org.eclipse.swt.widgets.FileDialog dialog = new org.eclipse.swt.widgets.FileDialog(shell);
			if (file != null) dialog.setFilterPath(Paths.get(file).getParent().toString());
			String newFile = dialog.open();
			shell.dispose(); display.dispose();
			if (newFile != null && !newFile.equals(file)) {
				file = newFile;
				try {
					strings = openFile(file);
					refreshFileTableData();
					lastPickedTableRow = -1;
					openFileBar.setText(file + " (" + nOfKeys + " keys)");
					jTextArea.setText("");
					EmptyLayout l = (EmptyLayout) dataTable.getLayout();
					l.setMinimumLayoutSize_dim(dataTable.getMinimumSize());
					l.setPreferredLayoutSize_dim(dataTable.getPreferredSize());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} else if (e.getSource() == fitColumnSizes) {
			autoFitColumnSizes();
		} else {
			if (file != null) {
				Display display = new Display();
				Shell shell = new Shell(display);
				org.eclipse.swt.widgets.FileDialog dialog = new org.eclipse.swt.widgets.FileDialog(shell, SWT.SAVE);
				dialog.setFilterPath(Paths.get(file).getParent().toString());
				String newFile = dialog.open();
				if (newFile != null) {
					try {
						writeFile(newFile, strings);
						file = newFile;
						openFileBar.setText(file + " (" + nOfKeys + " keys)");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				shell.dispose(); display.dispose();
			}
		}
	}
	
	public static void addToByteList(List<Byte> list, byte[] arr) {
		for (byte b : arr) {
			list.add(b);
		}
	}
	
	public static void addShortWordToList_littleEndian(List<Byte> list, int shortWord) {
		list.add((byte) (shortWord % 0x0100));
		list.add((byte) (shortWord / 0x0100));
	}
	
	public static void writeFile(String path, List<String> list) throws IOException {
		String regex = "<([^>]*)>";
		Pattern pattern = Pattern.compile(regex);
		List<List<Byte>> stringsAsBytes = new ArrayList<>(list.size());
		List<Byte> bytesToWrite = new ArrayList<>();
		addShortWordToList_littleEndian(bytesToWrite, list.size());
		int currentOffset = 2 + list.size() * 2;
		for (int i = 0; i < list.size(); i++) {
			String s = list.get(i);
			List<Byte> bb = new ArrayList<>();
			int last = 0;
			Matcher m = pattern.matcher(s);
			while(m.find()) {
				String d = m.group(1);
				switch(d) {
				case "0":
				case "1":
				case "2":
				case "3":
				case "4":
				case "5":
				case "6":
				case "7":
				case "8":
				case "9":
				case "10":
				case "18":
				case "19":
				case "27":
				case "35":
					addToByteList(bb, (s.substring(last, m.start()).getBytes())); bb.add(Byte.parseByte(d)); break;
				default:
					addToByteList(bb, (s.substring(last, m.end()).getBytes()));
				}
				
				last = m.end();
			}
			addToByteList(bb, (s.substring(last, s.length()).getBytes()));
			stringsAsBytes.add(bb);
			addShortWordToList_littleEndian(bytesToWrite, currentOffset);
			currentOffset += bb.size();
		}
		
		for (List<Byte> bb : stringsAsBytes) {
			bytesToWrite.addAll(bb);
		}
		
		writeByteArr(path, bytesToWrite);
	}
	
	public List<String> readStarcraftTBL(String path) throws IOException {
		byte[] data = readFileIntoByteArr(path);
		int numElements = (data[0] & 0xFF) + ((data[1] & 0xFF) << 8);
		int[] offsets = new int[numElements];
		TreeSet<Integer> offsets_treeSet = new TreeSet<>();
		for (int i = 0; i < numElements; i++) {
			int offset = (data[2 + i * 2] & 0xFF) + ((data[2 + i * 2 + 1] & 0xFF) << 8);
			offsets[i] = offset;
			offsets_treeSet.add(offset);
		}
		offsets_treeSet.add(data.length);
		List<Integer> offsets_list = new ArrayList<>(offsets_treeSet);
		
		Collections.sort(offsets_list);
		TreeMap<Integer,Integer> lengths = new TreeMap<>();
		for (int i = offsets_list.size() - 2; i >= 0; i--) {
			lengths.put(offsets_list.get(i), offsets_list.get(i+1) - offsets_list.get(i));
		}
		
		List<String> strings = new ArrayList<>();
		for (int i = 0; i < numElements; i++) {
			int o = offsets[i];
			int l = lengths.get(o);
			byte[] strData = Arrays.copyOfRange(data, o, o + l);
			int[] insert = new int[strData.length];
			for (int j = 0; j < insert.length; j++) insert[j] = -1;
			int newLength = strData.length;
			for (int j = 0; j < strData.length; j++) {
				if (strData[j] >= 0 && strData[j] <= 9) { insert[j] = strData[j]; newLength += 2; }
				else if (strData[j] == 10 || strData[j] == 18 || strData[j] == 19 || strData[j] == 27 || strData[j] == 35) { insert[j] = strData[j]; newLength += 3; }
			}
			byte[] strData2 = new byte[newLength];
			int offset = 0;
			for (int j = 0; j < strData.length; j++) {
				if (insert[j] >= 0 && insert[j] <= 9) { strData2[j + offset] = '<'; strData2[j + offset + 1] = (byte) ('0' + insert[j]); strData2[j + offset + 2] = '>'; offset+=2; }
				else if (strData[j] == 10 || strData[j] == 18 || strData[j] == 19 || strData[j] == 27 || strData[j] == 35) {
					{ strData2[j + offset] = '<'; strData2[j + offset + 1] = (byte) ('0' + insert[j] / 10); strData2[j + offset + 2] = (byte) ('0' + insert[j] % 10); strData2[j + offset + 3] = '>'; offset+=3; }
				}
				else strData2[j + offset] = strData[j];
			}
			strings.add(new String(strData2, "8859_1"));
		}
		
		nOfKeys = strings.size();
		return strings;
	}
	
	public static byte[] readFileIntoByteArr(String path) throws IOException {
		return Files.readAllBytes(Paths.get(path));
	}
	
	public static Path writeByteArr(String filepath, List<Byte> bytes) throws IOException {
		Path path = Paths.get(filepath);
		byte[] arr = new byte[bytes.size()];
		for (int i = 0; i < bytes.size(); i++) arr[i] = bytes.get(i);
		return Files.write(path, arr);
	}
	
	public static void main(String args[]) throws IOException, InterruptedException {
		TBL_Editor f = new TBL_Editor();
		f.setVisible(true);
	}
}
