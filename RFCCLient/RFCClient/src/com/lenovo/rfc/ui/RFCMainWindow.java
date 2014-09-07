package com.lenovo.rfc.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;

public class RFCMainWindow {

	protected Shell shell;

	/**
	 * Launch the application.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			RFCMainWindow window = new RFCMainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setMaximized(true);
		shell.setText("SWT Application");
		shell.setLayout(new GridLayout(1, false));

		ToolBar toolBar = new CToolBar(shell).getViewer();
		toolBar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		SashForm sashForm = new SashForm(shell, SWT.VERTICAL);
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		new WorkPanel(sashForm).getViewer();
		new ConsolePanel(sashForm);
		sashForm.setWeights(new int[] { 2, 1 });
	}
}
