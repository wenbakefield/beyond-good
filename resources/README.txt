Design Changes:

SpreadsheetVisualView was completely redesigned through the usage of JTable, as it is a flexible choice that can be used 
for the visual (read-only) view and the editor view.

SpreadsheetVisualView was renamed to SpreadsheetReadOnlyView to remove ambiguity with SpreadsheetEditorView.

SpreadsheetPanel was completely redesigned to use JTable instead of Graphics2D to display the model because
controller functionality is more easily implemented with JTable over Graphics2D. Additionally, using JTable allows
for both SpreadsheetEditorView and SpreadsheetReadOnlyView to use the same JPanel for their contents.