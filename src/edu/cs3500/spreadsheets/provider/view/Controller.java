package edu.cs3500.spreadsheets.provider.view;

import java.util.HashMap;
import java.util.Map;

import edu.cs3500.spreadsheets.provider.adapter.WorksheetModel;

/**
 * Controller class for handling the functions of the graphicview.
 */
public class Controller {

  private WorksheetModel w;
  private GraphicView v;

  /**
   * basic constructor for the controller class which takes in the graphic view ot be operated on.
   * @param v the graphic view to be controlled.
   */
  public Controller(GraphicView v) {
    this.w = v.table.getWorksheet();
    this.v = v;

    Map<Integer, Runnable> mouseClick = new HashMap<>();
    v.addMouseListener(new MyMouseListener(this.v));
    this.configureButtonListener();
    System.out.println("controller");
    //v.addAdjustmentListener(new MyAdjustmentListener(this.v));
  }

  /**
   * configures the button listeners for the graphic view.
   */
  private void configureButtonListener() {
    Map<String, Runnable> buttonClickedMap = new HashMap<String, Runnable>();
    MyButtonListener buttonListener = new MyButtonListener();

    buttonClickedMap.put("Confirm", () -> {
      v.updateWorksheet();

    });
    buttonClickedMap.put("Cancel", () -> {
      v.resetText();
    });

    buttonListener.setButtonClickedActionMap(buttonClickedMap);
    this.v.addActionListener(buttonListener);
  }

}
