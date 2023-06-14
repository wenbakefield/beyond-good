package edu.cs3500.spreadsheets.provider.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * Implementatino of a button listener to run the actions correlated with each button.
 */
public class MyButtonListener implements ActionListener {

  Map<String, Runnable> buttonClickedActions;


  /**
   * Set the map for key typed events. Key typed events in Java Swing are characters
   */
  public void setButtonClickedActionMap(Map<String, Runnable> map) {
    buttonClickedActions = map;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (buttonClickedActions.containsKey(e.getActionCommand())) {

      buttonClickedActions.get(e.getActionCommand()).run();
    }
  }
}
