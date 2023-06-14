package edu.cs3500.spreadsheets.provider.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import edu.cs3500.spreadsheets.provider.adapter.WorksheetModel;

public class MyKeyListener implements KeyListener {
  GraphicView v;
  WorksheetModel m;

  public MyKeyListener(GraphicView v) {
    this.v = v;
    this.m = v.table.getWorksheet();
  }
  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {

  }

  @Override
  public void keyReleased(KeyEvent e) {

  }
}
