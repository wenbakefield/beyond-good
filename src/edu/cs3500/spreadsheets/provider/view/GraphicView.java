package edu.cs3500.spreadsheets.provider.view;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.ComponentOrientation;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.adapter.Content;
import edu.cs3500.spreadsheets.provider.adapter.WorksheetModel;

/**
 * An object representing the graphical view of the spreasheet.
 */
public class GraphicView extends JFrame implements IView {
  public MyJTable table;
  public int maxX;
  public int maxY;

  public JTextField jtf;
  public JButton jcb1;
  public JButton jcb2;
  private String oldText = "";
  private Coord clickedPos;
  public JScrollPane sp;
  MyJList rowHeader;

  /**
   * A constructor which constructs a graphic view based off the inputted worksheet.
   * @param w the worksheet to be displayed.
   */
  public GraphicView(WorksheetModel w) {
    super("Excel");

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    maxX = 0;
    maxY = 0;

    Map<Coord, Content> m = w.getSheet();
    findMax(m);
    String[] header = new String[maxX];
    String[][] data = new String[maxY][maxX];

    for (int i = 1; i <= maxX; i++) {
      header[i - 1] = (Coord.colIndexToName(i));
    }

    /*for (int i = 0; i < maxY; i ++) {
      data[i][0] = Integer.toString(i + 1);
    }*/

    for (int x = 1; x <= maxX; x++) {
      for (int y = 1; y <= maxY; y++) {
        if (!m.containsKey(new Coord(x, y))) {
          data[y - 1][x - 1] = "";
        } else {
          data[y - 1][x - 1] = w.getValue(new Coord(x, y));
        }
      }
    }


    table = new MyJTable(new MyTableModel(data, header));
    table.setWorksheet(w);
    table.setRowHeight(25);
    for (int i = 0; i < maxX; i ++) {
      table.getColumnModel().getColumn(i).setWidth(100);
    }
    this.setBounds(50, 50, 300, 300);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    table.setGridColor(Color.GRAY);

    table.setColumnSelectionAllowed(false);
    table.setRowSelectionAllowed(false);
    table.setCellSelectionEnabled(true);

    sp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);


    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(sp, BorderLayout.CENTER);
    mainPanel.setPreferredSize(new Dimension(300,300));

    RowHeader lm = new RowHeader(maxY);
    /*new AbstractListModel() {
      int header[] = setHeader();

      public int getSize() {
        return header.length;
      }

      public Object getElementAt(int index) {
        return header[index];
      }
    };*/

    rowHeader = new MyJList(lm);

    rowHeader.setFixedCellWidth(50);
    rowHeader.setFixedCellHeight(table.getRowHeight());// + table.getRowMargin());
    rowHeader.setCellRenderer(new RowHeaderRenderer(table));
    sp.setRowHeaderView(rowHeader);




    this.setLayout(new BorderLayout());
    this.getContentPane().add(mainPanel, BorderLayout.CENTER);

    JPanel controlBar = new JPanel(new BorderLayout());

    JPanel buttons  = new JPanel(new FlowLayout(0, 5, 0));
    buttons.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    jcb1 = new JButton("✓");
    jcb1.setActionCommand("Confirm");
    jcb1.setSize(20,20);
    jcb1.setPreferredSize(new Dimension(20, 20));
    buttons.add(jcb1);

    jcb2 = new JButton("X");
    jcb2.setActionCommand("Cancel");
    jcb2.setSize(20,20);
    jcb2.setPreferredSize(new Dimension(20, 20));

    jcb2.setMaximumSize(getSize());

    buttons.add(jcb2);

    jtf = new JTextField();
    jtf.setPreferredSize(new Dimension(100, 20));

    controlBar.add(buttons, BorderLayout.WEST);
    controlBar.add(jtf, BorderLayout.CENTER);

    mainPanel.add(controlBar, BorderLayout.NORTH);


    this.pack();
    this.setMinimumSize(new Dimension(100, 100));
    this.setVisible(true);
    this.addAdjustmentListener(new MyAdjustmentListener(this));

  }

  /**
   * sets the maximum x and y coordinates of the spreadsheet.
   * @param m the map of the contents of the worksheet.
   */
  private void findMax(Map<Coord, Content> m) {
    for (Coord c: m.keySet()) {
      maxX = Math.max(maxX, c.col);
      maxY = Math.max(maxY, c.row);
    }
  }


  /**
   * adds a mouse listener to the table for selecting cells.
   * @param m the mouse listener to be added.
   */
  public void addMouseListener(MyMouseListener m)  {
    table.addMouseListener(m);
  }


  /**
   * adds an action listener for the JButtons.
   * @param actionListener the action listener to be added.
   */
  public void addActionListener(ActionListener actionListener) {
    jcb1.addActionListener(actionListener);
    jcb2.addActionListener(actionListener);
  }

  /**
   * adds an adjustment listener for changing the view when scrolling occurs.
   * @param adjustmentlistener the adjustment listener to be added.
   */
  public void addAdjustmentListener(MyAdjustmentListener adjustmentlistener) {
    sp.getVerticalScrollBar().addAdjustmentListener(adjustmentlistener);
    sp.getHorizontalScrollBar().addAdjustmentListener(adjustmentlistener);
  }


  /**
   * sets the data in the textfield to be the actual function in the worksheet.
   * @param t the string to be displayed.
   * @param c the coordinate that the user clicked on.
   */
  public void setTextField(String t, Coord c) {
    oldText = t;
    clickedPos = c;
    jtf.setText(t);
  }

  /**
   * updates the worksheet to change to what is contained in the textfield, and changes the.
   * tablemodel's worksheet accordingly.
   */
  public void updateWorksheet() {
    WorksheetModel w = table.getWorksheet();
    w.setCell(clickedPos, jtf.getText());
    oldText = jtf.getText();
    table.setWorksheet(w);
    table.getMyModel().updateTable(table.getWorksheet());
  }


  /**
   * If the user cancels the action, it resets the textfield to display the old value.
   */
  public void resetText() {
    jtf.setText(oldText);
  }



}
